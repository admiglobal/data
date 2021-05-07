package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class KpiEntityPK implements Serializable {
	private long dealerId;
	private long dataDate;

	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	@Id
	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "DATADATE", nullable = false, precision = 0)
	@Id
	public long getDataDate() {
		return dataDate;
	}

	public void setDataDate(long dataDate) {
		this.dataDate = dataDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KpiEntityPK that = (KpiEntityPK) o;
		return dealerId == that.dealerId &&
				dataDate == that.dataDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, dataDate);
	}
}
