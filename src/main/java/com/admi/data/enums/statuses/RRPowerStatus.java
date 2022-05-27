package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;

public enum RRPowerStatus implements DmsStatus {

	STOCK("Stock/Active"),
	ZEROG("Zero Guide/Phase Out"),
	N_STK("Non-Stock");

	private final String statusName;

	private RRPowerStatus(String statusName) {
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
		return List.of(STOCK, ZEROG);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(N_STK);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(STOCK);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(N_STK, ZEROG);
	}

//	public String toString(){
//		return statusName;
//	}

}
