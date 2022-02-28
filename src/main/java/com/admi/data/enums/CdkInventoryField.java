package com.admi.data.enums;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public enum CdkInventoryField {
    PARTNO("PART-NO.",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto::setPartNo,
                    String.class)),
    DESC("DESC",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setDescription,
                    String.class)),
    COST("COST",
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setCostCents,
                    Double.class)),
    BIN("BIN",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setBin,
                    String.class)),
    SOURCE("SO",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setSource,
                    String.class)),
    QOH("O.H.",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setQuantityOnHand,
                    String.class)),
    QOO("O.O.",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setQuantityOnOrder,
                    String.class)),
    STATUS("SS",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setStatus,
                    String.class)),
    MNS("MNS",
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setMns,
                    Long.class)),
    MNR("MNR",
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    CdkDto :: setMnr,
                    Long.class)),
    LAST_SALE("SALEDATE",
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getLocalDateTimeCellValue,
                    LocalDate.class,
                    CdkDto :: setSaleDate,
                    LocalDate.class)),
    LAST_RECEIPT("RDATE",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    LocalDate.class,
                    CdkDto :: setrDate,
                    LocalDate.class)),
    ENTRY("ENTRY",
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getLocalDateTimeCellValue,
                    LocalDate.class,
                    CdkDto :: setEntry,
                    LocalDate.class)),
    JAN("JAN",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setJan,
                    String.class)),
    FEB("FEB",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setFeb,
                    String.class)),
    MAR("MAR",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setMar,
                    String.class)),
    APR("APR",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setApr,
                    String.class)),
    MAY("MAY",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setMay,
                    String.class)),
    JUN("JUN",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setJun,
                    String.class)),
    JUL("JUL",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setJul,
                    String.class)),
    AUG("AUG",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setAug,
                    String.class)),
    SEP("SEP",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setSep,
                    String.class)),
    OCT("OCT",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setOct,
                    String.class)),
    NOV("NOV",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setNov,
                    String.class)),
    DEC("DEC",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    Long.class,
                    CdkDto :: setDec,
                    String.class)),
    YRSL("YRSL",
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    CdkDto :: setYrsl,
                    Long.class)),
    ;

    private final String fieldName;
    private final CellDefinition<CdkDto, ?, ?> definition;
    private static final Map<String, com.admi.data.enums.CdkInventoryField> map = new HashMap<>(values().length, 1);

    private CdkInventoryField(String fieldName, CellDefinition<CdkDto, ?, ?> definition) {
        this.fieldName = fieldName;
        this.definition = definition;
    }

    static {
        for (com.admi.data.enums.CdkInventoryField c : values()) map.put(c.fieldName, c);
    }

    public static com.admi.data.enums.CdkInventoryField of(String fieldName) {
        com.admi.data.enums.CdkInventoryField result = map.get(fieldName);
        if (result == null)
            throw new IllegalArgumentException("Invalid CdkInventoryField: " + fieldName);
        return result;
    }

    /**
     * Copied from RRField--modified because we only have one column name
     */
    public static CdkInventoryField findByColumnName(String columnName) {
        for (CdkInventoryField field : values()) {
            if (field.fieldName.equals(columnName)){
                return field;
            }
        }
        return null;
    }

    public String getFieldName() {
        return fieldName;
    }

    public CellDefinition<CdkDto, ?, ?> getDefinition() {
        return definition;
    }
}

