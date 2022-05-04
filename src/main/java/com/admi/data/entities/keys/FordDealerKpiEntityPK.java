package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

public class FordDealerKpiEntityPK implements Serializable {
	private Long dealerId;
	private LocalDate dateUpdated;

	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	@Id
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "DATE_UPDATED", nullable = false)
	@Id
	public LocalDate getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDate dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FordDealerKpiEntityPK that = (FordDealerKpiEntityPK) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (dateUpdated != null ? !dateUpdated.equals(that.dateUpdated) : that.dateUpdated != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (dateUpdated != null ? dateUpdated.hashCode() : 0);
		return result;
	}
}
