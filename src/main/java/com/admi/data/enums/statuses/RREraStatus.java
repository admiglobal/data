package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

public enum RREraStatus implements DmsStatus {

	STOCK("Stock/Active"),
	AP("Phased Out (AP)"),
	A("A"),
	DP("Phased Out (DP)"),
	DLT("Delete"),
	NS("Non-Stock"),
	MO("Manual Order"),
	NP("Manual Order"),
	RB("Stock/Active"),
	RBH("RBH"),
	OB("Obsolete");

	private final String statusName;

	private RREraStatus(String statusName) {
		if (statusName == null) {
			this.statusName = "Stock/Active";
		} else {
			this.statusName = statusName;
		}
	}

	public String getStatusName() {
		return statusName;
	}

	public List<DmsStatus> getStockStatuses() {
		return List.of(STOCK, AP, MO, DP, RB);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(NS, NP, OB, RBH);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(STOCK, MO, RB);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(NS, AP, DP, NP, OB, RBH);
	}

}
