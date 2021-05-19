package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ZigEntityPK implements Serializable {
	private String paCode;
	private String partNo;
	private LocalDateTime dataDate;

	@Column(name = "PA_CODE", nullable = false, length = 7)
	@Id
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Column(name = "PARTNO", nullable = false, length = 30)
	@Id
	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	@Column(name = "DATA_DATE", nullable = false)
	@Id
	public LocalDateTime getDataDate() {
		return dataDate;
	}

	public void setDataDate(LocalDateTime dataDate) {
		this.dataDate = dataDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ZigEntityPK that = (ZigEntityPK) o;
		return Objects.equals(paCode, that.paCode) &&
				Objects.equals(partNo, that.partNo) &&
				Objects.equals(dataDate, that.dataDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(paCode, partNo, dataDate);
	}
}
