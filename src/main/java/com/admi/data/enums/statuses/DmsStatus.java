package com.admi.data.enums.statuses;

import java.util.List;

public interface DmsStatus {

	public String getStatusName();

	public List<DmsStatus> getStockStatuses();

	public List<DmsStatus> getNonStockStatuses();

	public String toString();

	public List<DmsStatus> getActiveStatuses();

	public List<DmsStatus> getInactiveStatuses();

}
