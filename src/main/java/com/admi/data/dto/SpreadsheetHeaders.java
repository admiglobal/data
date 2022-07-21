
package com.admi.data.dto;

import com.admi.data.enums.CdkInventoryField;
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
    private final Sheet sheet;
    private final Function<String, F> findByColumnNameFunction;
    private final F[] inventoryFieldValues;
    /**
     * A header that we don't recognize for this InventoryField will be null
     */
    public List<F> headerList;
    /**
     * Default is null, since not all sheets have a secondary header list. Any blank or unrecognized fields will be null.
     */
    public List<F> secondaryHeaderList = null;
    /**
     * This represents the rownum (starting count from 1) of the primary (not secondary) header list
     */
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

        //assign simple variables
        this.sheet = sheet;
        this.findByColumnNameFunction = findByColumnNameFunction;
        this.inventoryFieldValues = inventoryFieldValues;

        //begin work of calculating headerRow and headerRowNum
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

    /**
     * Finds the header rows for this sheet that contains a primary and secondary header row.
     * Functions even if the header row isn't the first row of the sheet
     * (theoretically, the header list could be any row in the sheet).
     * It is assumed that the secondary header row will be the row after the primary header row.
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
                              F[] inventoryFieldValues,
                              boolean secondaryHeaderPresent)
            throws IllegalArgumentException{

        this(sheet, findByColumnNameFunction, inventoryFieldValues);
        this.generateSecondaryHeaderList();
    }

    /**
     * Return this object's headerRow and secondaryHeaderRow merged into a single list of InventoryFields.
     * The lists are merged as follows:<br>
     *  - If at index i, one list is null and the other has a value, we assign the non-null value to the return list<br>
     *  - If both lists have a value, the secondaryHeaderRow wins out.<br>
     * @return the merged list of headers
     */
    public List<F> getMergedPrimaryAndSecondaryHeaderRow(){
        if(secondaryHeaderList == null) { return headerList; }

        List<F> mergedHeaderList = new ArrayList<>(headerList.size());

        for(int i = 0; i < headerList.size(); i++){
            //if secondary list is shorter than primary list
            if(i >= secondaryHeaderList.size()){
                mergedHeaderList.add(headerList.get(i));

            } else if(headerList.get(i) != null && secondaryHeaderList.get(i) == null){
                mergedHeaderList.add(headerList.get(i));

            } else if(headerList.get(i) == null && secondaryHeaderList.get(i) != null) {
                mergedHeaderList.add(secondaryHeaderList.get(i));

            } else if(headerList.get(i) != null && secondaryHeaderList.get(i) != null) {
                mergedHeaderList.add(secondaryHeaderList.get(i));

            } else { //if both are null
                mergedHeaderList.add(null);
            }
        }

        return mergedHeaderList;
    }

    /**
     * This internal method iterates the given row and returns the corresponding list of InventoryFields
     */
    private List<F> findHeaderList(Row row, Function<String, F> findByColumnName) {
        List<F> headers = new ArrayList<>();

        for(int i = 0; i < row.getLastCellNum(); i++){
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL); //prevents skipping over blank cells

            String cellValue = SpreadsheetService.translateCellIntoString(cell);
            System.out.println("Cell value: " + cellValue);
            F field = findByColumnName.apply(cellValue); //Returns the InventoryField with this String value
            headers.add(field);
        }

        System.out.println();

        return headers;
    }

    /**
     * If this sheet has secondaryFieldValues, use this function to calculate them, since not calculated by default constructor.
     * It is assumed that the secondary header row is the row directly following the primary header row.
     */
    private void generateSecondaryHeaderList(){
        //Research shows that Apache POI reads in a merged cell as the cell content, then a bunch of nulls
        // (for the rest of the width of the cell)
        // Ideal for me!
        List<F> secondaryHeaderList = findHeaderList(sheet.getRow(headerRowNum+1), findByColumnNameFunction);

        int numFieldsNeeded = 3;
        int numFieldsFound = 0;

        //make sure our header list has at least three of the given inventory fields
        for(F field : inventoryFieldValues){
            if(secondaryHeaderList.contains(field)) { numFieldsFound++; }

            if(numFieldsFound >= numFieldsNeeded)
                break;
        }

        if(numFieldsFound < numFieldsNeeded){
            throw new IllegalArgumentException("The given sheet does not contain the expected secondary header row. " +
                    "Expected the following fields in row number " + (headerRowNum+1) + ": " + Arrays.toString(inventoryFieldValues));
        }

        this.secondaryHeaderList = secondaryHeaderList;

    }

    public int getSecondaryHeaderRowNum(){
        return headerRowNum + 1;
    }

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

    public List<F> getSecondaryHeaderList() {
        return secondaryHeaderList;
    }

    public void setSecondaryHeaderList(List<F> secondaryHeaderList) {
        this.secondaryHeaderList = secondaryHeaderList;
    }
}
