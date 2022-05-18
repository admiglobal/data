package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;
import java.util.Objects;

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
		this.statusName = Objects.requireNonNullElse(statusName, "No Status");
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return NON_STOCK;
	}

	public List<DmsStatus> getStockStatuses() {
		return List.of(STOCK);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(NON_STOCK);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(STOCK);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(NON_STOCK);
	}


}
