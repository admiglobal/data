package com.admi.data.enums;

public enum RRField implements InventoryField {

	PART_NO("PART-NO", "partNo"),
	QOH("QOH", "qoh"),
	EX_VALUE("EX-VALUE", "costCents"),
	SRC("SRC", "source"),
	MNS("MNS", "monthNoSale"),
	STAT("STAT", "status"),
	ENTRY("ENTRY", "entryDate"),
	BSL_CAT("BSL-CAT", "bsl"),
	HIST_6("HIST-6", "history6"),
	HIST_12("HIST-12", "history12"),
	HIST_24("HIST-24", "history24"),
	MIN("MIN", "min"),
	MAX("MAX", "max");

	private final String columnName;
	private final String fieldName;

	RRField(String columnName, String field) {
		this.columnName = columnName;
		this.fieldName = field;
	}

	public static RRField findByColumnName(String columnName) {
		for (RRField field : values()) {
			if (field.columnName.equals(columnName)) {
				return field;
			}
		}
		return null;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getFieldName() {
		return fieldName;
	}
}
