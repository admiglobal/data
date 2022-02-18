package com.admi.data.services;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import com.admi.data.dto.FieldDefinition;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.enums.CdkInventoryField;
import com.admi.data.services.ProcessService;
import com.admi.data.services.RimHistoryService;
import com.admi.data.services.ZigService;
import com.admi.data.repositories.AipInventoryRepository;
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
//		EnumMap<CdkImportService, FieldDefinition<RRDto, ?>> rrFields = getRRFieldMap();

		List<AipInventoryEntity> inventoryList = new ArrayList<>();

		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			CdkDto rowDTO = new CdkDto();

			if (row.getRowNum() != 0) {
				short lastCell = row.getLastCellNum();

				for(int i = 0; i < lastCell; i++){
//					int i = cell.getColumnIndex();
					Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); //prevents skipping over blank cells
					CdkInventoryField field = headers.get(i); //check this: not sure all headers are included

					try {
						setDtoField(cell, rowDTO, rrFields.get(field));
					} catch(Exception e) {
						System.out.println("Cell: " + cell);
						if (field != null)
							System.out.println("Field: " + field);
						e.printStackTrace();
					}
				}

//				Iterator<Cell> cellIterator = row.cellIterator();
//				while (cellIterator.hasNext()) {
//					Cell cell = cellIterator.next();
//				}

				inventoryList.add(rowDTO.toAipInventory(dealerId, LocalDate.now(), false));
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
			headers.add(CdkInventoryField.findByColumnName(cellValue));
		}

//		System.out.println(headers.toString());
		return headers;
	}

	private <V, W> void setDtoField(Cell cell, CdkDto dto, CellDefinition<CdkDto, V, W> cellDefinition) {
		CellType cellType = cellDefinition.getCellType();
		Class<?> setterClass = cellDefinition.getSetterClass();

		V value = null;

		if (cell.getCellType() == cellType) {
			if (setterClass == String.class) {
				value = (V) cell.getStringCellValue();
			} else if (setterClass == Long.class) {
				Double d = cell.getNumericCellValue();
				value = (V) Long.valueOf(Math.round(d));
			} else if (setterClass == Double.class) {
//				We are making the assumption that Doubles are only used for the cost of a part.
				Long l = Math.round(cell.getNumericCellValue() * 100);
				value = (V) l;
			} else if (setterClass == LocalDate.class) {
				LocalDate date = cell.getDateCellValue()
						.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
				value = (V) date;
			} else {
				System.out.println("Incorrect class for cell: " + cell.toString());
			}
			cellDefinition.getEntitySetter()
					.accept(dto, value);
		}
	}

}
