package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

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

	public DmsStatus getStockStatus() {
		return CdkStatus.STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return CdkStatus.NS;
	}
}
