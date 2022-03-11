package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

public enum LightyearStatus implements DmsStatus {

	//	Lightyear Status
	N("Non-Stock"),
	S("Stock/Active"),
	P("Phased Out"),
	X("Manual Order"),
	D("Delete"),
	T("Delete");

	private final String statusName;

	private LightyearStatus(String statusName) {
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
		return LightyearStatus.S;
	}

	public DmsStatus getNonStockStatus() {
		return LightyearStatus.N;
	}
}
