package com.admi.data.entities;

import com.admi.data.entities.keys.FcsdProgramCreditsEntityPK;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FCSD_PROGRAM_CREDITS", schema = "ADMI")
@IdClass(FcsdProgramCreditsEntityPK.class)
public class FcsdProgramCreditsEntity {
	private Long dealerId;
	private LocalDate dataDate;
	private BigDecimal ima;
	private BigDecimal dtc;
	private BigDecimal ww;
	private BigDecimal wgf;
	private BigDecimal wg;
	private BigDecimal wdd;
	private BigDecimal wf;
	private BigDecimal sixtyDay;
	private BigDecimal nr;
	private BigDecimal wj;
	private BigDecimal wec;

	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Id
	@Column(name = "DATA_DATE", nullable = false)
	public LocalDate getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDate dataDate) {
		this.dataDate = dataDate;
	}

	@Basic
	@Column(name = "IMA", nullable = true, precision = 2)
	public BigDecimal getIma() {
		return ima;
	}

	public void setIma(BigDecimal ima) {
		this.ima = ima;
	}

	@Basic
	@Column(name = "DTC", nullable = true, precision = 2)
	public BigDecimal getDtc() {
		return dtc;
	}

	public void setDtc(BigDecimal dtc) {
		this.dtc = dtc;
	}

	@Basic
	@Column(name = "WW", nullable = true, precision = 2)
	public BigDecimal getWw() {
		return ww;
	}

	public void setWw(BigDecimal ww) {
		this.ww = ww;
	}

	@Basic
	@Column(name = "WGF", nullable = true, precision = 2)
	public BigDecimal getWgf() {
		return wgf;
	}

	public void setWgf(BigDecimal wgf) {
		this.wgf = wgf;
	}

	@Basic
	@Column(name = "WG", nullable = true, precision = 2)
	public BigDecimal getWg() {
		return wg;
	}

	public void setWg(BigDecimal wg) {
		this.wg = wg;
	}

	@Basic
	@Column(name = "WDD", nullable = true, precision = 2)
	public BigDecimal getWdd() {
		return wdd;
	}

	public void setWdd(BigDecimal wdd) {
		this.wdd = wdd;
	}

	@Basic
	@Column(name = "WF", nullable = true, precision = 2)
	public BigDecimal getWf() {
		return wf;
	}

	public void setWf(BigDecimal wf) {
		this.wf = wf;
	}

	@Basic
	@Column(name = "SIXTY_DAY", nullable = true, precision = 2)
	public BigDecimal getSixtyDay() {
		return sixtyDay;
	}

	public void setSixtyDay(BigDecimal sixtyDay) {
		this.sixtyDay = sixtyDay;
	}

	@Basic
	@Column(name = "NR", nullable = true, precision = 2)
	public BigDecimal getNr() {
		return nr;
	}

	public void setNr(BigDecimal nr) {
		this.nr = nr;
	}

	@Basic
	@Column(name = "WJ", nullable = true, precision = 2)
	public BigDecimal getWj() {
		return wj;
	}

	public void setWj(BigDecimal wj) {
		this.wj = wj;
	}

	@Basic
	@Column(name = "WEC", nullable = true, precision = 2)
	public BigDecimal getWec() {
		return wec;
	}

	public void setWec(BigDecimal wec) {
		this.wec = wec;
	}

	@Override
	public String toString() {
		return "FcsdProgramCreditsEntity{" +
				"dealerId=" + dealerId +
				", dataDate=" + dataDate +
				", ima=" + ima +
				", dtc=" + dtc +
				", ww=" + ww +
				", wgf=" + wgf +
				", wg=" + wg +
				", wdd=" + wdd +
				", wf=" + wf +
				", sixtyDay=" + sixtyDay +
				", nr=" + nr +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FcsdProgramCreditsEntity that = (FcsdProgramCreditsEntity) o;
		return Objects.equals(dealerId, that.dealerId) && Objects.equals(dataDate, that.dataDate) && Objects.equals(ima, that.ima) && Objects.equals(dtc, that.dtc) && Objects.equals(ww, that.ww) && Objects.equals(wgf, that.wgf) && Objects.equals(wg, that.wg) && Objects.equals(wdd, that.wdd) && Objects.equals(wf, that.wf) && Objects.equals(sixtyDay, that.sixtyDay) && Objects.equals(nr, that.nr);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, dataDate, ima, dtc, ww, wgf, wg, wdd, wf, sixtyDay, nr);
	}
}
