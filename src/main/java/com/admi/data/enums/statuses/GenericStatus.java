package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

public enum GenericStatus implements DmsStatus {

	NONE("No Status"),
	STOCK("Stock"),
	NON_STOCK("Non-Stock"),
	SUPPORTED("Supported"),
	UNSUPPORTED("Unsupported"),
	RIM_ACTIVE("RIM Active"),
	RIM_INACTIVE("RIM Inactive");

	private final String statusName;

	GenericStatus(String statusName) {
		if (statusName == null) {
			this.statusName = "No Status";
		} else {
			this.statusName = statusName;
		}
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return GenericStatus.STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return GenericStatus.NON_STOCK;
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of();
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of();
	}

}
