package com.admi.data.services;

import com.admi.data.dto.*;
import com.admi.data.entities.*;
import com.admi.data.enums.UdbInventoryField;
import com.admi.data.repositories.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ImportService {

	@Autowired
	ProcessService processService;

	@Autowired
	EmailService emailService;

	@Autowired
	AipInventoryService aipInventoryService;

	@Autowired
	RRImportService rrImportService;

	@Autowired
	RRPowerImportService rrPowerImportService;

	@Autowired
	CdkImportService cdkImportService;

	@Autowired
	AipInventoryRepository inventoryRepo;

	@Autowired
	McPartsRepository partsRepo;

	@Autowired
	McOrdersRepository ordersRepo;

	@Autowired
	McOrdersContentRepository ordersContentRepo;

	@Autowired
	DealerMasterRepository dealerRepo;

	@Async("asyncExecutor")
	public void runAipInventory(InputStream file, Long dealerId, String paCode, int dmsId, String fileType)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		System.out.println(DateService.getTimeString() + ": Importing Dealer " + dealerId);

		List<AipInventoryEntity> inventory;

		System.out.println("File type: " + fileType);

		if (Objects.equals(fileType, "text/csv")	) {
			inventory = importCsvInventoryFile(file, dealerId, dmsId);
		} else if (Objects.equals(fileType, "application/vnd.ms-excel")) {
			inventory = importXlsInventoryFile(file, dealerId, dmsId);
		} else {
			inventory = importXlsxInventoryFile(file, dealerId, dmsId);
		}

		aipInventoryService.saveAll(inventory, dealerId, paCode);

		System.out.println(DateService.getTimeString() + ": Completed importing Dealer " + dealerId);
	}

	@Async("asyncMotorcraftExecutor")
	public void runMotorcraftOrders(ImportJob job) throws IOException, InvalidFormatException, MessagingException {
		List<MotorcraftOrderSet> orders = importMotorcraftOrders(job);

		try {
			emailService.sendMotorcraftOrderEmail(job.getEmail(), orders, job.getPaCode());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

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


		String paCode;
		try {
			paCode = paCodeCell.getStringCellValue();
		} catch (IllegalStateException e) {
			Double paCodeInt = paCodeCell.getNumericCellValue();
			paCode = String.valueOf(paCodeInt.intValue());
		}


		Boolean skipOrder = false;

		if (paCode == null || paCode.equals("")) {
//			paCode = job.getPaCode();
			issues.add(new ImportIssue(
					"Missing P&A Code",
					"Order was uploaded without a P&A Code",
					sheet.getSheetName(),
					"Please re-upload this sheet with a P&A Code." )
			);
			skipOrder = true;
		}

		Cell poNumberCell = sheet.getRow(1).getCell(1);
		String poNumber = poNumberCell.getStringCellValue();


		if (poNumber.equals("") || poNumber == null) {
			issues.add(new ImportIssue(
					"Missing PO Number",
					"Order was uploaded without a PO Number",
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

	public List<AipInventoryEntity> importInventoryFile(InputStream file, Long dealerId, int dmsId)
			throws InvalidFormatException, IllegalAccessException, NoSuchFieldException, IOException {
		List<AipInventoryEntity> inventory;
		inventory = importXlsxInventoryFile(file, dealerId, dmsId);

		return inventory;
	}

	public List<AipInventoryEntity> importInventoryFile(ImportJob job)
			throws InvalidFormatException, IllegalAccessException, NoSuchFieldException, IOException {
		File file = new File(job.getFilePath());

		System.out.println(job.toString());
		System.out.println("File Exists: " + file.exists());

		return importInventoryFile(new FileInputStream(file), job.getDealerId(), job.getDmsId());
	}

	public List<AipInventoryEntity> importUdbInventoryFile(MultipartFile file) throws IOException, InvalidFormatException {
		OPCPackage pkg = OPCPackage.open(file.getInputStream());
		Workbook workbook = new XSSFWorkbook(pkg);
		List<AipInventoryEntity> inventory = new ArrayList<>();
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
		DataFormatter cellFormatter = new DataFormatter();

		List<AipInventoryEntity> aipInventory = importUdbInventorySheet(workbook.getSheetAt(0), cellFormatter);

		inventoryRepo.saveAll(aipInventory);
		processService.calculateAisKpi(aipInventory);

		return aipInventory;
	}

	private List<AipInventoryEntity> importUdbInventorySheet(Sheet sheet, DataFormatter cellFormatter) {
		Iterator<Row> rowIterator = sheet.rowIterator();
		List<UdbInventoryField> headers = getUdbInventoryHeaders(rowIterator.next());
		List<AipInventoryEntity> inventory = new ArrayList<>();

		Row row = sheet.getRow(1);
		String paCode = cellFormatter.formatCellValue(row.getCell(0));

		if (paCode != null) {
			String paddedPaCode = String.format("%5s", paCode).replace(' ', '0');
			DealerMasterEntity dealer = dealerRepo.findFirstByPaCodeAndPrimaryManufacturerIdAndTerminationDateIsNull(paddedPaCode, 1);

			while (rowIterator.hasNext()) {
				AipInventoryEntity part = importUdbInventoryRow(rowIterator.next(), headers, cellFormatter, dealer.getDealerId());
				inventory.add(part);
			}
		}

		return inventory;
	}

	private AipInventoryEntity importUdbInventoryRow(Row row, List<UdbInventoryField> headers, DataFormatter cellFormatter, Long dealerId) {
		Iterator<Cell> cellIterator = row.cellIterator();
		AipInventoryEntity inventoryEntity = new AipInventoryEntity();
		inventoryEntity.setDataDate(LocalDate.now());
		inventoryEntity.setDealerId(dealerId);

		short lastCell = row.getLastCellNum();

		for (int i = 0; i < lastCell; i++) {
			Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			UdbInventoryField header = headers.get(i);

			CellDefinition<AipInventoryEntity, ?, ?> cellDefinition = header.getDefinition();
			String cellValue = cellFormatter.formatCellValue(cell);
//			System.out.println("Header: " + header.getFieldName() + " Value: " + cellValue);

			if (cellDefinition.getEntitySetter() != null)
				cellDefinition.getAndSetField(cellValue, inventoryEntity);
		}

		inventoryEntity.setAdmiStatus(getAdmiStatusForUdb(inventoryEntity.getStatus()));
		return inventoryEntity;
	}

	private String getAdmiStatusForUdb(String status) {
		String newStatus;
		if (status == null) {
			newStatus = "N";
		} else if (status.equals("Y")) {
			newStatus = "S";
		} else {
			newStatus = "N";
		}
		return newStatus;
	}

	private List<UdbInventoryField> getUdbInventoryHeaders(Row row) {
		Iterator<Cell> cellIterator = row.cellIterator();
		List<UdbInventoryField> headers = new ArrayList<>();

		while (cellIterator.hasNext())
			headers.add(UdbInventoryField.of(cellIterator.next().getStringCellValue()));

		return headers;
	}

	public ImportJob createImportJob(MultipartFile file, Long dealerId, Integer dmsId, String paCode) {
		String filePath = saveInventoryFile(file, paCode);

		return createImportJob(filePath, dealerId, dmsId, paCode, file.getContentType());
	}

	public ImportJob createImportJob(String filePath, Long dealerId, Integer dmsId, String paCode, String contentType) {
		ImportJob job = new ImportJob(dealerId, dmsId, filePath, paCode, contentType);

		if (filePath != null)
			return job;
		else
			return null;
	}

	public ImportJob createMotorcraftImportJob(MultipartFile file, Long dealerId, String paCode) {
		String filePath = saveMotorcraftFile(file, paCode);

		return createImportJob(filePath, dealerId, null, paCode, file.getContentType());
	}

	public List<AipInventoryEntity> importCsvInventoryFile(InputStream file, Long dealerId, int dmsId){
		List<AipInventoryEntity> inventory = new ArrayList<>();

		switch(dmsId) {
			case 1: 	//R&R ERA
			case 50:	//R&R Ignite
//				inventory = rrImportService.importCsvInventoryFile(file, dealerId);
				break;
			case 48: 	//R&R Power
				inventory = rrPowerImportService.importCsvInventoryFile(file, dealerId);
				break;
			default:
				System.out.println("Tried to import CSV for DMS ID " + dmsId + ", but there's no CSV import process for this DMS.");
				break;
		}

		return inventory;
	}

	public List<AipInventoryEntity> importXlsxInventoryFile(InputStream file, Long dealerId, int dmsId)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		OPCPackage pkg = OPCPackage.open(file);

		Workbook workbook = new XSSFWorkbook(pkg);
		Sheet sheet = workbook.getSheetAt(0);
		pkg.close();

		return importAnyExcelInventoryFile(sheet, dealerId, dmsId);
	}

	public List<AipInventoryEntity> importXlsInventoryFile(InputStream file, Long dealerId, int dmsId)
			throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
		Workbook workbook = new HSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);
		return importAnyExcelInventoryFile(sheet, dealerId, dmsId);
	}

	private List<AipInventoryEntity> importAnyExcelInventoryFile(Sheet sheet, Long dealerId, int dmsId) throws NoSuchFieldException, IllegalAccessException {
		List<AipInventoryEntity> inventory = new ArrayList<>();

		switch(dmsId) {
			case 0:
			case 1: 	//R&R ERA
			case 50:	//R&R Ignite
				inventory = rrImportService.importInventory(sheet, dealerId);
				break; //note that RR Power runs under the importCsvInventoryFile process
			case 8:		//CDK
			case 35:	//CDK
			case 37:	//CDK
			case 53:	//CDK
			case 54:	//CDK
			case 61:	//CDK
				inventory = cdkImportService.importInventory(sheet, dealerId);
				break;
			default:
				break;
		}

		return inventory;
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
