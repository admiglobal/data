package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CDK_DEALERS", schema = "ADMI")
public class CdkDealersEntity {
	private String dealerId;
	private Long admiDealerId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean partsinventory;
	private Boolean partssales;
	private Boolean serviceROs;
	private LocalDate installation;

	@Id
	@Column(name = "DEALER_ID", nullable = false, length = 10)
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	@Basic
	@Column(name = "ADMI_DEALER_ID", nullable = true, precision = 0)
	public Long getAdmiDealerId() {
		return admiDealerId;
	}

	public void setAdmiDealerId(Long admiDealerId) {
		this.admiDealerId = admiDealerId;
	}

	@Basic
	@Column(name = "START_DATE", nullable = true)
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Basic
	@Column(name = "END_DATE", nullable = true)
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Basic
	@Column(name = "PARTSINVENTORY", nullable = true, precision = 0)
	public Boolean getPartsinventory() {
		return partsinventory;
	}

	public void setPartsinventory(Boolean partsinventory) {
		this.partsinventory = partsinventory;
	}

	@Basic
	@Column(name = "PARTSSALES", nullable = true, precision = 0)
	public Boolean getPartssales() {
		return partssales;
	}

	public void setPartssales(Boolean partssales) {
		this.partssales = partssales;
	}

	@Basic
	@Column(name = "SERVICEROS", nullable = true, precision = 0)
	public Boolean getServiceROs() {
		return serviceROs;
	}

	public void setServiceROs(Boolean serviceros) {
		this.serviceROs = serviceros;
	}

	@Basic
	@Column(name = "INSTALLATION", nullable = true)
	public LocalDate getInstallation() {
		return installation;
	}

	public void setInstallation(LocalDate installation) {
		this.installation = installation;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CdkDealersEntity that = (CdkDealersEntity) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (admiDealerId != null ? !admiDealerId.equals(that.admiDealerId) : that.admiDealerId != null) return false;
		if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
		if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
		if (partsinventory != null ? !partsinventory.equals(that.partsinventory) : that.partsinventory != null)
			return false;
		if (partssales != null ? !partssales.equals(that.partssales) : that.partssales != null) return false;
		if (serviceROs != null ? !serviceROs.equals(that.serviceROs) : that.serviceROs != null) return false;
		if (installation != null ? !installation.equals(that.installation) : that.installation != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (admiDealerId != null ? admiDealerId.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		result = 31 * result + (partsinventory != null ? partsinventory.hashCode() : 0);
		result = 31 * result + (partssales != null ? partssales.hashCode() : 0);
		result = 31 * result + (serviceROs != null ? serviceROs.hashCode() : 0);
		result = 31 * result + (installation != null ? installation.hashCode() : 0);
		return result;
	}
}
