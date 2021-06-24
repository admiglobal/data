package com.admi.data.entities;

import com.admi.data.entities.keys.McOrdersContentEntityPK;

import javax.persistence.*;

@Entity
@Table(name = "MC_ORDERS_CONTENT", schema = "ADMI", catalog = "")
@IdClass(McOrdersContentEntityPK.class)
public class McOrdersContentEntity {
	private String paCode;
	private String orderNumber;
	private String partno;
	private Float price;
	private Long qty;
	private String ocPartno;
	private String supplierPartno;
	private Long done;

	@Id
	@Column(name = "PA_CODE", nullable = false, length = 5)
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Id
	@Column(name = "ORDER_NUMBER", nullable = false, length = 7)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Basic
	@Column(name = "PARTNO", nullable = true, length = 20)
	public String getPartno() {
		return partno;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	@Basic
	@Column(name = "PRICE", nullable = true, precision = 0)
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Basic
	@Column(name = "QTY", nullable = true, precision = 0)
	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	@Basic
	@Column(name = "OC_PARTNO", nullable = true, length = 20)
	public String getOcPartno() {
		return ocPartno;
	}

	public void setOcPartno(String ocPartno) {
		this.ocPartno = ocPartno;
	}

	@Id
	@Column(name = "SUPPLIER_PARTNO", nullable = false, length = 20)
	public String getSupplierPartno() {
		return supplierPartno;
	}

	public void setSupplierPartno(String supplierPartno) {
		this.supplierPartno = supplierPartno;
	}

	@Basic
	@Column(name = "DONE", nullable = true, precision = 0)
	public Long getDone() {
		return done;
	}

	public void setDone(Long done) {
		this.done = done;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		McOrdersContentEntity that = (McOrdersContentEntity) o;

		if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
		if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null) return false;
		if (partno != null ? !partno.equals(that.partno) : that.partno != null) return false;
		if (price != null ? !price.equals(that.price) : that.price != null) return false;
		if (qty != null ? !qty.equals(that.qty) : that.qty != null) return false;
		if (ocPartno != null ? !ocPartno.equals(that.ocPartno) : that.ocPartno != null) return false;
		if (supplierPartno != null ? !supplierPartno.equals(that.supplierPartno) : that.supplierPartno != null)
			return false;
		if (done != null ? !done.equals(that.done) : that.done != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = paCode != null ? paCode.hashCode() : 0;
		result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
		result = 31 * result + (partno != null ? partno.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (qty != null ? qty.hashCode() : 0);
		result = 31 * result + (ocPartno != null ? ocPartno.hashCode() : 0);
		result = 31 * result + (supplierPartno != null ? supplierPartno.hashCode() : 0);
		result = 31 * result + (done != null ? done.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "McOrdersContentEntity{" +
				"paCode='" + paCode + '\'' +
				", orderNumber='" + orderNumber + '\'' +
				", partno='" + partno + '\'' +
				", price=" + price +
				", qty=" + qty +
				", ocPartno='" + ocPartno + '\'' +
				", supplierPartno='" + supplierPartno + '\'' +
				", done=" + done +
				'}';
	}
}
