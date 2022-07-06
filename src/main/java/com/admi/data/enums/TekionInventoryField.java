package com.admi.data.enums;

import com.admi.data.dto.TekionDto;
import com.admi.data.dto.CellDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TekionInventoryField {
    PART_NUMBER(new String[] {"Part Number"},
            new CellDefinition<>(
                CellType.STRING,
                Cell :: getStringCellValue,
                String.class,
                TekionDto::setPartNumber,
                String.class)),
    DESCRIPTION(new String[] {"Part Description"},
            new CellDefinition<>(
                CellType.STRING,
                Cell :: getStringCellValue,
                String.class,
                TekionDto :: setPartDescription,
                String.class)),
    STOCKING_STATUS(new String[] {"Stocking Status"},
            new CellDefinition<>(
                CellType.STRING,
                Cell :: getStringCellValue,
                String.class,
                TekionDto :: setStockingStatus,
                String.class)),
    ON_HAND_QTY(new String[] {"On Hand Qty"},
            new CellDefinition<>(
                CellType.NUMERIC,
                Cell :: getNumericCellValue,
                Integer.class,
                TekionDto :: setOnHandQty,
                Integer.class)),
    EXCESS_STOCK_QTY(new String[] {"Excess Stock Qty"},
            new CellDefinition<>(
                CellType.NUMERIC,
                Cell :: getNumericCellValue,
                Integer.class,
                TekionDto :: setExcessStockQty,
                Integer.class)),
    ON_HOLD_QTY(new String[] {"On Hold Qty"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setOnHoldQty,
                    Integer.class)),
    ON_ORDER_QTY(new String[] {"On Order Qty"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setOnOrderQty,
                    Integer.class)),
    BINS(new String[] {"Bins"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setBins,
                    String.class)),
    PART_COST_DOLLARS(new String[] {"Part Cost (in $)"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Double.class,
                    TekionDto :: setPartCostDollars,
                    Double.class)),
    CORE_COST_DOLLARS(new String[] {"Core Cost (in $)"},
             new CellDefinition<>(
                     CellType.NUMERIC,
                     Cell :: getNumericCellValue,
                     Double.class,
                     TekionDto :: setCoreCostDollars,
                     Double.class)),
    TOTAL_PART_COST_DOLLARS(new String[] {"Total Part Cost (in $)"},
             new CellDefinition<>(
                     CellType.NUMERIC,
                     Cell :: getNumericCellValue,
                     Double.class,
                     TekionDto :: setTotalPartCostDollars,
                     Double.class)),
    TOTAL_EXTENDED_COST_DOLLARS(new String[] {"Total Extended Cost (in $)"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Double.class,
                    TekionDto :: setTotalExtendedCostDollars,
                    Double.class)),
    TOTAL_EXCESS_STOCK_COST(new String[] {"Total Excess Stock Cost"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Double.class,
                    TekionDto :: setTotalExcessStockCost,
                    Double.class)),
    SOURCE_CODE(new String[] {"Source Code"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setSourceCode,
                    Integer.class)),
    MANUALLY_CONTROLLED(new String[] {"Manually Controlled"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setManuallyControlled,
                    String.class)),
    MANUFACTURER(new String[] {"Manufacturer"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setManufacturer,
                    String.class)),
    MANUFACTURER_CONTROL_CODE(new String[] {"Manufacturer Control Code"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setManufacturerControlCode,
                    String.class)),
    MANUFACTURER_CONTROLLED(new String[] {"Manufacturer Controlled"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setManufacturerControlled,
                    String.class)),
    GROUP_NUMBER(new String[] {"Group Number"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setGroupNumber,
                    String.class)),
    PARTS_CLASSIFICATION_CODE(new String[] {"Parts Classification Code"},
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    TekionDto :: setPartsClassificationCode,
                    String.class)),
    MONTH_NO_SALE(new String[] {"Month No Sale"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setMonthNoSale,
                    Integer.class)),
    MONTH_NO_RECEIPT(new String[] {"Month No Receipt"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setMonthNoReceipt,
                    Integer.class)),
    TWELVE_MONTH_SALE_QTY(new String[] {"12 Month Sale Qty"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setTwelveMonthSaleQty,
                    Integer.class)),
    JAN(new String[] {"Jan'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setJan,
                    Integer.class)),
    FEB(new String[] {"Feb'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setFeb,
                    Integer.class)),
    MAR(new String[] {"Mar'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setMar,
                    Integer.class)),
    APR(new String[] {"Apr'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setApr,
                    Integer.class)),
    MAY(new String[] {"May'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setMay,
                    Integer.class)),
    JUN(new String[] {"Jun'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setJun,
                    Integer.class)),
    JUL(new String[] {"Jul'21"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setJul,
                    Integer.class)),
    AUG(new String[] {"Aug'21"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setAug,
                    Integer.class)),
    SEP(new String[] {"Sep'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setSep,
                    Integer.class)),
    OCT(new String[] {"Oct'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setOct,
                    Integer.class)),
    NOV(new String[] {"Nov'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setNov,
                    Integer.class)),
    DEC(new String[] {"Dec'22"},
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Integer.class,
                    TekionDto :: setDec,
                    Integer.class))
    ;

    private final String[] fieldNames;
    private final CellDefinition<TekionDto, ?, ?> definition;
    private static final Map<String, TekionInventoryField> map = new HashMap<>(values().length, 1);

    private TekionInventoryField(String[] fieldNames, CellDefinition<TekionDto, ?, ?> definition) {
        this.fieldNames = fieldNames;
        this.definition = definition;
    }

    static {
        for (com.admi.data.enums.TekionInventoryField field : values()){
            for(String fieldName: field.fieldNames){
                map.put(fieldName, field);
            }
        }
    }

    public static com.admi.data.enums.TekionInventoryField of(String fieldName) {
        com.admi.data.enums.TekionInventoryField result = map.get(fieldName);
        if (result == null)
            throw new IllegalArgumentException("Invalid TekionInventoryField: " + fieldName);
        return result;
    }

    /**
     * Accounts for more than one possible column name
     */
    public static TekionInventoryField findByColumnName(String columnName) {
        for (TekionInventoryField field : values()) {
            if (Arrays.asList(field.fieldNames).contains(columnName)){
                return field;
            }
        }
        return null;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public CellDefinition<TekionDto, ?, ?> getDefinition() {
        return definition;
    }
}
