package com.admi.data.entities;

public class DowOrderRow {

	private String paCode;
	private String orderType = "S";
	private String partNumber;
	private String termsCode = "G";
	private Long quantity;
	private String poNumber;

	public DowOrderRow(String paCode, String partNumber, Long quantity, String poNumber) {
		this.paCode = paCode;
		this.partNumber = partNumber;
		this.quantity = quantity;
		this.poNumber = poNumber;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public String getOrderType() {
		return orderType;
	}

	private void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getTermsCode() {
		return termsCode;
	}

	private void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	@Override
	public String toString() {
		return "DowOrderRow{" +
				"paCode='" + paCode + '\'' +
				", orderType='" + orderType + '\'' +
				", partNumber='" + partNumber + '\'' +
				", termsCode='" + termsCode + '\'' +
				", quantity=" + quantity +
				", poNumber='" + poNumber + '\'' +
				'}';
	}
}
