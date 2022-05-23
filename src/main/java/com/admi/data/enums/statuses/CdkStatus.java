package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;
import java.util.Objects;

public enum CdkStatus implements DmsStatus {

	//	CDK Status
	STOCK("Stock/Active"),
	AP("Phased Out (AP)"),
	DP("Phased Out (DP)"),
	NS("Non-Stock"),
	SP("Special"),
	MO("Manual Order"),
	DEL("Delete");

	private final String statusName;

	CdkStatus(String statusName) {
		this.statusName = Objects.requireNonNullElse(statusName, "No Status");
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return NS;
	}

	public List<DmsStatus> getStockStatuses() {
		return List.of(STOCK, AP, MO, DP);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(NS, SP);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(STOCK, MO);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(NS, AP, SP);
	}
}
