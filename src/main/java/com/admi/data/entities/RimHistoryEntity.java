package com.admi.data.entities;

import com.admi.data.entities.keys.RimHistoryEntityPK;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AIP_RIM_HISTORY", schema = "ADMI")
@IdClass(RimHistoryEntityPK.class)
public class RimHistoryEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	private Long dealerId;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "PARTNO", nullable = false, length = 30)
	private String partno;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "PHASE_IN", nullable = false)
	private LocalDate phaseIn;
	@Basic
	@Column(name = "PHASE_OUT", nullable = true)
	private LocalDate phaseOut;

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

	public LocalDate getPhaseOut() {
		return phaseOut;
	}

	public void setPhaseOut(LocalDate phaseOut) {
		this.phaseOut = phaseOut;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RimHistoryEntity that = (RimHistoryEntity) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (partno != null ? !partno.equals(that.partno) : that.partno != null) return false;
		if (phaseIn != null ? !phaseIn.equals(that.phaseIn) : that.phaseIn != null) return false;
		if (phaseOut != null ? !phaseOut.equals(that.phaseOut) : that.phaseOut != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (partno != null ? partno.hashCode() : 0);
		result = 31 * result + (phaseIn != null ? phaseIn.hashCode() : 0);
		result = 31 * result + (phaseOut != null ? phaseOut.hashCode() : 0);
		return result;
	}
}
