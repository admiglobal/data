package com.admi.data.enums;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CdkInventoryField {
    PARTNO(new String[] {"PART-NO."},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto::setPartNo,
                    String.class)),
    DESC(new String[] {"DESC"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setDescription,
                    String.class)),
    COST(new String[] {"COST"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setCostCents,
                    Double.class)),
    LIST(new String[] {"LIST"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setList,
                    Double.class)),
    BIN(new String[] {"BIN", "BIN1"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setBin,
                    String.class)),
    SOURCE(new String[] {"SO"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setSource,
                    String.class)),
    QOH(new String[] {"O.H.", "OH", "QOH"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setQuantityOnHand,
                    String.class)),
    QOO(new String[] {"O.O.", "OO", "QOO"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setQuantityOnOrder,
                    String.class)),
    STATUS(new String[] {"SS"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setStatus,
                    String.class)),
    MNS(new String[] {"MNS"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setMns,
                    Long.class)),
    MNR(new String[] {"MNR"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    CdkDto :: setMnr,
                    Long.class)),
    LAST_SALE(new String[] {"SALEDATE"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getLocalDateTimeCellValue,
                    LocalDate.class,
                    CdkDto :: setSaleDate,
                    LocalDate.class)),
    LAST_RECEIPT(new String[] {"RDATE"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    LocalDate.class,
                    CdkDto :: setrDate,
                    LocalDate.class)),
    ENTRY(new String[] {"ENTRY"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getLocalDateTimeCellValue,
                    LocalDate.class,
                    CdkDto :: setEntry,
                    LocalDate.class)),
    JAN(new String[] {"JAN"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setJan,
                    String.class)),
    FEB(new String[] {"FEB"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setFeb,
                    String.class)),
    MAR(new String[] {"MAR"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setMar,
                    String.class)),
    APR(new String[] {"APR"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setApr,
                    String.class)),
    MAY(new String[] {"MAY"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setMay,
                    String.class)),
    JUN(new String[] {"JUN"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setJun,
                    String.class)),
    JUL(new String[] {"JUL"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setJul,
                    String.class)),
    AUG(new String[] {"AUG"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setAug,
                    String.class)),
    SEP(new String[] {"SEP"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setSep,
                    String.class)),
    OCT(new String[] {"OCT"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setOct,
                    String.class)),
    NOV(new String[] {"NOV"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setNov,
                    String.class)),
    DEC(new String[] {"DEC"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setDec,
                    String.class)),
    YRSL(new String[] {"YRSL", "YTD"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    CdkDto :: setYrsl,
                    Long.class)),
    ;

    private final String[] fieldNames;
    private final CellDefinition<CdkDto, ?, ?> definition;
    private static final Map<String, com.admi.data.enums.CdkInventoryField> map = new HashMap<>(values().length, 1);

    private CdkInventoryField(String[] fieldNames, CellDefinition<CdkDto, ?, ?> definition) {
        this.fieldNames = fieldNames;
        this.definition = definition;
    }

    static {
        for (com.admi.data.enums.CdkInventoryField c : values()){
            for(String fieldName: c.fieldNames){
                map.put(fieldName, c);
            }
        }
    }

    public static com.admi.data.enums.CdkInventoryField of(String fieldName) {
        com.admi.data.enums.CdkInventoryField result = map.get(fieldName);
        if (result == null)
            throw new IllegalArgumentException("Invalid CdkInventoryField: " + fieldName);
        return result;
    }

    /**
     * Accounts for more than one possible column name
     */
    public static CdkInventoryField findByColumnName(String columnName) {
        for (CdkInventoryField field : values()) {
            if (Arrays.asList(field.fieldNames).contains(columnName)){
                return field;
            }
        }
        return null;
    }

    public String[] getfieldNames() {
        return fieldNames;
    }

    public CellDefinition<CdkDto, ?, ?> getDefinition() {
        return definition;
    }
}

