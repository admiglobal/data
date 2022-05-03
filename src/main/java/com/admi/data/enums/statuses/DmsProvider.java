package com.admi.data.enums.statuses;

public enum DmsProvider {


	CDK(new Integer[]{3,37,54,61}, "CDK"),
	RR(new Integer[]{}, ""),
	AUTOSOFT(new Integer[]{}, ""),
	AUTOMATE(new Integer[]{}, ""),
	RR(new Integer[]{}, ""),
	RR(new Integer[]{}, ""),
	RR(new Integer[]{}, ""),
	GENERIC(new Integer[]{}, "");

	private final Integer[] idArray;
	private final String dmsName;

	DmsProvider(Integer[] idArray, String dmsName) {
		this.idArray = idArray;
		this.dmsName = dmsName;
	}

switch (dms) {

		case 8:
		case 37:
		case 54:
		case 61:
			try {
				status = CdkStatus.valueOf(statusString);
			} catch (NullPointerException | IllegalArgumentException e) {
				status = CdkStatus.NS;
			}
			break;
		case 22:
			try {
				status = AutomateStatus.valueOf(statusString);
			} catch (NullPointerException | IllegalArgumentException e) {
				status = AutomateStatus.NOT_STOCKED;
			}
			break;
		case 9:
			try {
				status = AutosoftStatus.valueOf(statusString);
			} catch (NullPointerException | IllegalArgumentException e) {
				status = AutosoftStatus.N;
			}
			break;
		case 13:
			try {
				status = DealerTrackStatus.valueOf(statusString);
			} catch (NullPointerException | IllegalArgumentException e) {
				status = DealerTrackStatus.N;
			}
			break;
		case 23:
			try {
				status = LightyearStatus.valueOf(statusString);
			} catch (NullPointerException | IllegalArgumentException e) {
				status = LightyearStatus.N;
			}
			break;
		case 30:
			try {
				status = PbsStatus.findStatus(statusString.toUpperCase());
			} catch (NullPointerException | IllegalArgumentException e) {
				status = PbsStatus.TEST;
			}
			break;
		case 1:
		case 48:
		case 50:
			try {
				status = RREraStatus.valueOf(statusString);
			} catch (NullPointerException | IllegalArgumentException e) {
				status = RREraStatus.NS;
			}
			break;
		default:
			status = GenericStatus.NONE;
			break;
	}

}
