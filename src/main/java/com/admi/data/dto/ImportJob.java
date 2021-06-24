package com.admi.data.dto;

public class ImportJob {

	long dealerId;
	Integer dmsId;
	String filePath;
	String paCode;
	String email;

	public ImportJob() {}

	public ImportJob(Long dealerId, Integer dmsId, String filePath) {
		this.dealerId = dealerId;
		this.dmsId = dmsId;
		this.filePath = filePath;
	}
	public ImportJob(Long dealerId, Integer dmsId, String filePath, String email) {
		this.dealerId = dealerId;
		this.dmsId = dmsId;
		this.filePath = filePath;
		this.email = email;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public Integer getDmsId() {
		return dmsId;
	}

	public void setDmsId(Integer dmsId) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "{\"ImportJob\":{"
				+ "                		\"dealerId\":\"" + dealerId + "\""
				+ ",                 		\"dmsId\":\"" + dmsId + "\""
				+ ",                 		\"filePath\":\"" + filePath + "\""
				+ ",                 		\"paCode\":\"" + paCode + "\""
				+ ",                 		\"email\":\"" + email + "\""
				+ "}}";
	}
}
