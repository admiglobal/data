package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

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

	public DmsStatus getStockStatus() {
		return RREraStatus.STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return RREraStatus.NS;
	}
}
