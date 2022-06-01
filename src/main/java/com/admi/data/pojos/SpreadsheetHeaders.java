
package com.admi.data.pojos;

import com.admi.data.services.SpreadsheetService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * A class for describing the headers row of an imported spreadsheet.
 * Especially useful when the header row is not the first row of the spreadsheet
 * @param <F> An InventoryField enum, such as CdkInventoryField
 * @author Julia Betzig +JMJ+
 */
public class SpreadsheetHeaders<F extends Enum<?>> {
    /**
     * A header that we don't recognize for this InventoryField will be null
     */
    public List<F> headerList;
    public int headerRowNum;

    /**
     * Finds the header row for this sheet, even if the header row isn't the first row of the sheet.
     * Theoretically, the header list could be any row in the sheet.
     * Since not every inventory file will have all potential InventoryFields,
     * and since some files may contain unknown fields (which we'll return as null),
     * we consider to have found the header row once three known fields have been found in that row.
     * @param sheet The Sheet that we want to find the headers of
     * @param findByColumnNameFunction The Function that accepts a String and returns an enum instance for this InventoryField, usually named findByColumnName() or of(). This method should return null if this field isn't recognized.
     * @param inventoryFieldValues The list of all enum values for this InventoryField class
     * @throws IllegalArgumentException Thrown if the header row is missing or doesn't match our expected inventoryFieldValues
     */
    public SpreadsheetHeaders(Sheet sheet,
                              Function<String, F> findByColumnNameFunction,
                              F[] inventoryFieldValues)
            throws IllegalArgumentException{

        List<F> headerList = new ArrayList<>();

        int numFieldsNeeded = 3;
        int numFieldsFound = 0;

        for(Row row: sheet){
            headerList = findHeaderList(row, findByColumnNameFunction);

            //make sure our header list has at least three of the given inventory fields
            for(F field : inventoryFieldValues){
                if(headerList.contains(field)) { numFieldsFound++; }

                if(numFieldsFound >= numFieldsNeeded)
                    break;
            }

            if(numFieldsFound >= numFieldsNeeded){
                this.headerRowNum = row.getRowNum();
                break;
            }
        }

        if(numFieldsFound < numFieldsNeeded){
            throw new IllegalArgumentException("The given sheet does not contain the expected header row. Expected the following fields: " + Arrays.toString(inventoryFieldValues));
        }

        this.headerList = headerList;
    }

    private List<F> findHeaderList(Row row, Function<String, F> findByColumnName) {
        Iterator<Cell> cellIterator = row.iterator();
        List<F> headers = new ArrayList<>();

        while(cellIterator.hasNext()) {
            String cellValue = SpreadsheetService.translateCellIntoString(cellIterator.next());
            F field = findByColumnName.apply(cellValue); //Returns the InventoryField with this String value
            headers.add(field);
        }

        return headers;
    }

    //Static getters

    public List<F> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<F> headerList) {
        this.headerList = headerList;
    }

    public int getHeaderRowNum() {
        return headerRowNum;
    }

    public void setHeaderRowNum(int headerRowNum) {
        this.headerRowNum = headerRowNum;
    }
}
