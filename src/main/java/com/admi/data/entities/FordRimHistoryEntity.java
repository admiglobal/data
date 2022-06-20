package com.admi.data.entities;

import com.admi.data.entities.keys.FordRimHistoryEntityPK;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FORD_DEALER_RIM_HISTORY", schema = "ADMI", catalog = "")
@IdClass(FordRimHistoryEntityPK.class)
public class FordRimHistoryEntity {
	private String paCode;
	private String partNumber;
	private String status;
	private LocalDate contentDate;

	@Id
	@Column(name = "PA_CODE", nullable = false, length = 5)
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Id
	@Column(name = "PART_NUMBER", nullable = false, length = 22)
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Basic
	@Column(name = "STATUS", nullable = true, length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Basic
	@Column(name = "CONTENT_DATE", nullable = false)
	public LocalDate getContentDate() {
		return contentDate;
	}

	public void setContentDate(LocalDate contentDate) {
		this.contentDate = contentDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FordRimHistoryEntity that = (FordRimHistoryEntity) o;

		if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
		if (partNumber != null ? !partNumber.equals(that.partNumber) : that.partNumber != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (contentDate != null ? !contentDate.equals(that.contentDate) : that.contentDate != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = paCode != null ? paCode.hashCode() : 0;
		result = 31 * result + (partNumber != null ? partNumber.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (contentDate != null ? contentDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "FordRimHistoryEntity{" +
				"paCode='" + paCode + '\'' +
				", partNumber='" + partNumber + '\'' +
				", status='" + status + '\'' +
				", contentDate=" + contentDate +
				'}';
	}
}
