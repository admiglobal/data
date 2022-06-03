package com.admi.data.services;

import com.admi.data.dto.CellDefinition;
import com.admi.data.dto.RRPowerDto;
import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.enums.RRPowerInventoryField;
import com.admi.data.repositories.CdkDealersRepository;
import com.admi.data.repositories.CdkPartsInventoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RRPowerImportService {
    @Autowired
    AipInventoryService aipInventoryService;

    @Autowired
    CdkPartsInventoryRepository cdkRepo;

    @Autowired
    CdkDealersRepository cdkDealersRepo;

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
            System.out.println("Error with I/O while importing inventory for dealer ID " + dealerId + ": no records were saved.");
            ioe.printStackTrace();
            return inventory;
        }
        Iterator<CSVRecord> recordIterator = parser.iterator();

        List<RRPowerInventoryField> headers = getHeaderList(recordIterator.next().toList());

        while (recordIterator.hasNext()) {
            CSVRecord row = recordIterator.next();
            RRPowerDto dto = new RRPowerDto();


                for (int i = 0; i < row.size(); i++) {
                    String value = row.get(i);
                    RRPowerInventoryField field = headers.get(i);

                    if (field != null) {
                        setDtoField(value, dto, field.getDefinition());
                    }
                }

                if(!dto.isBlankRow()){
                    inventory.add(dto.toAipInventory(dealerId, LocalDate.now()));
//                    System.out.println("DTO: " + dto);
                }
        }
        return inventory;
    }

    private List<RRPowerInventoryField> getHeaderList(List<String> headerStrings) {
        List<RRPowerInventoryField> headers = new ArrayList<>();

        for(String header : headerStrings) {
            RRPowerInventoryField field = null;

            if(header.matches("... YR2")){
                System.out.println("Ignored junk field \"" + header + "\" in R&R Power import file. This column will not be read.");
            } else{
                try{
                    field = RRPowerInventoryField.of(header);
                } catch(IllegalArgumentException iae){
                    System.out.println("Could not map header name \"" + header + "\" to an RRPowerInventoryField. This column will not be read.");
                }
            }

            headers.add(field);
        }
        return headers;
    }


    /**
     * Sets the value of this cell to the appropriate element in the R&R Power DTO
     * @param cellValue
     * @param dto our RRPowerDto that will get its field set
     * @param cellDefinition Defines this cell, which here allows us to access the setter class for this attribute of the DTO
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
            Long l;
            try{
                l = Long.parseLong(cellValue);
            } catch (NumberFormatException nfe){
                System.out.println("Unable to parse \"" + cellValue + "\" as a Long.");
                l = null;
            }
            value = (V) l;

        } else if (setterClass == Double.class) {
            Double d;
            try{
                d = Double.parseDouble(cellValue);
            } catch (NumberFormatException nfe){
                System.out.println("Unable to parse \"" + cellValue + "\" as a Double.");
                d = null;
            }
            value = (V) d;

        } else if (setterClass == LocalDate.class) {
            LocalDate date = SpreadsheetService.parseAmericanDate(cellValue, "/");
            value = (V) date;

        } else {
            System.out.println("Given setter class not accounted for in RRPowerImportService: " + setterClass);
        }

        cellDefinition.getEntitySetter()
                .accept(dto, value);

    }

    /**
     * Given the R&R Power status string, returns the corresponding ADMI status string
     * @param rrPowerStatus The R&R Power status. One of: "STOCK", "N-STK", or "ZEROG"
     * @return The corresponding ADMI status, either "S" or "N"
     */
    public static String getAdmiStatus(String rrPowerStatus) {
        if(rrPowerStatus == null){ return "N"; }

        switch(rrPowerStatus){
            case "STOCK":
            case "ZEROG":
                return "S";
            case "N-STK":
            default:
                return "N";
        }
    }

}
