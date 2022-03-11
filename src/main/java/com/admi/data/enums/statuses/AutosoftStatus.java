package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

public enum AutosoftStatus implements DmsStatus {

	//	Autosoft Status
	Y("Stock/Active"),
	S("Special Order"),
	N("Non-Stock"),
	C("Old Part Number Change"),
	L("Lost Sale"),
	O("Obsolete"),
	P("Non-Stock"),
	R("Part Number Replacement");

	private final String statusName;

	private AutosoftStatus(String statusName) {
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
		return AutosoftStatus.Y;
	}

	public DmsStatus getNonStockStatus() {
		return AutosoftStatus.N;
	}
}
