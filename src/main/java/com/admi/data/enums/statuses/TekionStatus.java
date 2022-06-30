package com.admi.data.enums.statuses;

import java.util.Arrays;
import java.util.List;

public enum TekionStatus {
    ACTIVE("Stock/Active", new String[]{"Active"}),
    IN_ACTIVE("Inactive", new String[]{"In-Active"}), //TODO: Ask Jay what to call these statuses
    NON_STOCK("Non-Stock", new String[]{"Non-Stock"});

    private final String statusName;
    private final String[] statusNameArray;

    private TekionStatus(String statusName, String[] statusNameArray) {
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
    public static TekionStatus of(String statusName){
        for (TekionStatus status : values()) {
            if (Arrays.asList(status.statusNameArray).contains(statusName.toUpperCase()))
                return status;
        }
        return null;
    }

    //TODO: Check with Jay about which statuses are stock, non-stock, active, and inactive
//    public DmsStatus getStockStatus() {
//        return STOCK;
//    }
//
//    public DmsStatus getNonStockStatus() {
//        return N_STK;
//    }
//
//    public List<DmsStatus> getStockStatuses() {
//        return List.of(STOCK, ZEROG);
//    }
//
//    public List<DmsStatus> getNonStockStatuses() {
//        return List.of(N_STK);
//    }
//
//    public List<DmsStatus> getActiveStatuses() {
//        return List.of(STOCK);
//    }
//
//    public List<DmsStatus> getInactiveStatuses() {
//        return List.of(N_STK, ZEROG);
//    }

    public String toString(){
		return statusName;
	}

}
