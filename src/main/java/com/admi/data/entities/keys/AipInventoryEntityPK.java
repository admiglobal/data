package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

public class AipInventoryEntityPK implements Serializable {
	private Long dealerId;
	private String partno;
	private LocalDate dataDate;

	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	@Id
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "PARTNO", nullable = false, length = 30)
	@Id
	public String getPartNo() {
		return partno;
	}

	public void setPartNo(String partno) {
		this.partno = partno;
	}

	@Column(name = "DATA_DATE", nullable = false)
	@Id
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

		AipInventoryEntityPK that = (AipInventoryEntityPK) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (partno != null ? !partno.equals(that.partno) : that.partno != null) return false;
		if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (partno != null ? partno.hashCode() : 0);
		result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
		return result;
	}
}
