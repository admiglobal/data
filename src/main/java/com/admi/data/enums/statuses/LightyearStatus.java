package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

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

	public List<DmsStatus> getStockStatuses() {
		return List.of(S, P, X);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(N);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(S, X);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(N, P);
	}
}
