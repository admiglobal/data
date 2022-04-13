/**
 * A service for describing the headers row of an imported spreadsheet.
 * Accounts for header row not being the first row of the spreadsheet.
 */

package com.admi.data.services;

import com.admi.data.enums.CdkInventoryField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HeadersService {

    public List<CdkInventoryField> headerList;
    public int headerRowNum;

    /**
     * 	Finds the header list for this sheet, even if the header list isn't the first row of the sheet.
     * 	Theoretically, the header list could be any row in the sheet.
     * @throws IllegalArgumentException Thrown if the header row is missing or doesn't match our expected CdkInventoryFields
     */
    public HeadersService(Sheet sheet) throws IllegalArgumentException{
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
