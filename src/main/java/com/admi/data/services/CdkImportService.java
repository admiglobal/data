package com.admi.data.services;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.enums.CdkInventoryField;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import com.sun.istack.NotNull;
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
import java.time.format.DateTimeParseException;
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

		CdkDealersEntity dealer = cdkDealersRepo.findFirstByAdmiDealerIdAndEndDateIsNull(dealerId);
		List<CdkPartsInventoryChild> inventory = cdkRepo.findAllByDealerIdAndInventoryDate(dealer.getDealerId(), localDate);
		List<AipInventoryEntity> aipInventory = inventory
				.stream()
				.map(part -> part.toAipInventoryEntity(dealerId))
				.collect(Collectors.toList());

		aipInventoryService.saveAll(aipInventory, dealerId, paCode);

		System.out.println("Imported and processed CDK " + paCode + " Dealer Id: " + dealerId);
	}

	/**
	 * Parses a sheet into a list of AipInventoryEntity's for the given dealer
	 */
	public List<AipInventoryEntity> importInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
		Headers headersObject = new Headers(sheet);
		List<CdkInventoryField> headers = headersObject.headerList;
		int headerRowNum = headersObject.headerRowNum;


		List<AipInventoryEntity> inventoryList = new ArrayList<>();

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			CdkDto rowDTO = new CdkDto();

			if (row.getRowNum() > headerRowNum) {
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

				if(!rowDTO.isBlankRow()){
					inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now()));
				}
			}
		}

		System.out.println("Row Count: " + inventoryList.size());

		return inventoryList;
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
			Double d = translateCellIntoDouble(cell, null);
			value = (d == null) ? null : (V) Long.valueOf(Math.round(d));

		} else if (setterClass == Double.class) {
			value = (V) translateCellIntoDouble(cell, null);

		} else if (setterClass == LocalDate.class) {
			LocalDate date;
			try{
				date = cell.getDateCellValue()
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
			} catch(IllegalStateException ise){ //the cell isn't formatted as a numeric date
				//assume format like "04FEB22", "FEB22", or "-12345*19680-"--otherwise, null
				date = parseUglyDate(cell.getStringCellValue());
			}
			value = (V) date;

		} else {
			System.out.println("Given setter class not accounted for in CdkImportService: " + setterClass);
		}

		cellDefinition.getEntitySetter()
				.accept(dto, value);

	}


	public static String getAdmiStatus(String cdkStatus){
		String status = "N";

		if(cdkStatus != null){
			switch(cdkStatus){
				case "AP":
				case "MO":
					status = "S";
					break;
				case "SP":
				case "NS":
				case "DEL":
					status = "N";
					break;
			}
		} else{
			status = "S";
		}

		return status;
	}

	public static String getModifiedPartNumber(String partNumber, int hashCode) {
		if(partNumber == null || partNumber.trim().equals("")){
			return "XXXX-" + hashCode;
		} else {
			return makeAlphanumeric(partNumber);
		}

	}

	/**
	 * Returns the argument string, having removed any non-alphanumeric characters.
	 * @param string An arbitrary string
	 * @return A string containing only a-z, A-Z, and 0-9
	 */
	public static String makeAlphanumeric(String string){
		if (string != null) {
			return string.replaceAll("[^a-zA-Z0-9]", "");
		} else {
			return null;
		}
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
			//round to prevent decimal of .0 appended to numeric value
			Long wholeNum = Math.round(cell.getNumericCellValue());
			return wholeNum.toString();
		} else{
			System.out.println("Unable to translate cell value into String. Cell: " + cell);
		}
		return null;
	}

	/**
	 * Takes a Cell of NUMERIC or STRING CellType and returns its value as a Double.
	 * If the cell is blank or null, returns a null Double.
	 * If the value of the cell can't be parsed into a Double, returns the fallback value.
	 * @param cell
	 * @param fallbackValue This value is returned if the cell can't be parsed into a Double.
	 * @return
	 */
	private Double translateCellIntoDouble(Cell cell, Double fallbackValue){
		if(cell == null || cell.getCellType().equals(CellType.BLANK)){
			return null;
		} else if(cell.getCellType().equals(CellType.NUMERIC)){
			return cell.getNumericCellValue();
		} else if(cell.getCellType().equals(CellType.STRING)){
			try{
				return Double.parseDouble(cell.getStringCellValue());
			} catch (NumberFormatException nfe){
				return fallbackValue;
			}
		} else{
			System.out.println("Unable to translate cell value into a Double. Cell: " + cell);
		}
		return fallbackValue;
	}

	/**
	 * Parses a String of one of the given formats into a LocalDate.
	 * Accepted formats:
	 * 		- "04FEB22" (i.e. Feb 4, 2022)
	 * 		- "FEB22" (i.e. February 2022)
	 * 		- "-12345*19680-" (i.e. 19680 days after December 30, 1967)
	 * 		- "1997-08-12" (i.e. August 12, 1997)
	 * If not one of these formats, returns null.
	 * Years above 75 will be read as "1976" instead of "2076". If by some miracle this code is still being used after 2075, sorry for the headache.
	 * @param uglyDate A String with one of the accepted formats, representing a date between Jan 1st, 1976 and Dec 31st, 2075.
	 * @return a LocalDate representing this date, or null if unable to parse. If no day information given (as with "FEB22" format), assume 1st of the month.
	 */
	private LocalDate parseUglyDate(String uglyDate){
		if(uglyDate == null || uglyDate.equals("")){
			return null;
		}

		uglyDate = uglyDate.trim();

		//keep trying formats until it works
		try{ //first, try the easy option of "1997-08-12" format
			return LocalDate.parse(uglyDate);

		} catch (DateTimeParseException dtpe){
			try{ //format of "04FEB22" or "FEB22"
				return parseAlphanumericDate(uglyDate);

			} catch (Exception e){
				try{ //format of "-12345*19680-"
					return parse1967DateFormat(uglyDate);

				} catch(Exception f){
					System.out.println("Unable to parse \"" + uglyDate + "\" as a date.");
				}
			}
		}

		return null;
	}

	/**
	 * Parses a date of the format "-12345*19680-" into a LocalDate object.
	 * Here, "19680" represents days since December 30, 1967.
	 * (The "12345" is just an order number that is disregarded).
	 * Throws an exception if date is not of expected format.
	 * @param date
	 * @return
	 */
	private LocalDate parse1967DateFormat(@NotNull String date){
		date = date.trim().replace("-", "");
		String days = date.split("[*]")[1];
		LocalDate base = LocalDate.of(1967,12,30);
		return base.plusDays( Integer.parseInt(days) );
	}

	/**
	 * Parses a cell's contents that may contain many dates of format "-12345*19680-" into a LocalDate object.
	 * The LocalDate object represents the latest date in the series.
	 * Here, "19680" represents days since December 30, 1967.
	 * (The "12345" is just an order number that is disregarded).
	 * Throws an exception if the argument string does not contain a date of the format "-12345*19680-".
	 * @param contents one or more dates of the format "-12345*19680-". Can contain other substrings as well, as long as separated by whitespace.
	 * @return
	 */
//	private LocalDate parseMany1967DateFormats(@NotNull String contents){
//
//	}

	/**
	 * Parses an alphanumeric date of format "04FEB22" or "FEB22" (Feb 2022) into a LocalDate object.
	 * Throws an exception if alphanumDate not of this format (including if null).
	 * @param alphanumDate
	 * @return a LocalDate object represented by the input String.
	 */
	private LocalDate parseAlphanumericDate(@NotNull String alphanumDate){
		alphanumDate = alphanumDate.trim();
		LocalDate date = null;

		String dayString = null;
		String monthName = null;
		String shortYear = null;

		if(alphanumDate.length() == 7){ //format of 04FEB22
			dayString = alphanumDate.substring(0,2);
			monthName = alphanumDate.substring(2,5);
			shortYear = alphanumDate.substring(5,7);
		} else if(alphanumDate.length() == 5){ //format of FEB22
			dayString = "1"; //assume worst-case scenario of 1st of the month (earliest)
			monthName = alphanumDate.substring(0,3);
			shortYear = alphanumDate.substring(3,5);
		} else{ //other formats not accepted
			return null;
		}

		int day = Integer.parseInt(dayString);
		Month month = null;
		for(Month m: Month.values()){
			String monthAbbreviation = m.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
			if(monthName.equals(monthAbbreviation)){
				month = m;
				break;
			}
		}

		int year = Integer.parseInt(shortYear);
		if(year > 75){
			year = year + 1900;
		} else{
			year = year + 2000;
		}

		return LocalDate.of(year, month, day);
	}


	/**
	 * A sub-class for describing the headers row of the spreadsheet
	 */
	private class Headers {
		public List<CdkInventoryField> headerList;
		public int headerRowNum;

		/**
		 * 	Finds the header list for this sheet, even if the header list isn't the first row of the sheet.
		 * 	Theoretically, the header list could be any row in the sheet.
		 * @throws IllegalArgumentException Thrown if the header row is missing or doesn't match our expected CdkInventoryFields
		 */
		public Headers(Sheet sheet) throws IllegalArgumentException{
			List<CdkInventoryField> headerList = new ArrayList<>();
			for(Row row: sheet){
				headerList = getHeaderList(row);
				if(headerList.contains(CdkInventoryField.PARTNO) //checking first two should suffice
						&& headerList.contains(CdkInventoryField.DESC)){
					this.headerRowNum = row.getRowNum();
					break;
				}
			}

			if(headerList.isEmpty()){
				throw new IllegalArgumentException("The given sheet does not contain the expected header row.");
			}

			this.headerList = headerList;
		}

		private List<CdkInventoryField> getHeaderList(Row row) {
			Iterator<Cell> cellIterator = row.iterator();
			List<CdkInventoryField> headers = new ArrayList<>();

			while(cellIterator.hasNext()) {
				String cellValue = cellIterator.next().getStringCellValue();
				CdkInventoryField field = CdkInventoryField.findByColumnName(cellValue);
				headers.add(field);
			}

			return headers;
		}
	}

}
