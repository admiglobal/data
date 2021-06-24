package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class McOrdersContentEntityPK implements Serializable {
	private String paCode;
	private String orderNumber;
	private String supplierPartno;

	@Column(name = "PA_CODE", nullable = false, length = 5)
	@Id
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Column(name = "ORDER_NUMBER", nullable = false, length = 7)
	@Id
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "SUPPLIER_PARTNO", nullable = false, length = 20)
	@Id
	public String getSupplierPartno() {
		return supplierPartno;
	}

	public void setSupplierPartno(String supplierPartno) {
		this.supplierPartno = supplierPartno;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		McOrdersContentEntityPK that = (McOrdersContentEntityPK) o;

		if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
		if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null) return false;
		if (supplierPartno != null ? !supplierPartno.equals(that.supplierPartno) : that.supplierPartno != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = paCode != null ? paCode.hashCode() : 0;
		result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
		result = 31 * result + (supplierPartno != null ? supplierPartno.hashCode() : 0);
		return result;
	}
}
