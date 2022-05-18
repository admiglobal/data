/**
 * A class for describing the headers row of an imported spreadsheet.
 * Especially useful when the header row is not the first row of the spreadsheet
 */

package com.admi.data.pojos;

import com.admi.data.enums.InventoryField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

public class SpreadsheetHeaders<F extends Enum<?>> {
    public List<F> headerList;
    public int headerRowNum;

    /**
     * 	Finds the header list for this sheet, even if the header list isn't the first row of the sheet.
     * 	Theoretically, the header list could be any row in the sheet.
     * @throws IllegalArgumentException Thrown if the header row is missing or doesn't match our expected (F)ields
     */
    public SpreadsheetHeaders(Sheet sheet, Enum<?> inventoryFieldClass, BiFunction<Enum<?>, String, F> findByColumnNameFunction, F[] inventoryFieldValues) throws IllegalArgumentException{
        List<F> headerList = new ArrayList<>();
        for(Row row: sheet){
            headerList = getHeaderList(row, inventoryFieldClass, findByColumnNameFunction);

            //make sure our header list has all the Fields it should
            boolean headerRowContainsAllFields = true;
            for(F field : inventoryFieldValues){
                if(!headerList.contains(field)){
                    headerRowContainsAllFields = false;
                    break;
                }
            }

            if(headerRowContainsAllFields){
                this.headerRowNum = row.getRowNum();
                break;
            }
        }

        if(headerList.isEmpty()){
            throw new IllegalArgumentException("The given sheet does not contain the expected header row.");
        }

        this.headerList = headerList;
    }

    private List<F> getHeaderList(Row row, Enum<?> inventoryFieldClass, BiFunction<Enum<?>, String, F> findByColumnName) {
        Iterator<Cell> cellIterator = row.iterator();
        List<F> headers = new ArrayList<>();

        while(cellIterator.hasNext()) {
            String cellValue = cellIterator.next().getStringCellValue();
            F field = findByColumnName.apply(inventoryFieldClass, cellValue); //Returns the InventoryField with this String value
            headers.add(field);
        }

        return headers;
    }
}
