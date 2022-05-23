package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;
import java.util.Objects;

public enum DealerTrackStatus implements DmsStatus {

	//	DealerTrack Status
	N("Non-Stock"),
	A("Stock/Active"),
	C("Cores"),
	R("Replaced"),
	P("Phase Out"),
	NO_STATUS("No Status");

	private final String statusName;

	DealerTrackStatus(String statusName) {
		this.statusName = Objects.requireNonNullElse(statusName, "No Status");
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return A;
	}

	public DmsStatus getNonStockStatus() {
		return N;
	}

	public List<DmsStatus> getStockStatuses() {
		return List.of(A, P, R);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(N, C);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(A);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(N, P);
	}
}
