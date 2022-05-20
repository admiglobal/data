package com.admi.data.services;

import com.admi.data.dto.CellDefinition;
import com.admi.data.dto.RRDto;
import com.admi.data.dto.RRPowerDto;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.entities.CdkDealersEntity;
import com.admi.data.entities.CdkPartsInventoryChild;
import com.admi.data.enums.RRField;
import com.admi.data.enums.RRPowerInventoryField;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import com.sun.istack.NotNull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RRPowerImportService {
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
     * Parses a CSV file into a list of AipInventoryEntities for the given dealer
     */
    public List<AipInventoryEntity> importCsvInventoryFile(InputStream file, Long dealerId) {
        List<AipInventoryEntity> inventory = new ArrayList<>();
        Reader reader;
        CSVParser parser;
        
        try{
            reader = new InputStreamReader(file);
            parser = new CSVParser(reader, CSVFormat.DEFAULT);
        } catch (IOException ioe){
            System.out.println("Error with I/O while importing inventory: no records were saved.");
            ioe.printStackTrace();
            return inventory;
        }
        Iterator<CSVRecord> recordIterator = parser.iterator();

        List<RRPowerInventoryField> headers = getHeaderList(recordIterator.next().toList());
        System.out.println(headers);

        while (recordIterator.hasNext()) {
            CSVRecord row = recordIterator.next();
            RRPowerDto dto = new RRPowerDto();

            if(row.getRecordNumber() < 15){
                System.out.println("Record: " + row.toString());
            }
            
            System.out.println("Approaching for loop...");

            for (int i = 0; i < row.size(); i++) {
                System.out.println("inside for loop...");
                String value = row.get(i);
                RRPowerInventoryField field = headers.get(i);
                
                System.out.println("RRInventoryField: " + field + "; cell value: " + value);

                if (field != null) {
                    setDtoField(value, dto, field.getDefinition());
                    System.out.println("Saved to DTO!");
                } else{
                    System.out.println("The field was null, so we didn't save it to the dto.");
                }
            }
            
            System.out.println("outside for loop");

            if(!dto.isBlankRow()){
                inventory.add(dto.toAipInventory(dealerId, LocalDate.now()));
                System.out.println("saved to aip inventory!");
            } else{
                System.out.println("it's a blank row, so we didn't bother saving it to an aipInventoryEntity");
            }
        }
        return inventory;
    }

    private List<RRPowerInventoryField> getHeaderList(List<String> headerStrings) {
        List<RRPowerInventoryField> headers = new ArrayList<>();

        for(String header : headerStrings) {

            RRPowerInventoryField field = RRPowerInventoryField.of(header);

//            System.out.println(header);

//            if (field != null) {
//                System.out.println("matches " + field.toString());
//            }

            headers.add(field);
        }
        return headers;
    }


    /**
     * Sets the value of this cell to the appropriate element in the R&R Power DTO
     * @param cellValue
     * @param dto our RRPowerDto that will get its field set
     * @param cellDefinition
     * @param <V> The value to be set
     * @param <W> We don't care about this for our purposes here.
     */
    private <V, W> void setDtoField(String cellValue, RRPowerDto dto, CellDefinition<RRPowerDto, V, W> cellDefinition) {
        if(cellValue == null || cellValue.equals("")){ return; } //cellValue is blank: ok to leave null in dto, too

        Class<?> setterClass = cellDefinition.getSetterClass();

        V value = null;

        if (setterClass == String.class) {
            value = (V) cellValue;

        } else if (setterClass == Long.class) {
            Long l = Long.parseLong(cellValue);
            value = (V) l;

        } else if (setterClass == Double.class) {
            Double d = Double.parseDouble(cellValue);
            value = (V) d;

        } else if (setterClass == LocalDate.class) {
            LocalDate date = SpreadsheetService.parseUglyDate(cellValue);
            value = (V) date;

        } else {
            System.out.println("Given setter class not accounted for in RRPowerImportService: " + setterClass);
        }

        cellDefinition.getEntitySetter()
                .accept(dto, value);

    }


    /**
     * A sub-class for describing the headers row of the spreadsheet
     */
    private class Headers {
        public List<RRPowerInventoryField> headerList;
        public int headerRowNum;

        /**
         * 	Finds the header list for this sheet, even if the header list isn't the first row of the sheet.
         * 	Theoretically, the header list could be any row in the sheet.
         * @throws IllegalArgumentException Thrown if the header row is missing or doesn't match our expected RRPowerInventoryFields
         */
        public Headers(Sheet sheet) throws IllegalArgumentException{
            List<RRPowerInventoryField> headerList = new ArrayList<>();
            for(Row row: sheet){
                headerList = getHeaderList(row);
                if(headerList.contains(RRPowerInventoryField.PART_NUMBER) //checking two should suffice
                        && headerList.contains(RRPowerInventoryField.QOH)){
                    this.headerRowNum = row.getRowNum();
                    break;
                }
            }

            if(headerList.isEmpty()){
                throw new IllegalArgumentException("The given sheet does not contain the expected header row.");
            }

            this.headerList = headerList;
        }

        private List<RRPowerInventoryField> getHeaderList(Row row) {
            Iterator<Cell> cellIterator = row.iterator();
            List<RRPowerInventoryField> headers = new ArrayList<>();

            while(cellIterator.hasNext()) {
                String cellValue = cellIterator.next().getStringCellValue();
                RRPowerInventoryField field = RRPowerInventoryField.of(cellValue);
                headers.add(field);
            }

            return headers;
        }
    }
}
