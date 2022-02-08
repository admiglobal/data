package com.admi.data.enums;

import com.admi.data.dto.CellDefinition;
import com.admi.data.entities.AipInventoryEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public enum UdbInventoryField {

	PA_CODE("P&A",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					AipInventoryEntity :: setPaCode,
					String.class)),
	PART_NUMBER("Part #",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					AipInventoryEntity :: setPartNo,
					String.class)),
	PART_STATUS("Part Status (Stocking)",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					AipInventoryEntity :: setStatus,
					String.class)),
	QOH("Qty OH",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getNumericCellValue,
					String.class,
					AipInventoryEntity :: setQoh,
					Integer.class)),
	LAST_SALE_DATE("Part Last Sale Date",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getLocalDateTimeCellValue,
					String.class,
					AipInventoryEntity :: setLastSale,
					LocalDate.class)),
	LAST_RECEIPT_DATE("Qty OH Last Change Date",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getLocalDateTimeCellValue,
					String.class,
					AipInventoryEntity :: setLastReceipt,
					LocalDate.class)),
	BIN_LOCATION("BIN Location",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					AipInventoryEntity :: setBin,
					String.class)),
	QOO("Qty OO",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getNumericCellValue,
					String.class,
					AipInventoryEntity :: setQoo,
					Integer.class)),
	RIM_STATUS("RIM Status",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					AipInventoryEntity :: setMfgControlledFromString,
					String.class)),
	TWELVE_MONTH_SALES("12 Month Sales",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getNumericCellValue,
					String.class,
					AipInventoryEntity :: setTwelveMonthSales,
					Integer.class)),
	COST("Cost",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getNumericCellValue,
					String.class,
					AipInventoryEntity :: setCentsFromDouble,
					Double.class)),
	SERVICE_PART_NUMBER("MC-Ford ServicePart",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					null,
					null)),
	MC_PART_NUMBER("MC ServicePart",
			new CellDefinition<>(
					CellType.STRING,
					Cell :: getStringCellValue,
					String.class,
					null,
					null));

	private final String fieldName;
	private final CellDefinition<AipInventoryEntity, ?, ?> definition;
	private static final Map<String, UdbInventoryField> map = new HashMap<>(values().length, 1);

	private UdbInventoryField(String fieldName, CellDefinition<AipInventoryEntity, ?, ?> definition) {
		this.fieldName = fieldName;
		this.definition = definition;
	}

	static {
		for (UdbInventoryField u : values()) map.put(u.fieldName, u);
	}

	public static UdbInventoryField of(String fieldName) {
		UdbInventoryField result = map.get(fieldName);
		if (result == null)
			throw new IllegalArgumentException("Invalid UdbInventoryField: " + fieldName);
		return result;
	}

	public String getFieldName() {
		return fieldName;
	}

	public CellDefinition<AipInventoryEntity, ?, ?> getDefinition() {
		return definition;
	}
}
