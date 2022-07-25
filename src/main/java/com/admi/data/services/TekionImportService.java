package com.admi.data.services;

import com.admi.data.dto.CellDefinition;
import com.admi.data.dto.TekionDto;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.enums.TekionInventoryField;
import com.admi.data.dto.SpreadsheetHeaders;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class TekionImportService {

    /**
     * Parses a sheet into a list of AipInventoryEntity's for the given dealer
     */
    public List<AipInventoryEntity> importInventory(Sheet sheet, Long dealerId) throws NoSuchFieldException, IllegalAccessException {
        SpreadsheetHeaders<TekionInventoryField> headersObject = new SpreadsheetHeaders<>(sheet,
                TekionInventoryField :: findByColumnName,
                TekionInventoryField.values(),
                true);
        List<TekionInventoryField> headers = headersObject.getMergedPrimaryAndSecondaryHeaderRow();
        int headerRowNum = headersObject.getSecondaryHeaderRowNum();

        List<AipInventoryEntity> inventoryList = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            TekionDto rowDTO = new TekionDto();

            if (row.getRowNum() > headerRowNum) {
                short lastCell = row.getLastCellNum();

                for(int i = 0; i < lastCell; i++){
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); //prevents skipping over blank cells
                    TekionInventoryField field = headers.get(i);

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
     * Sets the value of this cell to the appropriate element in the Tekion DTO
     * @param cell If null, means the cell is blank
     * @param dto
     * @param cellDefinition
     * @param <V> The value to be set
     */
    private <V, W> void setDtoField(Cell cell, TekionDto dto, CellDefinition<TekionDto, V, W> cellDefinition) {
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

        } else if (setterClass == Integer.class) {
            Double d = SpreadsheetService.translateCellIntoDouble(cell, null);
            value = (d == null) ? null : (V) Integer.valueOf(d.intValue());

        } else if (setterClass == Double.class) {
            value = (V) SpreadsheetService.translateCellIntoDouble(cell, null);

        } else if (setterClass == LocalDate.class) {
            LocalDate date;
            String cellValue = SpreadsheetService.translateCellIntoString(cell);
            if(cellValue.equals("-")){
                date = null;
            } else {
                try{ //assume format like "March 3, 2022"
                    date = DateService.parseLongDate(cellValue);
                } catch (IllegalArgumentException iae) {
                    date = null;
                    System.out.println("Unable to parse " + cellValue + " as a LocalDate.");
                }
            }

            value = (V) date;

        } else {
            System.out.println("Given setter class not accounted for in TekionImportService: " + setterClass);
        }

        cellDefinition.getEntitySetter()
                .accept(dto, value);

    }


    public static String getAdmiStatus(String tekionStatus){
        String status = "N";

        switch(tekionStatus){
            case "Active":
                status = "S";
                break;
            case "Non-Stock":
                status = "N";
                break;
        }

        return status;
    }

}

