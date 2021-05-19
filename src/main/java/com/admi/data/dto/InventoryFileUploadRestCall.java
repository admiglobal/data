package com.admi.data.dto;

public class InventoryFileUploadRestCall {

	String filePath;
	String paCode;
	long dealerId;
	int dmsId;

	public InventoryFileUploadRestCall() {}

	public InventoryFileUploadRestCall(String file, String paCode, long dealerId, int dmsId) {
		this.filePath = file;
		this.paCode = paCode;
		this.dealerId = dealerId;
		this.dmsId = dmsId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	public int getDmsId() {
		return dmsId;
	}

	public void setDmsId(int dmsId) {
		this.dmsId = dmsId;
	}

	@Override
	public String toString() {
		return "{"
				+ " \"filePath\":\"" + filePath + "\""
				+ ", \"paCode\":\"" + paCode + "\""
				+ ", \"dealerId\":\"" + dealerId + "\""
				+ ", \"dmsId\":\"" + dmsId + "\""
				+ "}";
	}
}
