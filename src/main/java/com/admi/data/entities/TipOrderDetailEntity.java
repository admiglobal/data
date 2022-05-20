package com.admi.data.entities;

import com.admi.data.entities.keys.TipOrderDetailEntityPK;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TIP_ORDER_DETAIL", schema = "ADMI")
@IdClass(TipOrderDetailEntityPK.class)
public class TipOrderDetailEntity {
	private Long dealerId;
	private LocalDateTime dataDate;
	private String partNo;
	private String description;
	private Integer cents;
	private String src;
	private Integer ytdSales;
	private Byte ytdHitsByMonth;
	private Byte monthsNoSale;

	public TipOrderDetailEntity() {}

	public TipOrderDetailEntity(Long dealerId, LocalDateTime dataDate, String partNo, String description, Integer cents, String src, Integer ytdSales, Byte ytdHitsByMonth, Byte monthsNoSale) {
		this.dealerId = dealerId;
		this.dataDate = dataDate;
		this.partNo = partNo;
		this.description = description;
		this.cents = cents;
		this.src = src;
		this.ytdSales = ytdSales;
		this.ytdHitsByMonth = ytdHitsByMonth;
		this.monthsNoSale = monthsNoSale;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "DATA_DATE", nullable = false)
	public LocalDateTime getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDateTime dataDate) {
		this.dataDate = dataDate;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Column(name = "SRC", nullable = true, precision = 0)
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
	public Byte getYtdHitsByMonth() {
		return ytdHitsByMonth;
	}

	public void setYtdHitsByMonth(Byte ytdHitsByMonth) {
		this.ytdHitsByMonth = ytdHitsByMonth;
	}

	@Basic
	@Column(name = "MONTHS_NO_SALE", nullable = true, precision = 0)
	public Byte getMonthsNoSale() {
		return monthsNoSale;
	}

	public void setMonthsNoSale(Byte monthsNoSale) {
		this.monthsNoSale = monthsNoSale;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TipOrderDetailEntity that = (TipOrderDetailEntity) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
		if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (cents != null ? !cents.equals(that.cents) : that.cents != null) return false;
		if (src != null ? !src.equals(that.src) : that.src != null) return false;
		if (ytdSales != null ? !ytdSales.equals(that.ytdSales) : that.ytdSales != null) return false;
		if (ytdHitsByMonth != null ? !ytdHitsByMonth.equals(that.ytdHitsByMonth) : that.ytdHitsByMonth != null)
			return false;
		if (monthsNoSale != null ? !monthsNoSale.equals(that.monthsNoSale) : that.monthsNoSale != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
		result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (cents != null ? cents.hashCode() : 0);
		result = 31 * result + (src != null ? src.hashCode() : 0);
		result = 31 * result + (ytdSales != null ? ytdSales.hashCode() : 0);
		result = 31 * result + (ytdHitsByMonth != null ? ytdHitsByMonth.hashCode() : 0);
		result = 31 * result + (monthsNoSale != null ? monthsNoSale.hashCode() : 0);
		return result;
	}
}
