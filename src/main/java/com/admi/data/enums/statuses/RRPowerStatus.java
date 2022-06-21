package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.Arrays;
import java.util.List;

public enum RRPowerStatus implements DmsStatus {

	STOCK("Stock/Active", new String[]{"STOCK"}),
	ZEROG("Zero Guide/Phase Out", new String[]{"ZEROG"}),
	N_STK("Non-Stock", new String[]{"N-STK", "N_STK"});

	private final String statusName;
	private final String[] statusNameArray;

	private RRPowerStatus(String statusName, String[] statusNameArray) {
		if (statusName == null) {
			this.statusName = "No Status";
		} else {
			this.statusName = statusName;
		}

		if (statusNameArray == null) {
			this.statusNameArray = new String[]{"No Status"};
		} else {
			this.statusNameArray = statusNameArray;
		}
	}

	public String getStatusName() {
		return statusName;
	}

	/**
	 * Similar to valueOf(), except accounts for multiple possible names.
	 * Case-insensitive.
	 */
	public static RRPowerStatus of(String statusName){
		for (RRPowerStatus status : values()) {
			if (Arrays.asList(status.statusNameArray).contains(statusName.toUpperCase())) {
				return status;
			}
		}
		return null;
	}

	public DmsStatus getStockStatus() {
		return STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return N_STK;
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
