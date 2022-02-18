package com.admi.data.enums;

import com.admi.data.dto.CdkDto;
import com.admi.data.dto.CellDefinition;
import com.admi.data.entities.AipInventoryEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.Arrays;
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
                    CellType.STRING,
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
                    Cell :: getNumericCellValue,
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
                    Integer.class)),
    STATUS("SS",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setStatus,
                    String.class)),
    MNS("MNS",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setMns,
                    Integer.class)),
    MNR("MNR",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setMnr,
                    Integer.class)),
    LAST_SALE("SALEDATE",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getLocalDateTimeCellValue,
                    String.class,
                    CdkDto :: setSaleDate,
                    LocalDate.class)),
    LAST_RECEIPT("RDATE",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getLocalDateTimeCellValue,
                    String.class,
                    CdkDto :: setrDate,
                    LocalDate.class)),
    ENTRY("ENTRY",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getLocalDateTimeCellValue,
                    String.class,
                    CdkDto :: setEntry,
                    LocalDate.class)),
    JAN("JAN",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setJan,
                    Long.class)),
    FEB("FEB",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setFeb,
                    Long.class)),
    MAR("MAR",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setMar,
                    Long.class)),
    APR("APR",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setApr,
                    Long.class)),
    MAY("MAY",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setMar,
                    Long.class)),
    JUN("JUN",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setJun,
                    Long.class)),
    JUL("JUL",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setJul,
                    Long.class)),
    AUG("AUG",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setAug,
                    Long.class)),
    SEP("SEP",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setSep,
                    Long.class)),
    OCT("OCT",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setOct,
                    Long.class)),
    NOV("NOV",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setNov,
                    Long.class)),
    DEC("DEC",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    CdkDto :: setDec,
                    Long.class)),
    YRSL("YRSL",
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getNumericCellValue,
                    String.class,
                    CdkDto :: setYrsl,
                    Integer.class)),
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

