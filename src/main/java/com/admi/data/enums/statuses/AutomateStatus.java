package com.admi.data.enums.statuses;

import com.admi.data.enums.statuses.DmsStatus;

import java.util.List;
import java.util.Objects;

public enum AutomateStatus implements DmsStatus {

	//  Automate Status,
	AUTO_PHASE_OUT("Auto Phase Out"),
	MANUAL("Manual Order"),
	NOT_STOCKED("Non-Stock"),
	STOCKED("Stock/Active"),
	PHASE_OUT("Phased Out");

	private final String statusName;

	AutomateStatus(String statusName) {
		this.statusName = Objects.requireNonNullElse(statusName, "No Status");
	}

	public String getStatusName() {
		return statusName;
	}

	public DmsStatus getStockStatus() {
		return STOCKED;
	}

	public DmsStatus getNonStockStatus() {
		return NOT_STOCKED;
	}

	public List<DmsStatus> getStockStatuses() {
		return List.of(STOCKED, AUTO_PHASE_OUT, PHASE_OUT, MANUAL);
	}

	public List<DmsStatus> getNonStockStatuses() {
		return List.of(NOT_STOCKED);
	}

	public List<DmsStatus> getActiveStatuses() {
		return List.of(STOCKED, MANUAL);
	}

	public List<DmsStatus> getInactiveStatuses() {
		return List.of(NOT_STOCKED, AUTO_PHASE_OUT);
	}

}
