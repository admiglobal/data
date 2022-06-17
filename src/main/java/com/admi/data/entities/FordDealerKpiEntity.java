package com.admi.data.entities;

import com.admi.data.entities.keys.FordDealerKpiEntityPK;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FORD_DEALER_KPI", schema = "ADMI")
@IdClass(FordDealerKpiEntityPK.class)
public class FordDealerKpiEntity {

	private Long dealerId;
	private LocalDate dateUpdated;
	private Integer totalSValue;
	private Integer totalNsValue;
	private Integer totalRimValue;
	private Integer sSkuCount;
	private Integer nsSkuCount;
	private Integer rimSkuCount;
	private Integer sOver9M;
	private Integer nsOver60;
	private Integer nsUnder60;
	private Integer nsOver270;

	public FordDealerKpiEntity() {}

	public FordDealerKpiEntity(Long dealerId, LocalDate dateUpdated, Integer totalSValue, Integer totalNsValue, Integer totalRimValue, Integer sSkuCount, Integer nsSkuCount, Integer rimSkuCount, Integer sOver9M, Integer nsOver60, Integer nsUnder60, Integer nsOver270) {
		this.dealerId = dealerId;
		this.dateUpdated = dateUpdated;
		this.totalSValue = totalSValue;
		this.totalNsValue = totalNsValue;
		this.totalRimValue = totalRimValue;
		this.sSkuCount = sSkuCount;
		this.nsSkuCount = nsSkuCount;
		this.rimSkuCount = rimSkuCount;
		this.sOver9M = sOver9M;
		this.nsOver60 = nsOver60;
		this.nsUnder60 = nsUnder60;
		this.nsOver270 = nsOver270;
	}

	@Id
	@Column(name = "DEALER_ID", nullable = false)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Id
	@Column(name = "DATE_UPDATED", nullable = false)
	public LocalDate getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDate dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Basic
	@Column(name = "TOTAL_S_VALUE", nullable = true)
	public Integer getTotalSValue() {
		return totalSValue;
	}

	public void setTotalSValue(Integer totalSValue) {
		this.totalSValue = totalSValue;
	}

	@Basic
	@Column(name = "TOTAL_NS_VALUE", nullable = true)
	public Integer getTotalNsValue() {
		return totalNsValue;
	}

	public void setTotalNsValue(Integer totalNsValue) {
		this.totalNsValue = totalNsValue;
	}

	@Basic
	@Column(name = "TOTAL_RIM_VALUE", nullable = true)
	public Integer getTotalRimValue() {
		return totalRimValue;
	}

	public void setTotalRimValue(Integer totalRimValue) {
		this.totalRimValue = totalRimValue;
	}

	@Basic
	@Column(name = "S_SKU_COUNT", nullable = true)
	public Integer getsSkuCount() {
		return sSkuCount;
	}

	public void setsSkuCount(Integer sSkuCount) {
		this.sSkuCount = sSkuCount;
	}

	@Basic
	@Column(name = "NS_SKU_COUNT", nullable = true)
	public Integer getNsSkuCount() {
		return nsSkuCount;
	}

	public void setNsSkuCount(Integer nsSkuCount) {
		this.nsSkuCount = nsSkuCount;
	}

	@Basic
	@Column(name = "RIM_SKU_COUNT", nullable = true)
	public Integer getRimSkuCount() {
		return rimSkuCount;
	}

	public void setRimSkuCount(Integer rimSkuCount) {
		this.rimSkuCount = rimSkuCount;
	}

	@Basic
	@Column(name = "S_OVER_9M", nullable = true)
	public Integer getsOver9M() {
		return sOver9M;
	}

	public void setsOver9M(Integer sOver9M) {
		this.sOver9M = sOver9M;
	}

	@Basic
	@Column(name = "NS_OVER_60", nullable = true)
	public Integer getNsOver60() {
		return nsOver60;
	}

	public void setNsOver60(Integer nsOver60) {
		this.nsOver60 = nsOver60;
	}

	@Basic
	@Column(name = "NS_UNDER_60", nullable = true)
	public Integer getNsUnder60() {
		return nsUnder60;
	}

	public void setNsUnder60(Integer nsUnder60) {
		this.nsUnder60 = nsUnder60;
	}

	@Basic
	@Column(name = "NS_OVER_270", nullable = true)
	public Integer getNsOver270() {
		return nsOver270;
	}

	public void setNsOver270(Integer nsOver270) {
		this.nsOver270 = nsOver270;
	}

	@Override
	public String toString() {
		return "FordDealerKpiEntity{" +
				"dealerId=" + dealerId +
				", dateUpdated=" + dateUpdated +
				", totalSValue=" + totalSValue +
				", totalNsValue=" + totalNsValue +
				", totalRimValue=" + totalRimValue +
				", sSkuCount=" + sSkuCount +
				", nsSkuCount=" + nsSkuCount +
				", rimSkuCount=" + rimSkuCount +
				", sOver9M=" + sOver9M +
				", nsOver60=" + nsOver60 +
				", nsUnder60=" + nsUnder60 +
				", nsOver270=" + nsOver270 +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FordDealerKpiEntity that = (FordDealerKpiEntity) o;
		return Objects.equals(dealerId, that.dealerId) && Objects.equals(dateUpdated, that.dateUpdated) && Objects.equals(totalSValue, that.totalSValue) && Objects.equals(totalNsValue, that.totalNsValue) && Objects.equals(totalRimValue, that.totalRimValue) && Objects.equals(sSkuCount, that.sSkuCount) && Objects.equals(nsSkuCount, that.nsSkuCount) && Objects.equals(rimSkuCount, that.rimSkuCount) && Objects.equals(sOver9M, that.sOver9M) && Objects.equals(nsOver60, that.nsOver60) && Objects.equals(nsUnder60, that.nsUnder60) && Objects.equals(nsOver270, that.nsOver270);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, dateUpdated, totalSValue, totalNsValue, totalRimValue, sSkuCount, nsSkuCount, rimSkuCount, sOver9M, nsOver60, nsUnder60, nsOver270);
	}
}
