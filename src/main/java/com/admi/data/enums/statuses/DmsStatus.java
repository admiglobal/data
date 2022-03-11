package com.admi.data.enums.statuses;

public interface DmsStatus {

	public String getStatusName();

	public DmsStatus getStockStatus();

	public DmsStatus getNonStockStatus();

	public String toString();

}
