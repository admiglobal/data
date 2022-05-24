package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

public class TipOrderDetailEntityPK implements Serializable {
	private Long dealerId;
	private LocalDate dataDate;
	private String partNo;

	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	@Id
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "DATA_DATE", nullable = false)
	@Id
	public LocalDate getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDate dataDate) {
		this.dataDate = dataDate;
	}

	@Column(name = "PART_NO", nullable = false, length = 30)
	@Id
	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TipOrderDetailEntityPK that = (TipOrderDetailEntityPK) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
		if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
		result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
		return result;
	}
}
