package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

public enum AutosoftStatus implements DmsStatus {

	//	Autosoft Status
	Y("Stock/Active"),
	S("Non Stock/Special Order"),
	N("Manual Order"),
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

	public List<DmsStatus> getStockStatuses() {
		return List.of(Y, N, C, R);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(S, L, O, P);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(Y, N);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(S, L, O, P);
	}
}
