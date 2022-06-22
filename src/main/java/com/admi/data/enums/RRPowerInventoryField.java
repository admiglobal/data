package com.admi.data.enums;

import com.admi.data.dto.RRPowerDto;
import com.admi.data.dto.CellDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public enum RRPowerInventoryField{
    OCT(new String[] {"OCT"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setOct,
                    Long.class)),
    NOV(new String[] {"NOV"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setNov,
                    Long.class)),
    DEC(new String[] {"DEC"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setDec,
                    Long.class)),
    JAN(new String[] {"JAN"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setJan,
                    Long.class)),
    FEB(new String[] {"FEB"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setFeb,
                    Long.class)),
    MAR(new String[] {"MAR"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setMar,
                    Long.class)),
    TWELVE_MONTH_SALES_YR1(new String[] {"12 Month Sales YR1"},
            false,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setTwelveMonthSalesYr1,
                    Long.class)),
    NET(new String[] {"Net"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setNet,
                    String.class)), //this is a String because sometimes formatted as "$5.49", sometimes as "549"
    QOH(new String[] {"On-Hand"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setQuantityOnHand,
                    Long.class)),
    ON_HAND_VALUE(new String[] {"On-Hand Value"},
            false,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Double.class,
                    RRPowerDto :: setOnHandValue,
                    Double.class)),
    BACKORDER(new String[] {"Backorder"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setBackorder,
                    Long.class)),
    OUTSTANDING(new String[] {"Outstanding"},
            false,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setOutstanding,
                    Long.class)),
    MINIMUM(new String[] {"Minimum"},
            false,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setMinimum,
                    Long.class)),
    MAXIMUM(new String[] {"Maximum"},
            false,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setMaximum,
                    Long.class)),
    SOURCE(new String[] {"Source"},
            true,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto :: setSource,
                    String.class)),
    PART_NUMBER(new String[] {"Part"},
            true,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto::setPartNumber,
                    String.class)),
    GROUP(new String[] {"Group"},
            false,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto::setGroup,
                    String.class)),
    BIN(new String[] {"Bin"},
            true,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto :: setBin,
                    String.class)),
    ENTRY(new String[] {"Entry"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getLocalDateTimeCellValue,
                    LocalDate.class,
                    RRPowerDto :: setEntry,
                    LocalDate.class)),
    LAST_SALE_DATE(new String[] {"Last Sale Date"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getLocalDateTimeCellValue,
                    LocalDate.class,
                    RRPowerDto :: setLastSaleDate,
                    LocalDate.class)),
    OVERALL_STATUS(new String[] {"Overall Status"},
            true,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto :: setOverallStatus,
                    String.class)),
    RECEIPT_DATE(new String[] {"Receipt Date"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    LocalDate.class,
                    RRPowerDto :: setReceiptDate,
                    LocalDate.class)),
    RIM_STATE(new String[] {"RIM State"},
            true,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto :: setRimState,
                    String.class)),
    MAY(new String[] {"MAY"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setMay,
                    Long.class)),
    APR(new String[] {"APR"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getStringCellValue,
                    Long.class,
                    RRPowerDto :: setApr,
                    Long.class)),
    //Jay says this is a junk field--ignore it
//    MAR_YR2(new String[] {"MAR YR2"},
//            new CellDefinition<>(
//                    CellType.NUMERIC,
//                    Cell :: getStringCellValue,
//                    Long.class,
//                    RRPowerDto :: setMarYr2,
//                    Long.class)),
    SEP(new String[] {"SEP"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setSep,
                    Long.class)),
    DESCRIPTION(new String[] {"Description"},
            true,
            new CellDefinition<>(
                    CellType.STRING,
                    Cell :: getStringCellValue,
                    String.class,
                    RRPowerDto :: setDescription,
                    String.class)),
    AUG(new String[] {"AUG"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setAug,
                    Long.class)),
    JUL(new String[] {"JUL"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setJul,
                    Long.class)),
    JUN(new String[] {"JUN"},
            true,
            new CellDefinition<>(
                    CellType.NUMERIC,
                    Cell :: getNumericCellValue,
                    Long.class,
                    RRPowerDto :: setJun,
                    Long.class))
    ;

    private final String[] fieldNames;
    /**
     * A field is considered critical when we use it in the RRPowerDto method toAipInventory()
     */
    private final boolean criticalField;
    private final CellDefinition<RRPowerDto, ?, ?> definition;
    private static final Map<String, RRPowerInventoryField> map = new HashMap<>(values().length, 1);

    RRPowerInventoryField(String[] fieldNames, boolean criticalField, CellDefinition<RRPowerDto, ?, ?> definition) {
        this.fieldNames = fieldNames;
        this.criticalField = criticalField;
        this.definition = definition;
    }

    static {
        for (com.admi.data.enums.RRPowerInventoryField c : values()){
            for(String fieldName: c.fieldNames){
                map.put(fieldName, c);
            }
        }
    }

    /**
     * Returns the RRPowerInventoryField enum that corresponds to this field name.
     * @param fieldName A String corresponding to a field name. This class accounts for multiple possible names for a column
     * @return The corresponding RRPowerInventoryField enum constant
     */
    public static RRPowerInventoryField of(String fieldName) {
        RRPowerInventoryField result = map.get(fieldName);
        if (result == null)
            throw new IllegalArgumentException("Invalid RRPowerInventoryField: " + fieldName);
        return result;
    }

    public String[] getfieldNames() {
        return fieldNames;
    }

    public CellDefinition<RRPowerDto, ?, ?> getDefinition() {
        return definition;
    }
}
