package com.admi.data.entities;

import com.admi.data.entities.keys.AipInventoryEntityPK;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "AIP_INVENTORY", schema = "ADMI")
@IdClass(AipInventoryEntityPK.class)
public class AipInventoryEntity {
	private Long dealerId;
	private String partNo;
	private Integer cents;
	private Integer qoh;
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
	private Integer qoo;
	private Integer twelveMonthSales;

	private String paCode;

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
	public Integer getCents() {
		return cents;
	}

	public void setCents(Integer cents) {
		this.cents = cents;
	}

	@Transient
	public void setCentsFromDouble(Double price) {
		this.cents = Math.toIntExact(Math.round(price * 100));
	}

	@Basic
	@Column(name = "QOH", nullable = true, precision = 0)
	public Integer getQoh() {
		return qoh;
	}

	public void setQoh(Integer qoh) {
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

	@Transient
	public void setMfgControlledFromString(String mfgControlString) {
		if (mfgControlString.equalsIgnoreCase("Y")) {
			this.mfgControlled = true;
		} else if (mfgControlString.equalsIgnoreCase("N")) {
			this.mfgControlled = false;
		} else {
			this.mfgControlled = null;
		}

	}

	@Basic
	@Column(name = "QOO", nullable = true, precision = 0)
	public Integer getQoo() {
		return qoo;
	}

	public void setQoo(Integer qoo) {
		this.qoo = qoo;
	}

	@Basic
	@Column(name = "TWELVE_MONTH_SALES", nullable = true, precision = 0)
	public Integer getTwelveMonthSales() {
		return twelveMonthSales;
	}

	public void setTwelveMonthSales(Integer twelveMonthSales) {
		this.twelveMonthSales = twelveMonthSales;
	}

	@Id
	@Column(name = "DATA_DATE", nullable = false)
	public LocalDate getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDate dataDate) {
		this.dataDate = dataDate;
	}

	@Transient
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public void setPaCodeAsDouble(Double paCode) {
		this.paCode = String.valueOf(Math.round(paCode));
	}

	@Transient
	public LocalDate getLastSaleOrReceipt() {
		if (lastSale != null) {
			return lastSale;
		} else if (lastReceipt != null) {
			return lastReceipt;
		} else {
			return LocalDate.of(2000,1,1);
		}
	}

	@Transient
	public ZigEntity toZigEntity(String paCode) {
		ZigEntity zig = new ZigEntity();

		zig.setPaCode(paCode);
		zig.setPartNo(this.partNo);
		zig.setQoh(Long.valueOf(this.qoh));
		zig.setDes(this.description);
		zig.setLsDate(this.lastSale);
		zig.setLrDate(this.lastReceipt);
		zig.setStatus(this.admiStatus);
		zig.setRFlag(isReturnable());
		zig.setCost(new BigDecimal(cents/100).setScale(2, RoundingMode.HALF_UP));
		zig.setBin(this.bin);
		zig.setSrc(this.source);
		zig.setDmsStatus(this.status);
		zig.setMfgControlled(null);
		zig.setDataDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0)));

		return zig;
	}

	@Transient
	public Boolean isReturnable() {
		return this.cents > 1500;
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
