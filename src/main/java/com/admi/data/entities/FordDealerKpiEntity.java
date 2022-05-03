package com.admi.data.entities;

import com.admi.data.entities.keys.FordDealerKpiEntityPK;

import javax.persistence.*;
import java.time.LocalDate;

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

	public FordDealerKpiEntity() {}

	public FordDealerKpiEntity(Long dealerId, LocalDate dateUpdated, Integer totalSValue, Integer totalNsValue, Integer totalRimValue, Integer sSkuCount, Integer nsSkuCount, Integer rimSkuCount, Integer sOver9M, Integer nsOver60) {
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
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "DEALER_ID", nullable = false)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FordDealerKpiEntity that = (FordDealerKpiEntity) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (dateUpdated != null ? !dateUpdated.equals(that.dateUpdated) : that.dateUpdated != null) return false;
		if (totalSValue != null ? !totalSValue.equals(that.totalSValue) : that.totalSValue != null) return false;
		if (totalNsValue != null ? !totalNsValue.equals(that.totalNsValue) : that.totalNsValue != null) return false;
		if (totalRimValue != null ? !totalRimValue.equals(that.totalRimValue) : that.totalRimValue != null)
			return false;
		if (sSkuCount != null ? !sSkuCount.equals(that.sSkuCount) : that.sSkuCount != null) return false;
		if (nsSkuCount != null ? !nsSkuCount.equals(that.nsSkuCount) : that.nsSkuCount != null) return false;
		if (rimSkuCount != null ? !rimSkuCount.equals(that.rimSkuCount) : that.rimSkuCount != null) return false;
		if (sOver9M != null ? !sOver9M.equals(that.sOver9M) : that.sOver9M != null) return false;
		if (nsOver60 != null ? !nsOver60.equals(that.nsOver60) : that.nsOver60 != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (dateUpdated != null ? dateUpdated.hashCode() : 0);
		result = 31 * result + (totalSValue != null ? totalSValue.hashCode() : 0);
		result = 31 * result + (totalNsValue != null ? totalNsValue.hashCode() : 0);
		result = 31 * result + (totalRimValue != null ? totalRimValue.hashCode() : 0);
		result = 31 * result + (sSkuCount != null ? sSkuCount.hashCode() : 0);
		result = 31 * result + (nsSkuCount != null ? nsSkuCount.hashCode() : 0);
		result = 31 * result + (rimSkuCount != null ? rimSkuCount.hashCode() : 0);
		result = 31 * result + (sOver9M != null ? sOver9M.hashCode() : 0);
		result = 31 * result + (nsOver60 != null ? nsOver60.hashCode() : 0);
		return result;
	}
}
