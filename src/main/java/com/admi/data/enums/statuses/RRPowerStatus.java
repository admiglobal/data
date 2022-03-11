package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

public enum RRPowerStatus implements DmsStatus {

	STOCK("Stock/Active"),
	AP("Phased Out (AP)"),
	DP("Phased Out (DP)"),
	DLT("Delete"),
	NS("Non-Stock"),
	MO("Manual Order"),
	NP("Manual Order"),
	OB("Obsolete");

	private final String statusName;

	private RRPowerStatus(String statusName) {
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
		return RRPowerStatus.STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return RRPowerStatus.NS;
	}
}
