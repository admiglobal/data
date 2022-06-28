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

		List<AipInventoryEntity> inventoryList = new ArrayList<>();

		Exception e = null;

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

					if (field == null)
						break;

					try {
						setDtoField(cell, rowDTO, field.getField());
					} catch(Exception f) {
						e = f;
//						System.out.println("Cell: " + cell);
//						if (field != null)
//							System.out.println("Field: " + field);
//						e.printStackTrace();
					}
				}

				inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now(), true));
			}
		}

		if (e != null) {
			System.out.println("Check error log for " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Row Count: " + inventoryList.size());

		return inventoryList;
	}

	public List<AipInventoryEntity> importCsvInventoryFile(InputStream file, Long dealerId) throws IOException {
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
			inventory.add(dto.toAipInventory(dealerId, LocalDate.now(), true));
		}
		return inventory;
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

			headers.add(RRField.findByColumnName(cellValue));
		}
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
