package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.Arrays;

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
	SUPERSEDED("Replaced", new String[]{"SUPERSEDED"}),
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

	public static PbsStatus findStatus(String statusName) {
		for (PbsStatus status : values()) {
			if (Arrays.asList(status.statusNameArray).contains(statusName)) {
				return status;
			}
		}
		return null;
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return PbsStatus.STOCK;
	}

	public DmsStatus getNonStockStatus() {
		return PbsStatus.TEST;
	}
}
