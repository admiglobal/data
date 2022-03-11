package com.admi.data.enums.statuses;

public enum KpiTitle {

	SUPPORTED("Supported"),
	UNSUPPORTED("Unsupported"),
	RIM_ACTIVE("RIM Active"),
	RIM_INACTIVE("RIM Inactive"),
	NON_RIM("Non-RIM");

	KpiTitle(String title) {
		this.title = title;
	}

	private final String title;

	public String getTitle() {
		return title;
	}
}
