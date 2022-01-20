package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class CdkInventoryPK implements Serializable {
	private String hostItemId;
	private String dealerId;
	private LocalDate inventoryDate;

	@Column(name = "HOST_ITEM_ID", nullable = false, length = 40)
	@Id
	public String getHostItemId() {
		return hostItemId;
	}

	public void setHostItemId(String hostItemId) {
		this.hostItemId = hostItemId;
	}

	@Column(name = "DEALER_ID", nullable = false, length = 7)
	@Id
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	@Column(name = "INVENTORY_DATE", nullable = false)
	@Id
	public LocalDate getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CdkInventoryPK that = (CdkInventoryPK) o;
		return Objects.equals(hostItemId, that.hostItemId) &&
				Objects.equals(dealerId, that.dealerId) &&
				Objects.equals(inventoryDate, that.inventoryDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hostItemId, dealerId, inventoryDate);
	}
}
