package com.admi.data.imports;

import com.admi.data.dto.*;
import com.admi.data.entities.*;
import com.admi.data.enums.RRField;
import com.admi.data.processes.DateService;
import com.admi.data.processes.EmailService;
import com.admi.data.processes.ProcessService;
import com.admi.data.repositories.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.admi.data.enums.RRField.*;

@Service
public class ImportService {

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	ProcessService processService;

	@Autowired
	ZigRepository zigRepo;

	@Autowired
	McPartsRepository partsRepo;

	@Autowired
	McOrdersRepository ordersRepo;

	@Autowired
	McOrdersContentRepository ordersContentRepo;

	@Autowired
	EmailService emailService;


	@Async("asyncExecutor")
	public void runAipInventory(ImportJob importJob) throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {

		System.out.println(DateService.getTimeString() + ": Importing Dealer " + importJob.getDealerId());
		List<AipInventoryEntity> inventory = importInventoryFile(importJob);
		System.out.println(DateService.getTimeString() + ": Completed importing Dealer " + importJob.getDealerId());


		System.out.println(DateService.getTimeString() + ": Processing KPIs");
		KpiEntity kpis = processService.calculateAisKpi(inventory, importJob.getPaCode());
		System.out.println(DateService.getTimeString() + ": Processing complete!");

		System.out.println(DateService.getTimeString() + ": Writing ZIG");
		zigRepo.deleteAllByPaCode(importJob.getPaCode());
		zigRepo.copyZigParts(importJob.getPaCode(), importJob.getDealerId(), LocalDate.now());
		System.out.println(DateService.getTimeString() + ": ZIG Complete!");
		System.out.println(DateService.getTimeString() + ": Inventory Import Complete!");
	}

	@Async("asyncMotorcraftExecutor")
	public void runMotorcraftOrders(ImportJob job) throws IOException, InvalidFormatException, MessagingException {

		List<ImportIssue> issues = new ArrayList<>();
		List<MotorcraftOrderSet> orders = importMotorcraftOrders(job);

		orders.forEach((MotorcraftOrderSet order) -> issues.addAll(order.getIssues()));

		emailService.sendMotorcraftOrderEmail(job.getEmail(), issues, job.getPaCode());

		processService.generateDowOrders(orders);
		System.out.println("File Imported");
	}

	public List<MotorcraftOrderSet> importMotorcraftOrders(ImportJob job) throws IOException, InvalidFormatException {
		File file = new File(job.getFilePath());
		OPCPackage pkg = OPCPackage.open(file);

		List<MotorcraftOrderSet> orders = new ArrayList<>();

		Workbook workbook = new XSSFWorkbook(pkg);
		for (Iterator<Sheet> it = workbook.sheetIterator(); it.hasNext(); ) {
			Sheet sheet = it.next();

			orders.add(createMotorcraftOrder(sheet, job));
		}

		pkg.close();

		return orders;
	}

	private MotorcraftOrderSet createMotorcraftOrder(Sheet sheet, ImportJob job) {
		McOrdersEntity order = new McOrdersEntity();
		List<McOrdersContentEntity> orderContent = new ArrayList<>();
		List<McPartsEntity> allowedParts = partsRepo.findAllByReleasedIsNotNull();

		List<ImportIssue> issues = new ArrayList<>();

		String orderNumber = ordersRepo.getOrderNumber();

		Cell paCodeCell = sheet.getRow(0).getCell(1);
		String paCode = paCodeCell.getStringCellValue();

		Boolean skipOrder = false;

		if (paCode == null || paCode.equals("")) {
			paCode = job.getPaCode();
			issues.add(new ImportIssue(
					"Missing P&A Code",
					"Order was submitted without a P&A Code",
					sheet.getSheetName(),
					"The order has been imported using your account's P&A Code." )
			);
		}

		Cell poNumberCell = sheet.getRow(1).getCell(1);
		String poNumber = poNumberCell.getStringCellValue();


		if (poNumber.equals("") || poNumber == null) {
			issues.add(new ImportIssue(
					"Missing PO Number",
					"Order was submitted without a PO Number",
					sheet.getSheetName(),
					"Please re-upload this sheet with a PO Number." )
			);
			skipOrder = true;
		}

		Cell orderDateCell = sheet.getRow(2).getCell(1);
		Date cellDate = orderDateCell.getDateCellValue();
		LocalDate orderDate = LocalDate.now();

		if (cellDate != null) {
			orderDate = orderDateCell.getDateCellValue()
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
		} else {
			issues.add(new ImportIssue(
					"Missing Order Date",
					"Order date was missing or invalid.",
					sheet.getSheetName(),
					"Please re-upload this sheet with a valid order date." )
			);
			skipOrder = true;
		}

		order.setOrderNumber(orderNumber);
		order.setPaCode(paCode);
		order.setEmail(job.getEmail());
		order.setPlaced(LocalDateTime.now());
		order.setLastUpdated(LocalDateTime.now());
		order.setPoNumber(poNumber);
		order.setOrderDate(orderDate);

		boolean hasNextPart = true;
		int rowIndex = 5;

		while (hasNextPart && !skipOrder) {
			Row part = sheet.getRow(rowIndex);
			String partNumber;

			try {
				partNumber = part.getCell(0).getStringCellValue();
			} catch (NullPointerException e) {
				hasNextPart = false;
				break;
			}

			McPartsEntity mcPart = getAllowedPart(partNumber, allowedParts);

			if (mcPart != null) {
				McOrdersContentEntity orderLine = new McOrdersContentEntity();
				Double quantity = part.getCell(2).getNumericCellValue();

				orderLine.setOrderNumber(order.getOrderNumber());
				orderLine.setPaCode(paCode);
				orderLine.setPartno(mcPart.getPartno());
				orderLine.setPrice(mcPart.getFadPrice());
				orderLine.setQty(quantity.longValue());
				orderLine.setOcPartno(mcPart.getOcPartno());
				orderLine.setSupplierPartno(mcPart.getPartno());

				if (orderLine.getQty() > 0) {
					System.out.println(orderLine);
					orderContent.add(orderLine);
				}
			}
			rowIndex++;
		}

//		System.out.println(order.toString());
		if (!skipOrder) {
			if (orderContent.size() > 0) {

				order = ordersRepo.save(order);
				ordersContentRepo.saveAll(orderContent);

				System.out.println("Saved!");
			} else {
				issues.add(new ImportIssue(
						"No Parts",
						"Order was submitted without any quantities.",
						sheet.getSheetName(),
						"Please re-upload this sheet with corrected quantities, if this was a mistake." )
				);
			}
		}

		return new MotorcraftOrderSet(order, orderContent, issues);
	}

	private List<McOrdersContentEntity> updateOrderNumber(List<McOrdersContentEntity> parts, String orderNumber) {
		for (McOrdersContentEntity part : parts) {
			part.setOrderNumber(orderNumber);
		}
		return parts;
	}

	private McPartsEntity getAllowedPart(String partNumber, List<McPartsEntity> parts) {
		for (McPartsEntity part : parts) {
			if (part.getPartno().equals(partNumber)) {
				return part;
			}
		}
		return null;
	}

	private boolean isPartAllowed(String partNumber, List<McPartsEntity> parts) {
		for (McPartsEntity part : parts) {
			if (part.getPartno().equals(partNumber)) {
				return true;
			}
		}
		return false;
	}

	public List<AipInventoryEntity> importInventoryFile(File file, Long dealerId, int dmsId)
			throws InvalidFormatException, IllegalAccessException, NoSuchFieldException, IOException {
		List<AipInventoryEntity> inventory = new ArrayList<>();


		String fileType = Files.probeContentType(file.toPath());
		System.out.println("File Type: " + fileType);

//		switch (Objects.requireNonNull(fileType)) {
//			case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
				inventory = importXlsxInventoryFile(file, dealerId, dmsId);
//				break;
//			case "application/vnd.ms-excel":
//			case "application/octet-stream":
//			default:
//				break;
//		}
		return inventory;
	}

	public List<AipInventoryEntity> importInventoryFile(ImportJob job)
			throws InvalidFormatException, IllegalAccessException, NoSuchFieldException, IOException {
		File file = new File(job.getFilePath());

		System.out.println(job.toString());
		System.out.println("File Exists: " + file.exists());

		return importInventoryFile(file, job.getDealerId(), job.getDmsId());
	}

	public ImportJob createImportJob(MultipartFile file, Long dealerId, Integer dmsId, String paCode) {
		String filePath = saveInventoryFile(file, paCode);

		return createImportJob(filePath, dealerId, dmsId, paCode);
	}

	public ImportJob createImportJob(String filePath, Long dealerId, Integer dmsId, String paCode) {
		ImportJob job = new ImportJob(dealerId, dmsId, filePath);

		if (filePath != null) {
			return job;
		} else {
			return null;
		}
	}

	public ImportJob createMotorcraftImportJob(MultipartFile file, Long dealerId, String paCode) {
		String filePath = saveMotorcraftFile(file, paCode);

		return createImportJob(filePath, dealerId, null, paCode);
	}

	private List<AipInventoryEntity> importXlsxInventoryFile(File file, Long dealerId, int dmsId)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		OPCPackage pkg = OPCPackage.open(file);

		Workbook workbook = new XSSFWorkbook(pkg);
		Sheet sheet = workbook.getSheetAt(0);
		pkg.close();

		List<AipInventoryEntity> inventory = new ArrayList<>();

		switch(dmsId) {
			case 0:
			case 1:
			case 48:
			case 50:
				inventory = importRAndRInventory(sheet, dealerId);
				break;
			default:
				break;
		}

		inventoryRepo.saveAll(inventory);
		return inventory;
	}

	private List<AipInventoryEntity> importRAndRInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
		Row topRow = sheet.getRow(0);

		List<RRField> headers = getHeaderList(topRow);
		EnumMap<RRField, FieldDefinition<RRDto, ?>> rrFields = getRRFieldMap();

		List<RRDto> inventory = new ArrayList<>();
		List<AipInventoryEntity> inventoryList = new ArrayList<>();

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			RRDto rowDTO = new RRDto();

			if (row.getRowNum() != 0) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int i = cell.getColumnIndex();
					RRField field = headers.get(i);

					try {
						setDtoField(cell, rowDTO, rrFields.get(field));
					} catch(Exception e) {
						System.out.println("Cell: " + cell.toString());
						System.out.println("Field: " + field.toString());
						e.printStackTrace();
					}
//					throw new IllegalStateException("Unexpected value: " + headers.get(i));
				}

//				System.out.println(rowDTO.toString());
				inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now()));
				inventory.add(rowDTO);
			}
		}

		System.out.println("Row Count: " + inventory.size());

		return inventoryList;
	}

	@SuppressWarnings("Duplicates")
	private EnumMap<RRField, FieldDefinition<RRDto, ?>> getRRFieldMap (){
		EnumMap<RRField, FieldDefinition<RRDto, ?>> enumMap = new EnumMap<>(RRField.class);

		Map<RRField, FieldDefinition<RRDto, ?>> map = new HashMap<>();

		enumMap.put(PART_NO, new FieldDefinition<>(CellType.STRING, String.class, RRDto ::setPartNo));
		enumMap.put(COST, new FieldDefinition<>(CellType.NUMERIC, Double.class, RRDto :: setCostCents));
		enumMap.put(QOH, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto ::setQuantityOnHand));
		enumMap.put(DESC, new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setDescription));
		enumMap.put(STAT, new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setStatus));
		enumMap.put(LAST_SALES_DATE, new FieldDefinition<>(CellType.NUMERIC, LocalDate.class, RRDto :: setLastSaleDate));
		enumMap.put(LAST_RECEIPT_DATE, new FieldDefinition<>(CellType.NUMERIC, LocalDate.class, RRDto :: setLastReceiptDate));
		enumMap.put(SRC, new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setSource));
		enumMap.put(BIN, new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setBin));
		enumMap.put(MAKE, new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setMake));
		enumMap.put(MFG_CONTROL, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setMfgControlled));
		enumMap.put(MIN, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setMin));
		enumMap.put(MAX, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setMax));
		enumMap.put(BSL_CAT, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto ::setBestStockingLevel));
		enumMap.put(QPR, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto ::setQuantityPerRepair));
		enumMap.put(HIST_6, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setHistory6));
		enumMap.put(HIST_12, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setHistory12));
		enumMap.put(HIST_24, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setHistory24));

		return enumMap;
	}

	private <T, V> void setDtoField(Cell cell, T dto, FieldDefinition<T, V> fieldDefinition) {
		CellType cellType = fieldDefinition.getCellType();
		Class<?> clazz = fieldDefinition.getClazz();

		V value = null;

		if (cell.getCellType() == cellType) {
			if (clazz == String.class) {
				value = (V) cell.getStringCellValue();
			} else if (clazz == Long.class) {
				Double d = cell.getNumericCellValue();
				value = (V) Long.valueOf(Math.round(d));
			} else if (clazz == Double.class) {
//				We are making the assumption that Doubles are only used for the cost of a part.
				Long l = Math.round(cell.getNumericCellValue() * 100);
				value = (V) l;
			} else if (clazz == LocalDate.class) {
				LocalDate date = cell.getDateCellValue()
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
				value = (V) date;
			} else {
				System.out.println("Incorrect class for cell: " + cell.toString());
			}
			fieldDefinition.getSetter()
					.accept(dto, value);
		}
	}

	private List<RRField> getHeaderList(Row row) {
		Iterator<Cell> cellIterator = row.iterator();
		List<RRField> headers = new ArrayList<>();

		while(cellIterator.hasNext()) {
			String cellValue = cellIterator.next().getStringCellValue();

//			System.out.println(cellValue);

			RRField field = RRField.findByColumnName(cellValue);
			headers.add(RRField.findByColumnName(cellValue));
		}

//		System.out.println(headers.toString());
		return headers;
	}

	public String saveInventoryFile(MultipartFile file, String paCode) {
		String filePath = "P:" + File.separator +
				"Development" + File.separator +
				"AIP Inventory Files" + File.separator
				+ paCode + File.separator;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		filePath += paCode + "_" + DateService.getFileTimeString() + "." + extension;

		if (isAcceptedType(file) && saveFileToDirectory(file, filePath)) {
			return filePath;
		} else {
			return null;
		}
	}

	public String saveMotorcraftFile(MultipartFile file, String paCode) {
		String filePath = "P:" + File.separator +
				"Development" + File.separator +
				"Motorcraft_Orders" + File.separator
				+ paCode + File.separator;

		String extension = FilenameUtils.getExtension(file.getOriginalFilename());

		filePath += paCode + "_" + DateService.getFileTimeString() + "." + extension;

		if (isAcceptedType(file) && saveFileToDirectory(file, filePath)) {
			return filePath;
		} else {
			return null;
		}
	}

	private Boolean isAcceptedType(MultipartFile file) {
		String fileType = Objects.requireNonNull(file.getContentType());

		switch (fileType) {
			case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
				return true;
			case "application/vnd.ms-excel":
			case "application/octet-stream":
			default:
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "File type " + fileType + " is not accepted.");
		}
	}

	private Boolean saveFileToDirectory(MultipartFile file, String filePath) {
		try {
			Files.createDirectories(Paths.get(filePath));

			File newFile = new File(filePath);

			file.transferTo(newFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


}
