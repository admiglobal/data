package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

public enum AutomateStatus implements DmsStatus {

	//  Automate Status,
	AUTO_PHASE_OUT("Auto Phase Out"),
	MANUAL("Manual Order"),
	NOT_STOCKED("Non-Stock"),
	STOCKED("Stock/Active"),
	PHASE_OUT("Phased Out");

	private final String statusName;

	private AutomateStatus(String statusName) {
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
		return AutomateStatus.STOCKED;
	}

	public DmsStatus getNonStockStatus() {
		return AutomateStatus.NOT_STOCKED;
	}

}
