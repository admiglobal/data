package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

public enum DealerTrackStatus implements DmsStatus {

	//	DealerTrack Status
	N("Non-Stock"),
	A("Stock/Active"),
	C("Cores"),
	R("Replaced"),
	P("Phase Out"),
	NO_STATUS("No Status");

	private final String statusName;

	private DealerTrackStatus(String statusName) {
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
		return List.of(A, P, R);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(N, C);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(A);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(N, P, R);
	}
}
