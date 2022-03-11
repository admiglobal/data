package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

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

	public DmsStatus getStockStatus() {
		return DealerTrackStatus.A;
	}

	public DmsStatus getNonStockStatus() {
		return DealerTrackStatus.N;
	}
}
