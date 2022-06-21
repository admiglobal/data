package com.admi.data.enums;

import com.admi.data.enums.statuses.*;

import java.util.Arrays;

public enum DmsProvider {

	CDK(new Integer[]{8,37,54,61}, "CDK", CdkStatus.STOCK),
	RR_ERA(new Integer[]{1,50}, "R&R Era/Ignite", RREraStatus.STOCK),
	AUTOSOFT(new Integer[]{9}, "AutoSoft", AutosoftStatus.Y),
	AUTOMATE(new Integer[]{22}, "AutoMate", AutomateStatus.STOCKED),
	RR_POWER(new Integer[]{48}, "R&R Power", RRPowerStatus.STOCK),
	PBS(new Integer[]{30}, "PBS", PbsStatus.STOCK),
	DEALERTRACK(new Integer[]{13}, "DealerTrack", DealerTrackStatus.A),
	LIGHTYEAR(new Integer[]{23}, "LightYear", LightyearStatus.S),
	QUORUM(new Integer[]{}, "Quorum", GenericStatus.STOCK),
	DOMINION(new Integer[]{}, "Dominion", GenericStatus.STOCK),
	GENERIC(new Integer[]{0}, "Generic", GenericStatus.STOCK);

	private final Integer[] idArray;
	private final String dmsName;
	private final DmsStatus statusType;

	DmsProvider(Integer[] idArray, String dmsName, DmsStatus statusType) {
		this.idArray = idArray;
		this.dmsName = dmsName;
		this.statusType = statusType;
	}

	public static DmsProvider findDms(Integer dmsId) {
		for (DmsProvider dms : values()) {
			if (Arrays.asList(dms.idArray).contains(dmsId))
				return dms;
		}
		return GENERIC;
	}

	public static DmsProvider getByMixSource(MixSource mixDms) {
		switch (mixDms) {
			case AUTOMATE:
				return AUTOMATE;
			case AUTOSOFT:
				return AUTOSOFT;
			case DOMINION:
				return DOMINION;
			case PBS:
				return PBS;
			case QUORUM:
				return QUORUM;
			default:
				return GENERIC;
		}
	}

	public Integer[] getIdArray() {
		return idArray;
	}

	public String getDmsName() {
		return dmsName;
	}

	public DmsStatus getStatusType() {
		return statusType;
	}
}
