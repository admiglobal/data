package com.admi.data.entities;

import com.admi.data.entities.keys.KpiEntityPK;

import javax.persistence.*;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "AIS_KPI", schema = "ADMI")
@IdClass(KpiEntityPK.class)
public class KpiEntity {
	private long dealerId;
	private long dataDate;
	private Long totalSValue;
	private Long totalNsValue;
	private Long sSkuCount;
	private Long nsSkuCount;
	private Long sOver9M;
	private Long nsOver9M;
	private Long nsLessThan60;
	private Long ns61To90;
	private Long ns61To9M;
	private Long dmsPerf;
	private Long pieceCount;
	private Long outOfSpec;
	private Long ns30To60;
	private Long nsNewIdle;
	private Long nsPreIdle;
	private LocalDateTime dataUpdated;

	public KpiEntity() {
		this.dealerId = 0;
		this.dataDate = 0;
		this.totalSValue = 0L;
		this.totalNsValue = 0L;
		this.sSkuCount = 0L;
		this.nsSkuCount = 0L;
		this.sOver9M = 0L;
		this.nsOver9M = 0L;
		this.nsLessThan60 = 0L;
		this.ns61To90 = 0L;
		this.ns61To9M = 0L;
		this.dmsPerf = 0L;
		this.pieceCount = 0L;
		this.outOfSpec = 0L;
		this.ns30To60 = 0L;
		this.nsNewIdle = 0L;
		this.nsPreIdle = 0L;
		this.dataUpdated = LocalDateTime.now();
	}

	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	@Id
	@Column(name = "DATADATE", nullable = false, precision = 0)
	public long getDataDate() {
		return dataDate;
	}

	public void setDataDate(long dataDate) {
		this.dataDate = dataDate;
	}

	@Basic
	@Column(name = "TOTAL_S_VALUE", nullable = true, precision = 0)
	public Long getTotalSValue() {
		return totalSValue;
	}

	public void setTotalSValue(Long totalSValue) {
		this.totalSValue = totalSValue;
	}

	@Basic
	@Column(name = "TOTAL_NS_VALUE", nullable = true, precision = 0)
	public Long getTotalNsValue() {
		return totalNsValue;
	}

	public void setTotalNsValue(Long totalNsValue) {
		this.totalNsValue = totalNsValue;
	}

	@Basic
	@Column(name = "S_SKU_COUNT", nullable = true, precision = 0)
	public Long getSSkuCount() {
		return sSkuCount;
	}

	public void setSSkuCount(Long sSkuCount) {
		this.sSkuCount = sSkuCount;
	}

	@Basic
	@Column(name = "NS_SKU_COUNT", nullable = true, precision = 0)
	public Long getNsSkuCount() {
		return nsSkuCount;
	}

	public void setNsSkuCount(Long nsSkuCount) {
		this.nsSkuCount = nsSkuCount;
	}

	@Basic
	@Column(name = "S_OVER_9M", nullable = true, precision = 0)
	public Long getSOver9M() {
		return sOver9M;
	}

	public void setSOver9M(Long sOver9M) {
		this.sOver9M = sOver9M;
	}

	@Basic
	@Column(name = "NS_OVER_9M", nullable = true, precision = 0)
	public Long getNsOver9M() {
		return nsOver9M;
	}

	public void setNsOver9M(Long nsOver9M) {
		this.nsOver9M = nsOver9M;
	}

	@Basic
	@Column(name = "NS_LESS_THAN_60", nullable = true, precision = 0)
	public Long getNsLessThan60() {
		return nsLessThan60;
	}

	public void setNsLessThan60(Long nsLessThan60) {
		this.nsLessThan60 = nsLessThan60;
	}

	@Basic
	@Column(name = "NS_61_TO_90", nullable = true, precision = 0)
	public Long getNs61To90() {
		return ns61To90;
	}

	public void setNs61To90(Long ns61To90) {
		this.ns61To90 = ns61To90;
	}

	@Basic
	@Column(name = "NS_61_TO_9M", nullable = true, precision = 0)
	public Long getNs61To9M() {
		return ns61To9M;
	}

	public void setNs61To9M(Long ns61To9M) {
		this.ns61To9M = ns61To9M;
	}

	@Basic
	@Column(name = "DMS_PERF", nullable = true, precision = 2)
	public Long getDmsPerf() {
		return dmsPerf;
	}

	public void setDmsPerf(Long dmsPerf) {
		this.dmsPerf = dmsPerf;
	}

	@Basic
	@Column(name = "PIECE_COUNT", nullable = true, precision = 0)
	public Long getPieceCount() {
		return pieceCount;
	}

	public void setPieceCount(Long pieceCount) {
		this.pieceCount = pieceCount;
	}

	@Basic
	@Column(name = "OOS", nullable = true, precision = 0)
	public Long getOutOfSpec() {
		return outOfSpec;
	}

	public void setOutOfSpec(Long outOfSpec) {
		this.outOfSpec = outOfSpec;
	}

	@Basic
	@Column(name = "NS_30_TO_60", nullable = true, precision = 0)
	public Long getNs30To60() {
		return ns30To60;
	}

	public void setNs30To60(Long ns30To60) {
		this.ns30To60 = ns30To60;
	}

	@Basic
	@Column(name = "NS_NEW_IDLE", nullable = true, precision = 0)
	public Long getNsNewIdle() {
		return nsNewIdle;
	}

	public void setNsNewIdle(Long nsNewIdle) {
		this.nsNewIdle = nsNewIdle;
	}

	@Basic
	@Column(name = "NS_PRE_IDLE", nullable = true, precision = 0)
	public Long getNsPreIdle() {
		return nsPreIdle;
	}

	public void setNsPreIdle(Long nsPreIdle) {
		this.nsPreIdle = nsPreIdle;
	}

	@Basic
	@Column(name = "DATA_UPDATED", nullable = true)
	public LocalDateTime getDataUpdated() {
		return dataUpdated;
	}

	public void setDataUpdated(LocalDateTime dataUpdated) {
		this.dataUpdated = dataUpdated;
	}

	@Transient
	public String getFormattedDate() {
		String dateString = Long.toString(dataDate);
		String year = dateString.substring(2, 4);
		int month = Integer.parseInt(dateString.substring(4));

		String monthString = new DateFormatSymbols().getShortMonths()[month - 1];

		return monthString.concat(' ' + year);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KpiEntity kpiEntity = (KpiEntity) o;
		return dealerId == kpiEntity.dealerId &&
				dataDate == kpiEntity.dataDate &&
				Objects.equals(totalSValue, kpiEntity.totalSValue) &&
				Objects.equals(totalNsValue, kpiEntity.totalNsValue) &&
				Objects.equals(sSkuCount, kpiEntity.sSkuCount) &&
				Objects.equals(nsSkuCount, kpiEntity.nsSkuCount) &&
				Objects.equals(sOver9M, kpiEntity.sOver9M) &&
				Objects.equals(nsOver9M, kpiEntity.nsOver9M) &&
				Objects.equals(nsLessThan60, kpiEntity.nsLessThan60) &&
				Objects.equals(ns61To90, kpiEntity.ns61To90) &&
				Objects.equals(ns61To9M, kpiEntity.ns61To9M) &&
				Objects.equals(dmsPerf, kpiEntity.dmsPerf) &&
				Objects.equals(pieceCount, kpiEntity.pieceCount) &&
				Objects.equals(outOfSpec, kpiEntity.outOfSpec) &&
				Objects.equals(ns30To60, kpiEntity.ns30To60) &&
				Objects.equals(nsNewIdle, kpiEntity.nsNewIdle) &&
				Objects.equals(nsPreIdle, kpiEntity.nsPreIdle) &&
				Objects.equals(dataUpdated, kpiEntity.dataUpdated);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, dataDate, totalSValue, totalNsValue, sSkuCount, nsSkuCount, sOver9M, nsOver9M, nsLessThan60, ns61To90, ns61To9M, dmsPerf, pieceCount, outOfSpec, ns30To60, nsNewIdle, nsPreIdle, dataUpdated);
	}
}
