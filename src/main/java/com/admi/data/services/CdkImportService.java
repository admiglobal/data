package com.admi.data.services;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.enums.CdkInventoryField;
import com.admi.data.enums.DmsProvider;
import com.admi.data.dto.SpreadsheetHeaders;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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

		aipInventoryService.saveAll(aipInventory, dealerId, paCode, DmsProvider.CDK);

		System.out.println("Imported and processed CDK " + paCode + " Dealer Id: " + dealerId);
	}

	/**
	 * Parses a sheet into a list of AipInventoryEntity's for the given dealer
	 */
	public List<AipInventoryEntity> importInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
		SpreadsheetHeaders<CdkInventoryField> headersObject = new SpreadsheetHeaders<>(sheet,
																					   CdkInventoryField :: findByColumnName,
																					   CdkInventoryField.values());
		List<CdkInventoryField> headers = headersObject.getHeaderList();
		int headerRowNum = headersObject.getHeaderRowNum();

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

					if (field != null) {
						try {
							setDtoField(cell, rowDTO, field.getDefinition());
						} catch(Exception e) {
							System.out.print("Cell: " + cell + " | ");
							System.out.print("Field: " + field + " | ");
							e.printStackTrace();
						}
					}
				}

				if(!rowDTO.isBlankRow()){
					inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now()));
				}
			}
		}

		System.out.println("");
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
			String cellValue = SpreadsheetService.translateCellIntoString(cell);
			value = (V) cellValue;

		} else if (setterClass == Long.class) {
			Double d = SpreadsheetService.translateCellIntoDouble(cell, null);
			value = (d == null) ? null : (V) Long.valueOf(Math.round(d));

		} else if (setterClass == Double.class) {
			value = (V) SpreadsheetService.translateCellIntoDouble(cell, null);

		} else if (setterClass == LocalDate.class) {
			LocalDate date;
			try{
				date = cell.getDateCellValue()
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
			} catch(IllegalStateException ise){ //the cell isn't formatted as a numeric date
				//assume format like "04FEB22", "FEB22", or "-12345*19680-"--otherwise, null
				date = SpreadsheetService.parseUglyDate(cell.getStringCellValue());
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

}
