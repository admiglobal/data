package com.admi.data.entities;

import com.admi.data.entities.keys.TipOrderDetailEntityPK;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "TIP_ORDER_DETAIL", schema = "ADMI")
@IdClass(TipOrderDetailEntityPK.class)
public class TipOrderDetailEntity {
	private Long dealerId;
	private LocalDate dataDate;
	private String partNo;
	private String description;
	private Integer cents;
	private String src;
	private Integer ytdSales;
	private Integer ytdHitsByMonth;
	private Integer monthsNoSale;
	private Integer qoh;
	private String status;
	private LocalDate lastSale;
	private LocalDate lastReceipt;
	private String bin;
	private Boolean mfgControlled;
	private String manufacturer;
	private Integer qoo;
	private LocalDate entryDate;
	private Integer orderQty;

	public TipOrderDetailEntity() {}

	public TipOrderDetailEntity(Long dealerId, LocalDate dataDate, String partNo, String description, Integer cents, String src, Integer ytdSales, Integer ytdHitsByMonth, Integer monthsNoSale, Integer qoh, String status, LocalDate lastSale, LocalDate lastReceipt, String bin, Boolean mfgControlled, String manufacturer, Integer qoo, LocalDate entryDate, Integer orderQty) {
		this.dealerId = dealerId;
		this.dataDate = dataDate;
		this.partNo = partNo;
		this.description = description;
		this.cents = cents;
		this.src = src;
		this.ytdSales = ytdSales;
		this.ytdHitsByMonth = ytdHitsByMonth;
		this.monthsNoSale = monthsNoSale;
		this.qoh = qoh;
		this.status = status;
		this.lastSale = lastSale;
		this.lastReceipt = lastReceipt;
		this.bin = bin;
		this.mfgControlled = mfgControlled;
		this.manufacturer = manufacturer;
		this.qoo = qoo;
		this.entryDate = entryDate;
		this.orderQty = orderQty;
	}

	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Id
	@Column(name = "DATA_DATE", nullable = false)
	public LocalDate getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDate dataDate) {
		this.dataDate = dataDate;
	}

	@Id
	@Column(name = "PART_NO", nullable = false, length = 30)
	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	@Basic
	@Column(name = "DESCRIPTION", nullable = true, length = 60)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "CENTS", nullable = true, precision = 0)
	public Integer getCents() {
		return cents;
	}

	public void setCents(Integer cents) {
		this.cents = cents;
	}

	@Basic
	@Column(name = "SRC", nullable = true, precision = 0, length = 15)
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Basic
	@Column(name = "YTD_SALES", nullable = true, precision = 0)
	public Integer getYtdSales() {
		return ytdSales;
	}

	public void setYtdSales(Integer ytdSales) {
		this.ytdSales = ytdSales;
	}

	@Basic
	@Column(name = "YTD_HITS_BY_MONTH", nullable = true, precision = 0)
	public Integer getYtdHitsByMonth() {
		return ytdHitsByMonth;
	}

	public void setYtdHitsByMonth(Integer ytdHitsByMonth) {
		this.ytdHitsByMonth = ytdHitsByMonth;
	}

	@Basic
	@Column(name = "MONTHS_NO_SALE", nullable = true, precision = 0)
	public Integer getMonthsNoSale() {
		return monthsNoSale;
	}

	public void setMonthsNoSale(Integer monthsNoSale) {
		this.monthsNoSale = monthsNoSale;
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
	@Column(name = "STATUS", nullable = true, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	@Column(name = "BIN", nullable = true, length = 200)
	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	@Basic
	@Column(name = "MFG_CONTROLLED", nullable = true, precision = 0)
	public Boolean getMfgControlled() {
		return mfgControlled;
	}

	public void setMfgControlled(Boolean mfgControlled) {
		this.mfgControlled = mfgControlled;
	}

	@Basic
	@Column(name = "MANUFACTURER", nullable = true, length = 20)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
	@Column(name = "ENTRY_DATE", nullable = true)
	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public Integer getOrderQty() { return orderQty; }

	public void setOrderQty(Integer orderQty) { this.orderQty = orderQty; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TipOrderDetailEntity)) return false;
		TipOrderDetailEntity that = (TipOrderDetailEntity) o;
		return dealerId.equals(that.dealerId) &&
				dataDate.equals(that.dataDate) &&
				partNo.equals(that.partNo) &&
				Objects.equals(description, that.description) &&
				Objects.equals(cents, that.cents) && Objects.equals(src, that.src) &&
				Objects.equals(ytdSales, that.ytdSales) &&
				Objects.equals(ytdHitsByMonth, that.ytdHitsByMonth) &&
				Objects.equals(monthsNoSale, that.monthsNoSale) &&
				Objects.equals(qoh, that.qoh) &&
				Objects.equals(status, that.status) &&
				Objects.equals(lastSale, that.lastSale) &&
				Objects.equals(lastReceipt, that.lastReceipt) &&
				Objects.equals(bin, that.bin) &&
				Objects.equals(mfgControlled, that.mfgControlled) &&
				Objects.equals(manufacturer, that.manufacturer) &&
				Objects.equals(qoo, that.qoo) &&
				Objects.equals(entryDate, that.entryDate) &&
				Objects.equals(orderQty, that.orderQty);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId,
				dataDate,
				partNo,
				description,
				cents,
				src,
				ytdSales,
				ytdHitsByMonth,
				monthsNoSale,
				qoh,
				status,
				lastSale,
				lastReceipt,
				bin,
				mfgControlled,
				manufacturer,
				qoo,
				entryDate,
				orderQty);
	}

	@Override
	public String toString() {
		return "TipOrderDetailEntity{" +
				"dealerId=" + dealerId +
				", dataDate=" + dataDate +
				", partNo='" + partNo + '\'' +
				", description='" + description + '\'' +
				", cents=" + cents +
				", src='" + src + '\'' +
				", ytdSales=" + ytdSales +
				", ytdHitsByMonth=" + ytdHitsByMonth +
				", monthsNoSale=" + monthsNoSale +
				", qoh=" + qoh +
				", status='" + status + '\'' +
				", lastSale=" + lastSale +
				", lastReceipt=" + lastReceipt +
				", bin='" + bin + '\'' +
				", mfgControlled=" + mfgControlled +
				", manufacturer='" + manufacturer + '\'' +
				", qoo=" + qoo +
				", entryDate=" + entryDate +
				", orderQty=" + orderQty +
				'}';
	}
}
