package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;
import java.util.Objects;

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

	AutosoftStatus(String statusName) {
		this.statusName = Objects.requireNonNullElse(statusName, "No Status");
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return Y;
	}

	public DmsStatus getNonStockStatus() {
		return S;
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
