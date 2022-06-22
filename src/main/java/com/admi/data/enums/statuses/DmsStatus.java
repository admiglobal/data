package com.admi.data.enums.statuses;

import com.admi.data.enums.DmsProvider;

import java.util.List;

public interface DmsStatus {

	String getStatusName();

	DmsStatus getStockStatus();

	DmsStatus getNonStockStatus();

	List<DmsStatus> getStockStatuses();

	List<DmsStatus> getNonStockStatuses();

	String toString();

	List<DmsStatus> getActiveStatuses();

	List<DmsStatus> getInactiveStatuses();

	static DmsStatus findStatus(String status, DmsProvider dmsProvider) {

		try {
			switch (dmsProvider) {
				case AUTOMATE:
					return AutomateStatus.valueOf(status);
				case AUTOSOFT:
					return AutosoftStatus.valueOf(status);
				case CDK:
					return CdkStatus.valueOf(status);
				case DEALERTRACK:
					return DealerTrackStatus.valueOf(status);
				case LIGHTYEAR:
					return LightyearStatus.valueOf(status);
				case PBS:
					return PbsStatus.findStatus(status);
				case RR_ERA:
					return RREraStatus.valueOf(status);
				case RR_POWER:
					return RRPowerStatus.of(status);
				case DOMINION:
				case GENERIC:
				case QUORUM:
				default:
					return GenericStatus.valueOf(status);
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			return dmsProvider.getStatusType().getNonStockStatus();
		}
	}

}
