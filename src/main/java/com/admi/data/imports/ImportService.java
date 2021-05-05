package com.admi.data.imports;

import com.admi.data.dto.FieldDefinition;
import com.admi.data.dto.RRDto;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.enums.RRField;
import com.admi.data.repositories.AipInventoryRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.admi.data.enums.RRField.*;

@Service
public class ImportService {

	@Autowired
	AipInventoryRepository inventoryRepo;

	DateTimeFormatter rrFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

	public String importXlsxInventoryFile(MultipartFile multipartFile, Long dealerId, Long dmsId) throws IOException, InvalidFormatException, NoSuchFieldException, IllegalAccessException {
//		FileInputStream inputStream = new FileInputStream(file);
//		File file = getFileFromMultipartFile(multipartFile);

//		OPCPackage pkg = OPCPackage.open(file);
		OPCPackage pkg = OPCPackage.open(multipartFile.getInputStream());

		Workbook workbook = new XSSFWorkbook(pkg);
		Sheet sheet = workbook.getSheetAt(0);

		if (dmsId == 0) {
			importRAndRInventory(sheet, dealerId);
		} else if (dmsId == 1) {

		} else if (dmsId == 2) {

		}

		pkg.close();
		return "Imported";
	}

	private File getFileFromMultipartFile(MultipartFile multipartFile) throws IOException {
		File file = new File("src/main/resources/temp/" + multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);

		return file;
	}

	private void importRAndRInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
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
						System.out.println(e);
						System.out.println(cell.toString());
						System.out.println(rrFields.get(field).toString());
					}
//					throw new IllegalStateException("Unexpected value: " + headers.get(i));
				}
				System.out.println(rowDTO.toString());
				inventoryList.add(rowDTO.toAipInventory(1969L, LocalDate.now()));
				inventory.add(rowDTO);
			}
		}
		inventoryRepo.saveAll(inventoryList);
		System.out.println("Row Count: " + inventory.size());
	}

	@SuppressWarnings("Duplicates")
	private EnumMap<RRField, FieldDefinition<RRDto, ?>> getRRFieldMap (){
		EnumMap<RRField, FieldDefinition<RRDto, ?>> enumMap = new EnumMap<>(RRField.class);

		Map<RRField, FieldDefinition<RRDto, ?>> map = new HashMap<>();

		enumMap.put(PART_NO, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto ::setPartNo));
		enumMap.put(COST, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Double.class, RRDto :: setCostCents));
		enumMap.put(QOH, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto ::setQuantityOnHand));
		enumMap.put(DESC, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setDescription));
		enumMap.put(STAT, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setStatus));
		enumMap.put(LAST_SALES_DATE, new FieldDefinition<RRDto, LocalDate>(CellType.NUMERIC, LocalDate.class, RRDto :: setLastSaleDate));
		enumMap.put(LAST_RECEIPT_DATE, new FieldDefinition<RRDto, LocalDate>(CellType.NUMERIC, LocalDate.class, RRDto :: setLastReceiptDate));
		enumMap.put(SRC, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setSource));
		enumMap.put(BIN, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setBin));
		enumMap.put(MAKE, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setMake));
		enumMap.put(MFG_CONTROL, new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setMfgControlled));
		enumMap.put(MIN, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto :: setMin));
		enumMap.put(MAX, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto :: setMax));
		enumMap.put(BSL_CAT, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto ::setBestStockingLevel));
		enumMap.put(QPR, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto ::setQuantityPerRepair));
		enumMap.put(HIST_6, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto :: setHistory6));
		enumMap.put(HIST_12, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto :: setHistory12));
		enumMap.put(HIST_24, new FieldDefinition<RRDto, Long>(CellType.NUMERIC, Long.class, RRDto :: setHistory24));

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
				System.out.println(cell.toString());
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
			headers.add(RRField.findByColumnName(cellValue));
		}

		return headers;
	}

	public void saveInventoryToDatabase(){

	}

	public void saveFileToDirectory(File file, String fileName, String directory) {

	}


}
