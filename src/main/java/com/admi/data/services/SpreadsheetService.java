/**
 * This class contains helpful methods for reading in spreadsheets using the Apache POI library.
 * Contains methods for reading cells, parsing data of different formats, etc.
 * @author Julia Betzig +JMJ+
 */

package com.admi.data.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SpreadsheetService {

    /**
     * Takes a Cell of NUMERIC or STRING CellType and returns its value as a String.
     * If the cell is blank or null, returns a null String.
     * @param cell the Cell we're reading from
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
     * If the value of the cell is a String that can't be parsed into a Double, returns the fallback value.
     * @param cell the Cell we're reading from
     * @param fallbackValue This value is returned if the cell can't be parsed into a Double. Usually something like null or 0.
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
     * Returns an alphanumeric part number.
     * If the partNumber argument is null or blank, provides a dummy part number of the format "XXXX-[rowHashCode]".
     * The hashCode is the hash code of the corresponding row, which ensures that this part number uniquely identifies the values in this row.
     * Useful for inventory files where some part numbers may be blank.
     * @param partNumber The original part number found in this row. May be null or blank.
     * @param rowHashCode The hash code of the row where this part number originally appeared.
     * @return If part number is not null or blank, will return the original part number made alphanumeric (removing spaces, punctuation, etc). Otherwise, returns a dummy part number of format XXXX-rowHashCode.
     */
    public static String getModifiedPartNumber(String partNumber, int rowHashCode) {
        if(partNumber == null || partNumber.trim().equals("")){
            return "XXXX-" + rowHashCode;
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
}
