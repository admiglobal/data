/**
 * This class contains helpful methods for reading in spreadsheets, especially using the Apache POI library.
 * Contains methods for reading cells, parsing data of different formats, etc.
 * @author Julia Betzig +JMJ+
 */

package com.admi.data.services;

import com.sun.istack.NotNull;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class SpreadsheetService {

    /**
     * Takes a Cell of NUMERIC or STRING CellType and returns its value as a String.
     * If the cell is blank or null, returns a null String.
     * @param cell the Cell we're reading from
     * @return the cell's value as a String
     */
    public static String translateCellIntoString(Cell cell){
        if(cell == null || cell.getCellType().equals(CellType.BLANK)){ return null; }

        if(cell.getCellType().equals(CellType.STRING)){ return cell.getStringCellValue(); }

        if(cell.getCellType().equals(CellType.NUMERIC)){
            Double numericCellValue = cell.getNumericCellValue();
            //Translating a cell made of digits into a Double will automatically append a ".0" to the end. Remove this.
            return removeDecimalZero(numericCellValue.toString());
        }

        System.out.println("Unable to translate cell value into String. Cell: " + cell);
        return null;
    }

    /**
     * Takes a Cell of NUMERIC or STRING CellType and returns its value as a Double.
     * If the cell is blank or null, returns a null Double.
     * If the value of the cell is a String that can't be parsed into a Double, returns the fallback value.
     * @param cell the Cell we're reading from
     * @param fallbackValue This value is returned if the cell can't be parsed into a Double; usually something like null or 0.
     * @return
     */
    public static Double translateCellIntoDouble(Cell cell, Double fallbackValue){
        if(cell == null || cell.getCellType().equals(CellType.BLANK)){ return null; }

        if(cell.getCellType().equals(CellType.NUMERIC)){ return cell.getNumericCellValue(); }

        if(cell.getCellType().equals(CellType.STRING)){
            String value = cell.getStringCellValue();
            if(value == null || value.equals("")){ return null; }
            try{
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException nfe){
                //return fallbackValue;
            }
        }

        System.out.println("Unable to translate cell value into a Double. Cell: " + cell);
        return fallbackValue;
    }

    /**
     * If the given string is a number ending in ".0", then the ".0" is removed.
     * Otherwise, just returns the argument string.
     * Helpful if your parsed String is a number format that was autocorrected to gain a trailing zero; e.g. "BIN" column in CDK with a value of "17934" will unhappily become "17934.0"
     * @param numberString Any string
     * @return The whole number part of the String, if it is a string of number format with a single trailing zero. Otherwise, returns the argument.
     */
    public static String removeDecimalZero(String numberString){
        if(numberString.matches("[0-9]+.0")){ //A number with trailing decimal zeros
            return numberString.split("\\.")[0];
        }

        return numberString;
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
    public static String getPartNumberDbFormat(String partNumber, int rowHashCode) {
        if(partNumber == null || partNumber.trim().equals("")){
            return "XXXX-" + rowHashCode;
        }

        return makeAlphanumeric(partNumber);
    }

    /**
     * Returns the argument string, having removed any non-alphanumeric characters.
     * @param string An arbitrary string
     * @return A string containing only a-z, A-Z, and 0-9
     */
    public static String makeAlphanumeric(String string){
        if(string == null){ return null; }

        return string.replaceAll("[^a-zA-Z0-9]", "");
    }

    /**
     * Parses a String of one of the given formats into a LocalDate.
     * Accepted formats:
     * 		- "04FEB22" (i.e. Feb 4, 2022)
     * 		- "FEB22" (i.e. February 2022)
     * 		- "-12345*19680-" (i.e. 19680 days after December 30, 1967)
     * 		- "1997-08-12" (i.e. August 12, 1997)
     * 	    - "3/23/2022" (i.e. March 23rd, 2022)
     * If not one of these formats, returns null.
     * Note that a date of format d/M/yyyy may be misread as M/d/yyyy.
     * Years above 75 will be read as "1976" instead of "2076". If by some miracle this code is still being used after 2075, sorry for the headache.
     * @param uglyDate A String with one of the accepted formats, representing a date between Jan 1st, 1976 and Dec 31st, 2075.
     * @return a LocalDate representing this date, or null if unable to parse. If no day information given (as with "FEB22" format), assume 1st of the month.
     */
    public static LocalDate parseUglyDate(String uglyDate){
        if(uglyDate == null || uglyDate.equals("")){ return null; }

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
    public static LocalDate parse1967DateFormat(@NotNull String date){
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
     * Parses an alphanumeric date of format "04FEB22" (Feb 4th, 2022) or "FEB22" (Feb 1st, 2022) into a LocalDate object.
     * Throws an exception if alphanumDate not of this format (including if null).
     * @param alphanumDate
     * @return a LocalDate object represented by the input String.
     */
    public static LocalDate parseAlphanumericDate(@NotNull String alphanumDate){
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
     * Parses a date of the format "M-d-yyyy" or "M-d-yy", with the given delimiter.
     * @param date The String that represents a date
     * @param dateDelimiter The delimiter between date parts, usually either "/" or "-"
     * @return the LocalDate object that corresponds to this date
     */
    public static LocalDate parseAmericanDate(String date, String dateDelimiter){
        try{ //format "M-d-yyyy"
            return parseAmericanDateLongYear(date, dateDelimiter);
        } catch(DateTimeParseException dtpe){
            try{ //format "M-d-yy"
                return parseAmericanDateShortYear(date, dateDelimiter);
            } catch (DateTimeParseException dtpe2){
                System.out.println("Unable to parse the American date \"" + date + "\" with the delimiter \"" + dateDelimiter + "\".");
            }
        }
        return null;
    }

    /**
     * Parses a date of the format "M-d-yy", with the given delimiter.
     * @param date The String that represents a date
     * @param dateDelimiter The delimiter between date parts, usually either "/" or "-"
     * @return the LocalDate object that corresponds to this date
     */
    private static LocalDate parseAmericanDateShortYear(String date, String dateDelimiter){
        String datePattern = "M" + dateDelimiter + "d" + dateDelimiter + "yy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern).withLocale(Locale.US);
        return LocalDate.parse(date, formatter);
    }

    /**
     * Parses a date of the format "M-d-yyyy", with the given delimiter.
     * @param date The String that represents a date
     * @param dateDelimiter The delimiter between date parts, usually either "/" or "-"
     * @return the LocalDate object that corresponds to this date
     */
    private static LocalDate parseAmericanDateLongYear(String date, String dateDelimiter){
        String datePattern = "M" + dateDelimiter + "d" + dateDelimiter + "yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern).withLocale(Locale.US);
        return LocalDate.parse(date, formatter);
    }
}
