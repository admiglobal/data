package com.admi.data.services;

import com.admi.data.dto.FieldDefinition;
import com.admi.data.dto.RRDto;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.enums.RRField;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.admi.data.enums.RRField.*;

@Service
public class RRImportService {

	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("M/d/yyyy");

	public List<AipInventoryEntity> importInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
		Row topRow = sheet.getRow(0);

		List<RRField> headers = getHeaderList(topRow);
		EnumMap<RRField, FieldDefinition<RRDto, ?>> rrFields = getRRFieldMap();

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
						System.out.println("Cell: " + cell);
						if (field != null)
							System.out.println("Field: " + field);
						e.printStackTrace();
					}
				}

				inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now(), false));
			}
		}
		System.out.println("Row Count: " + inventoryList.size());

		return inventoryList;
	}

	public List<AipInventoryEntity> importCsvInventoryFile(InputStream file, Long dealerId, int dms) throws IOException {
		Reader reader = new InputStreamReader(file);
		CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
		List<AipInventoryEntity> inventory = new ArrayList<>();
		Iterator<CSVRecord> recordIterator = parser.iterator();

		List<RRField> headers = getHeaderList(recordIterator.next().toList());
		System.out.println(headers);

		while (recordIterator.hasNext()) {
			CSVRecord record = recordIterator.next();
			RRDto dto = new RRDto();

			System.out.println(record.toString());

			for (int i = 0; i == record.size(); i++) {
				String value = record.get(i);
				RRField field = headers.get(i);

				if (field != null) {
					setDtoField(value, dto, field.getField());
				}
			}
			inventory.add(dto.toAipInventory(dealerId, LocalDate.now(), false));
		}
		return inventory;
	}

	@SuppressWarnings("Duplicates")
	private EnumMap<RRField, FieldDefinition<RRDto, ?>> getRRFieldMap (){
		EnumMap<RRField, FieldDefinition<RRDto, ?>> enumMap = new EnumMap<>(RRField.class);

		Map<RRField, FieldDefinition<RRDto, ?>> map = new HashMap<>();

		enumMap.put(PART_NO, new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setPartNo));
		enumMap.put(COST, new FieldDefinition<>(CellType.NUMERIC, Double.class, RRDto :: setCostCents));
		enumMap.put(QOH, new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setQuantityOnHand));
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

	private <T, V> void setDtoField(String stringValue, T dto, FieldDefinition<T, V> field) {
		V value = null;

		if (field.getClazz() == String.class) {
			value = (V) stringValue;
		} else if (field.getClazz() == Long.class) {
			value = (V) Long.valueOf(stringValue);
		} else if (field.getClazz() == Double.class) {
//			We are making the assumption that Doubles are only used for the cost of a part.
			Double doubleValue = Double.valueOf(stringValue);
			Long longValue = Math.round(doubleValue * 100);
			value = (V) longValue;
		} else if (field.getClazz() == LocalDate.class) {
			LocalDate date = LocalDate.parse(stringValue, format);
			value = (V) date;
		}
		field.getSetter().accept(dto, value);
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

	private List<RRField> getHeaderList(List<String> headerStrings) {
		List<RRField> headers = new ArrayList<>();

		for(String header : headerStrings) {

			RRField field = RRField.findByColumnName(header);

			System.out.println(header);

			if (field != null) {
				System.out.println("matches " + field.toString());
			}

			headers.add(field);
		}
		return headers;
	}

}
