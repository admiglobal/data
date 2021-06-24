package com.admi.data.enums;

import java.util.Arrays;

public enum RRField implements InventoryField {

	PART_NO(new String [] {"PART-NO","PART-NO-1","PART-NO-2"}),
	COST(new String [] {"BASE-COST","EX-VALUE"}),
	QOH(new String [] {"QOH"}),
	DESC(new String [] {"DESC"}),
	STAT(new String [] {"STAT"}),
	LAST_SALES_DATE(new String [] {"LAST-SLS-DATE"}),
	LAST_RECEIPT_DATE(new String [] {"LAST-RECV-DATE"}),
	SRC(new String [] {"SRC"}),
	BIN(new String [] {"BIN", "BIN1"}),
	MAKE(new String [] {"MAKE"}),
	MFG_CONTROL(new String [] {"MFR-CONTROLLED"}),
	MIN(new String [] {"MIN"}),
	MAX(new String [] {"MAX"}),
	BSL_CAT(new String [] {"BSL-CAT"}),
	QPR(new String [] {"QPR"}),
	HIST_6(new String [] {"HIST-6"}),
	HIST_12(new String [] {"HIST-12"}),
	HIST_24(new String [] {"HIST-24"});

	private final String [] columnNames;

	RRField(String [] columnName) {
		this.columnNames = columnName;
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

}
