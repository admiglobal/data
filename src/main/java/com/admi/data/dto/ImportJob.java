package com.admi.data.dto;

public class ImportJob {

	long dealerId;
	int dmsId;
	String filePath;
	String paCode;

	public ImportJob() {}

	public ImportJob(Long dealerId, int dmsId, String filePath) {
		this.dealerId = dealerId;
		this.dmsId = dmsId;
		this.filePath = filePath;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public int getDmsId() {
		return dmsId;
	}

	public void setDmsId(int dmsId) {
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

	@Override
	public String toString() {
		return "ImportJob{" +
				"dealerId=" + dealerId +
				", dmsId=" + dmsId +
				", filePath='" + filePath + '\'' +
				", paCode='" + paCode + '\'' +
				'}';
	}
}
