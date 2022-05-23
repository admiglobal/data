package com.admi.data.entities;

import com.admi.data.entities.keys.AipInventoryEntityPK;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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
	private LocalDate entryDate;
	private Integer ytdMonthsWithSales;

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
	@Column(name = "DESCRIPTION", nullable = true, length = 60)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null) {
			int size = 60;
			int inLength = description.length();
			if (inLength > size)
				description = description.substring(0, size);
		}
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
		String[] yesList = new String[]{"Y", "YES"};
		String[] noList = new String[]{"N", "NO"};

		if (Arrays.asList(yesList).contains(mfgControlString.toUpperCase())) {
			this.mfgControlled = true;
		} else if (Arrays.asList(noList).contains(mfgControlString.toUpperCase())) {
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

	@Basic
	@Column(name = "ENTRY_DATE", nullable = true)
	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	@Basic
	@Column(name = "YTD_MONTHS_WITH_SALES", nullable = true)
	public Integer getYtdMonthsWithSales(){
		return ytdMonthsWithSales;
	}

	public void setYtdMonthsWithSales(Integer ytdMonthsWithSales){
		this.ytdMonthsWithSales = ytdMonthsWithSales;
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
//		if (lastSale != null) {
//			return lastSale;
//		} else if (lastReceipt != null) {
//			return lastReceipt;
//		} else if (entryDate != null) {
//			return entryDate;
//		} else {
//			return LocalDate.of(2000,1,1);
//		}

		if (lastReceipt != null) {
			return lastReceipt;
		} else if (lastSale != null) {
			return lastSale;
		} else if (entryDate != null) {
			return entryDate;
		} else {
			return LocalDate.of(2000,1,1);
		}
	}

	@Transient
	public ZigEntity toZigEntity(String paCode) {
		ZigEntity zig = new ZigEntity();

		zig.setPaCode(paCode);
		zig.setPartNo(this.partNo);
		zig.setQoh(getQohIfNull());
		zig.setDes(this.description);
		zig.setLsDate(this.lastSale);
		zig.setLrDate(this.lastReceipt);
		zig.setStatus(this.admiStatus);
		zig.setRFlag(isReturnable());
		zig.setCost(getCentsIfNull());
		zig.setBin(this.bin);
		zig.setSrc(this.source);
		zig.setDmsStatus(this.status);
		zig.setMfgControlled(this.mfgControlled);
		zig.setDataDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0)));

		return zig;
	}

	@Transient
	public Boolean isReturnable() {
		if (this.cents != null)
			return this.cents > 1500;
		else
			return false;
	}

	@Transient
	private Long getQohIfNull() {
		if (this.qoh != null)
			return Long.valueOf(this.qoh);
		else
			return 0L;
	}

	@Transient
	private BigDecimal getCentsIfNull() {
		if (this.cents != null) {
			return BigDecimal.valueOf((cents * 1.00) / 100).setScale(2, RoundingMode.HALF_UP);
		} else {
			return BigDecimal.valueOf(0);
		}
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

	@Override
	public String toString() {
		return "AipInventoryEntity{" +
				"dealerId=" + dealerId +
				", partNo='" + partNo + '\'' +
				", cents=" + cents +
				", qoh=" + qoh +
				", description='" + description + '\'' +
				", status='" + status + '\'' +
				", admiStatus='" + admiStatus + '\'' +
				", lastSale=" + lastSale +
				", lastReceipt=" + lastReceipt +
				", bin='" + bin + '\'' +
				", source='" + source + '\'' +
				", mfgControlled=" + mfgControlled +
				", dataDate=" + dataDate +
				", manufacturer='" + manufacturer + '\'' +
				", qoo=" + qoo +
				", twelveMonthSales=" + twelveMonthSales +
				", entryDate=" + entryDate +
				", ytdMonthsWithSales=" + ytdMonthsWithSales +
				", paCode='" + paCode + '\'' +
				'}';
	}
}
