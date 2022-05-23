package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;
import java.util.Objects;

public enum LightyearStatus implements DmsStatus {

	//	Lightyear Status
	N("Non-Stock"),
	S("Stock/Active"),
	P("Phased Out"),
	X("Manual Order"),
	D("Delete"),
	T("Delete");

	private final String statusName;

	LightyearStatus(String statusName) {
		this.statusName = Objects.requireNonNullElse(statusName, "No Status");
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return S;
	}

	public DmsStatus getNonStockStatus() {
		return N;
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
