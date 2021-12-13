package com.admi.data.entities;

import com.admi.data.enums.MixSource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "MIX_DEALERS", schema = "ADMI")
public class MixDealersEntity {
	private String dealerCode;
	private Long dealerId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String partSource;
	private MixSource mixSource;

	@Id
	@Column(name = "DEALER_CODE", nullable = true, length = 15)
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	@Basic
	@Column(name = "DEALER_ID", nullable = true, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Basic
	@Column(name = "START_DATE", nullable = true)
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	@Basic
	@Column(name = "END_DATE", nullable = true)
	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Basic
	@Column(name = "PART_SOURCE", nullable = true)
	public String getPartSource() {
		return partSource;
	}

	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}

	@Basic
	@Column(name = "MIX_SOURCE", nullable = true, length = 11)
	@Enumerated(EnumType.STRING)
	public MixSource getMixSource() {
		return mixSource;
	}

	public void setMixSource(MixSource mixSource) {
		this.mixSource = mixSource;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MixDealersEntity that = (MixDealersEntity) o;
		return Objects.equals(dealerCode, that.dealerCode) &&
				Objects.equals(dealerId, that.dealerId) &&
				Objects.equals(startDate, that.startDate) &&
				Objects.equals(endDate, that.endDate) &&
				Objects.equals(partSource, that.partSource);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerCode, dealerId, startDate, endDate, partSource);
	}
}
