package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

public class RimHistoryEntityPK implements Serializable {
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dealerId;
	@Column(name = "PARTNO", nullable = false, length = 30)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String partno;
	@Column(name = "PHASE_IN", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private LocalDate phaseIn;

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getPartno() {
		return partno;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	public LocalDate getPhaseIn() {
		return phaseIn;
	}

	public void setPhaseIn(LocalDate phaseIn) {
		this.phaseIn = phaseIn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RimHistoryEntityPK that = (RimHistoryEntityPK) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (partno != null ? !partno.equals(that.partno) : that.partno != null) return false;
		if (phaseIn != null ? !phaseIn.equals(that.phaseIn) : that.phaseIn != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (partno != null ? partno.hashCode() : 0);
		result = 31 * result + (phaseIn != null ? phaseIn.hashCode() : 0);
		return result;
	}
}
