package com.admi.data.services;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.enums.CdkInventoryField;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CdkImportService {

	@Autowired
	AipInventoryService aipInventoryService;

	@Autowired
	CdkPartsInventoryRepository cdkRepo;

	@Autowired
	CdkDealersRepository cdkDealersRepo;

	/**
	 * Copies a CDK dealer's inventory from CDK_PARTS_INVENTORY table into AIP_INVENTORY table
	 */
	@Async("asyncCdkExecutor")
	public void importInventory(Long dealerId, LocalDate localDate, String paCode) {

		CdkDealersEntity dealer = cdkDealersRepo.findAllByAdmiDealerId(dealerId);
		List<CdkPartsInventoryChild> inventory = cdkRepo.findAllByDealerIdAndInventoryDate(dealer.getDealerId(), localDate);
		List<AipInventoryEntity> aipInventory = inventory.stream().map(part -> part.toAipInventoryEntity(dealerId)).collect(Collectors.toList());

		aipInventoryService.saveAll(aipInventory, dealerId, paCode);

		System.out.println("Imported and processed CDK " + paCode + " Dealer Id: " + dealerId);
	}

	/**
	 * Copied over from RRImportService
	 */
	public List<AipInventoryEntity> importInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
		Row topRow = sheet.getRow(0);

		List<CdkInventoryField> headers = getHeaderList(topRow);

		List<AipInventoryEntity> inventoryList = new ArrayList<>();

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			CdkDto rowDTO = new CdkDto();

			if (row.getRowNum() != 0) {
				short lastCell = row.getLastCellNum();

				for(int i = 0; i < lastCell; i++){
					Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); //prevents skipping over blank cells
					CdkInventoryField field = headers.get(i);

					try {
						setDtoField(cell, rowDTO, field.getDefinition());
					} catch(Exception e) {
						System.out.println("Cell: " + cell);
						if (field != null)
							System.out.println("Field: " + field);
						e.printStackTrace();
					}
				}

				inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now()));
			}
		}

		System.out.println("Row Count: " + inventoryList.size());

		return inventoryList;
	}

	private List<CdkInventoryField> getHeaderList(Row row) {
		Iterator<Cell> cellIterator = row.iterator();
		List<CdkInventoryField> headers = new ArrayList<>();

		while(cellIterator.hasNext()) {
			String cellValue = cellIterator.next().getStringCellValue();
//			System.out.println(cellValue);
			CdkInventoryField field = CdkInventoryField.findByColumnName(cellValue);
			headers.add(field);
		}

//		System.out.println(headers.toString());
		return headers;
	}

	/**
	 * Sets the value of this cell to the appropriate element in the Cdk DTO
	 * @param cell If null, means the cell is blank
	 * @param dto
	 * @param cellDefinition
	 * @param <V> The value to be set
	 */
	private <V, W> void setDtoField(Cell cell, CdkDto dto, CellDefinition<CdkDto, V, W> cellDefinition) {
		if(cell == null){
			return; //cell is blank: ok to leave null in dto, too
		}

		Class<?> setterClass = cellDefinition.getSetterClass();

		V value = null;

		if (setterClass == String.class) {
			String cellValue = translateCellIntoString(cell);
			value = (V) cellValue;

		} else if (setterClass == Long.class) {
			Double d = cell.getNumericCellValue();
			value = (V) Long.valueOf(Math.round(d));

		} else if (setterClass == Double.class) {
			Double d = cell.getNumericCellValue();
			value = (V) d;

		} else if (setterClass == LocalDate.class) {
			LocalDate date;
			try{
				date = cell.getDateCellValue()
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
			} catch(IllegalStateException ise){ //the cell isn't formatted as a date
				//assume format like "04FEB22"--otherwise, null
				date = parseUglyDate(cell.getStringCellValue());
			}
			value = (V) date;

		} else {
			System.out.println("Given setter class not accounted for in CdkImportService: " + setterClass);
		}

		cellDefinition.getEntitySetter()
				.accept(dto, value);

	}

	/**
	 * Takes a Cell of NUMERIC or STRING CellType and returns its value as a String.
	 * If the cell is blank or null, returns a null String.
	 * @param cell
	 * @return the cell's value as a String
	 */
	private String translateCellIntoString(Cell cell){
		if(cell == null || cell.getCellType().equals(CellType.BLANK)){
			return null;
		} else if(cell.getCellType().equals(CellType.STRING)){
			return cell.getStringCellValue();
		} else if(cell.getCellType().equals(CellType.NUMERIC)){
			Double num = cell.getNumericCellValue();
			return num.toString();
		} else{
			System.out.println("Unable to translate cell value into String. Cell: " + cell);
		}
		return null;
	}

	/**
	 * Parses a String of format like "04FEB22" (i.e. Feb 4, 2022) into a LocalDate.
	 * If incorrect format, returns null.
	 * **If by some miracle this code is still being used in the 22nd century, sorry for the headache.
	 * @param uglyDate A String with format ddMMMyy (e.g. "04FEB22" for Feb 4, 2022)
	 * @return a LocalDate representing this date, or null if unable to parse.
	 */
	private LocalDate parseUglyDate(String uglyDate){
		LocalDate date = null;
		try{
			uglyDate = uglyDate.trim();
			String dayString = uglyDate.substring(0,2);
			String monthName = uglyDate.substring(2,5);
			String shortYear = uglyDate.substring(5,7);

			int day = Integer.parseInt(dayString);
			Month month = null;
			for(Month m: Month.values()){
				String monthAbbreviation = m.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
				if(monthName.equals(monthAbbreviation)){
					month = m;
					break;
				}
			}
			int year = Integer.parseInt("20" + shortYear);

			date = LocalDate.of(year, month, day);

		} catch(Exception e){
			System.out.println("Unable to parse " + uglyDate + " as a date.");
			e.printStackTrace();
		}

		return date;

	}

}
