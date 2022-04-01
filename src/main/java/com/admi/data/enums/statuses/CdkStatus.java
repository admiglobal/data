package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

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

	private CdkStatus(String statusName) {
		if (statusName == null) {
			this.statusName = "No Status";
		} else {
			this.statusName = statusName;
		}
	}

	public String getStatusName() {
		return statusName;
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
		return List.of(NS, AP, SP, DP);
	}
}
