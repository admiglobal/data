package com.admi.data.entities;

import com.admi.data.entities.converters.ZigDateConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "AIS_ZIG_NEW", schema = "ADMI")
@IdClass(com.admi.data.entities.keys.ZigEntityPK.class)
public class ZigEntity {
	private String paCode;
	private String partNo;
	private Long qoh;
	private String des;
	private LocalDate lsDate;
	private LocalDate lrDate;
	private LocalDateTime dataDate;
	private String status;
	private String rFlag;
	private BigDecimal cost;
	private String bin;
	private String src;
	private String dmsStatus;
	private Boolean mfgControlled;

	@Id
	@Column(name = "PA_CODE", nullable = false, length = 7)
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Id
	@Column(name = "PARTNO", nullable = false, length = 30)
	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	@Basic
	@Column(name = "QOH", nullable = true, precision = 0)
	public Long getQoh() {
		return qoh;
	}

	public void setQoh(Long qoh) {
		this.qoh = qoh;
	}

	@Basic
	@Column(name = "DES", nullable = true, length = 25)
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Basic
	@Column(name = "LS_DATE", nullable = true, length = 11)
	@Convert(converter = ZigDateConverter.class)
	public LocalDate getLsDate() {
		return lsDate;
	}

	public void setLsDate(LocalDate lsDate) {
		this.lsDate = lsDate;
	}

	@Basic
	@Column(name = "LR_DATE", nullable = true, length = 11)
	@Convert(converter = ZigDateConverter.class)
	public LocalDate getLrDate() {
		return lrDate;
	}

	public void setLrDate(LocalDate lrDate) {
		this.lrDate = lrDate;
	}

	@Id
	@Column(name = "DATA_DATE", nullable = false)
	public LocalDateTime getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDateTime dataDate) {
		this.dataDate = dataDate;
	}

	@Basic
	@Column(name = "STATUS", nullable = true, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Basic
	@Column(name = "RFLAG", nullable = true, length = 1)
	public String getRFlag() {
		return rFlag;
	}

	public void setRFlag(String rflag) {
		this.rFlag = rflag;
	}

	@Transient
	public void setRFlag(Boolean returnable) {
		if (returnable) {
			this.rFlag = "Y";
		} else {
			this.rFlag = "N";
		}
	}

	@Basic
	@Column(name = "COST", precision = 12, scale = 2)
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}


	@Basic
	@Column(name = "BIN", nullable = true, length = 20)
	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	@Basic
	@Column(name = "SRC", nullable = true, length = 7)
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Basic
	@Column(name = "DMS_STS", nullable = true, length = 20)
	public String getDmsStatus() {
		return dmsStatus;
	}

	public void setDmsStatus(String dmsSts) {
		this.dmsStatus = dmsSts;
	}

	@Basic
	@Column(name = "MFG_CONTROLLED")
	public Boolean getMfgControlled() {
		return mfgControlled;
	}

	public void setMfgControlled(Boolean mfg_controlled) {
		this.mfgControlled = mfg_controlled;
	}

	@Transient
	public Long getDaysSinceReceipt() {
		return ChronoUnit.DAYS.between(this.lrDate, LocalDate.now());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ZigEntity that = (ZigEntity) o;

		if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
		if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
		if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;
		if (des != null ? !des.equals(that.des) : that.des != null) return false;
		if (lsDate != null ? !lsDate.equals(that.lsDate) : that.lsDate != null) return false;
		if (lrDate != null ? !lrDate.equals(that.lrDate) : that.lrDate != null) return false;
		if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (rFlag != null ? !rFlag.equals(that.rFlag) : that.rFlag != null) return false;
		if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = paCode != null ? paCode.hashCode() : 0;
		result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
		result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
		result = 31 * result + (des != null ? des.hashCode() : 0);
		result = 31 * result + (lsDate != null ? lsDate.hashCode() : 0);
		result = 31 * result + (lrDate != null ? lrDate.hashCode() : 0);
		result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (rFlag != null ? rFlag.hashCode() : 0);
		result = 31 * result + (cost != null ? cost.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ZigEntity{" +
				"paCode='" + paCode + '\'' +
				", partNo='" + partNo + '\'' +
				", qoh=" + qoh +
				", des='" + des + '\'' +
				", lsDate=" + lsDate +
				", lrDate=" + lrDate +
				", dataDate=" + dataDate +
				", status='" + status + '\'' +
				", rFlag='" + rFlag + '\'' +
				", cost=" + cost +
				", bin='" + bin + '\'' +
				", src='" + src + '\'' +
				", dmsStatus='" + dmsStatus + '\'' +
				", mfgControlled=" + mfgControlled +
				'}';
	}
}
