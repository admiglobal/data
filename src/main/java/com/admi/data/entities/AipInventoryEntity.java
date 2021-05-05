package com.admi.data.entities;

import com.admi.data.entities.keys.AipInventoryEntityPK;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "AIP_INVENTORY", schema = "ADMI")
@IdClass(AipInventoryEntityPK.class)
public class AipInventoryEntity {
	private Long dealerId;
	private String partNo;
	private Long cents;
	private Long qoh;
	private String description;
	private String status;
	private String admiStatus;
	private LocalDate lastSale;
	private LocalDate lastReceipt;
	private String bin;
	private String source;
	private Boolean mfgControlled;
	private LocalDate dataDate;
	private String manufacturer;

	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Id
	@Column(name = "PARTNO", nullable = false, length = 30)
	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partno) {
		this.partNo = partno;
	}

	@Basic
	@Column(name = "CENTS", nullable = true, precision = 0)
	public Long getCents() {
		return cents;
	}

	public void setCents(Long cents) {
		this.cents = cents;
	}

	@Basic
	@Column(name = "QOH", nullable = true, precision = 0)
	public Long getQoh() {
		return qoh;
	}

	public void setQoh(Long qoh) {
		this.qoh = qoh;
	}

	@Basic
	@Column(name = "DESCRIPTION", nullable = true, length = 40)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "STATUS", nullable = true, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Basic
	@Column(name = "ADMI_STATUS", nullable = true, length = 1)
	public String getAdmiStatus() {
		return admiStatus;
	}

	public void setAdmiStatus(String admiStatus) {
		this.admiStatus = admiStatus;
	}

	@Basic
	@Column(name = "LAST_SALE", nullable = true)
	public LocalDate getLastSale() {
		return lastSale;
	}

	public void setLastSale(LocalDate lastSale) {
		this.lastSale = lastSale;
	}

	@Basic
	@Column(name = "LAST_RECEIPT", nullable = true)
	public LocalDate getLastReceipt() {
		return lastReceipt;
	}

	public void setLastReceipt(LocalDate lastReceipt) {
		this.lastReceipt = lastReceipt;
	}

	@Basic
	@Column(name = "BIN", nullable = true, length = 20)
	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	@Basic
	@Column(name = "SOURCE", nullable = true, length = 7)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Basic
	@Column(name = "MANUFACTURER", nullable = true, length = 10)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Basic
	@Column(name = "MFG_CONTROLLED", nullable = true, precision = 0)
	public Boolean getMfgControlled() {
		return mfgControlled;
	}

	public void setMfgControlled(Boolean mfgControlled) {
		this.mfgControlled = mfgControlled;
	}

	@Id
	@Column(name = "DATA_DATE", nullable = false)
	public LocalDate getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDate dataDate) {
		this.dataDate = dataDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AipInventoryEntity that = (AipInventoryEntity) o;
		return Objects.equals(dealerId, that.dealerId) &&
				Objects.equals(partNo, that.partNo) &&
				Objects.equals(cents, that.cents) &&
				Objects.equals(qoh, that.qoh) &&
				Objects.equals(description, that.description) &&
				Objects.equals(status, that.status) &&
				Objects.equals(admiStatus, that.admiStatus) &&
				Objects.equals(lastSale, that.lastSale) &&
				Objects.equals(lastReceipt, that.lastReceipt) &&
				Objects.equals(bin, that.bin) &&
				Objects.equals(source, that.source) &&
				Objects.equals(mfgControlled, that.mfgControlled) &&
				Objects.equals(dataDate, that.dataDate) &&
				Objects.equals(manufacturer, that.manufacturer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, partNo, cents, qoh, description, status, admiStatus, lastSale, lastReceipt, bin, source, mfgControlled, dataDate, manufacturer);
	}
}
