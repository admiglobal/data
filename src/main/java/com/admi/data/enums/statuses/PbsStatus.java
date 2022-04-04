package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.Arrays;
import java.util.List;

public enum PbsStatus implements DmsStatus {

	//	PBS Status
//	T("Non-Stock", new String[]{"T"}),
	TEST("Non-Stock", new String[]{"Test", "TEST", "T"}),
//	S("Stock/Active", new String[]{"S"}),
//	STK("Stock/Active", new String[]{"STK"}),
	STOCK("Stock/Active",  new String[]{"STOCK", "STK", "S"}),
	D("Delete", new String[]{"D"}),
	C("Cores", new String[]{"C"}),
	R("Replaced", new String[]{"R"}),
	MANUAL_ORDER("Manual Order", new String[]{"MANUAL ORDER", "MANUAL_ORDER"}),
	MEMO("Memo", new String[]{"MEMO"}),
	SUPERSEDED("Superseded", new String[]{"SUPERSEDED"}),
	O("Other", new String[]{"O"});

	private final String statusName;
	private final String[] statusNameArray;

	private PbsStatus(String statusName, String[] statusNameArray) {
		this.statusNameArray = statusNameArray;
		if (statusName == null) {
			this.statusName = "No Status";
		} else {
			this.statusName = statusName;
		}
	}

	/**
	 * Returns the corresponding PBS status object for this status name.
	 * Accounts for multiple possible names for a status.
	 * Case-insensitive.
	 * @param statusName A string of arbitrary case describing a PBS status
	 * @return The corresponding PBS status object.
	 */
	public static PbsStatus findStatus(String statusName) {
		for (PbsStatus status : values()) {
			if (Arrays.asList(status.statusNameArray).contains(statusName.toUpperCase())) {
				return status;
			}
		}
		return null;
	}

	public String getStatusName() {
		return statusName;
	}

	public List<DmsStatus> getStockStatuses() {
		return List.of(STOCK, R, MANUAL_ORDER, SUPERSEDED);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(TEST, C, O, MEMO);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(STOCK, MANUAL_ORDER);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(TEST, O, R, SUPERSEDED, MEMO);
	}

}
