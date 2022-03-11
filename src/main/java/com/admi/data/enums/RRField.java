package com.admi.data.enums;

import com.admi.data.dto.FieldDefinition;
import com.admi.data.dto.RRDto;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.util.Arrays;

public enum RRField implements InventoryField {

	PART_NO(new String [] {"PART-NO","PART-NO-1","PART-NO-2", "\uFEFFPART-NO-1"},
			new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setPartNo)),
	COST(new String [] {"BASE-COST","EX-VALUE"},
			new FieldDefinition<>(CellType.NUMERIC, Double.class, RRDto :: setCostCents)),
	QOH(new String [] {"QOH"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setQuantityOnHand)),
	DESC(new String [] {"DESC"},
			new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setDescription)),
	STAT(new String [] {"STAT"},
			new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setStatus)),
	LAST_SALES_DATE(new String [] {"LAST-SLS-DATE"},
			new FieldDefinition<>(CellType.NUMERIC, LocalDate.class, RRDto :: setLastSaleDate)),
	LAST_RECEIPT_DATE(new String [] {"LAST-RECV-DATE"},
			new FieldDefinition<>(CellType.NUMERIC, LocalDate.class, RRDto :: setLastReceiptDate)),
	SRC(new String [] {"SRC"},
			new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setSource)),
	BIN(new String [] {"BIN", "BIN1"},
			new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setBin)),
	MAKE(new String [] {"MAKE"},
			new FieldDefinition<>(CellType.STRING, String.class, RRDto :: setMake)),
//	MFG_CONTROL(new String [] {"MFR-CONTROLLED"},
//			new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setMfgControlled)),
	MIN(new String [] {"MIN"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setMin)),
	MAX(new String [] {"MAX"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setMax)),
	BSL_CAT(new String [] {"BSL-CAT"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setBestStockingLevel)),
	QPR(new String [] {"QPR"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setQuantityPerRepair)),
	HIST_6(new String [] {"HIST-6"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setHistory6)),
	HIST_12(new String [] {"HIST-12"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto :: setHistory12)),
	HIST_24(new String [] {"HIST-24"},
			new FieldDefinition<>(CellType.NUMERIC, Long.class, RRDto:: setHistory24)),
	MFR_STAT(new String [] {"MFR-STAT"},
			new FieldDefinition<RRDto, String>(CellType.STRING, String.class, RRDto :: setMfgControlled));

	private final String [] columnNames;
	private final FieldDefinition<RRDto, ?> field;

	RRField(String[] columnNames, FieldDefinition<RRDto, ?> field) {
		this.columnNames = columnNames;
		this.field = field;
	}

	public static RRField findByColumnName(String columnName) {
		for (RRField field : values()) {
			if (Arrays.asList(field.columnNames).contains(columnName)) {
				return field;
			}
		}
		return null;
	}

	public String [] getColumnNames() {
		return columnNames;
	}

	public FieldDefinition<RRDto, ?> getField() {
		return field;
	}

	@Override
	public String toString() {
		return "RRField{" +
				"columnNames=" + Arrays.toString(columnNames) +
				", field=" + field +
				'}';
	}
}
