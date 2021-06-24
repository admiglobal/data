package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MC_ORDERS", schema = "ADMI")
public class McOrdersEntity {
	private String orderNumber;
	private String paCode;
	private Long mtgId;
	private String orderer;
	private String email;
	private String cell;
	private Long cellPermission;
	private Long noShow;
	private LocalDateTime placed;
	private LocalDateTime lastUpdated;
	private Long phase;
	private Long extension;
	private String poNumber;
	private LocalDate orderDate;

	@Id
	@Column(name = "ORDER_NUMBER", nullable = false, length = 20)
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Basic
	@Column(name = "PA_CODE", nullable = true, length = 5)
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Basic
	@Column(name = "MTG_ID", nullable = true, precision = 0)
	public Long getMtgId() {
		return mtgId;
	}

	public void setMtgId(Long mtgId) {
		this.mtgId = mtgId;
	}

	@Basic
	@Column(name = "ORDERER", nullable = true, length = 100)
	public String getOrderer() {
		return orderer;
	}

	public void setOrderer(String orderer) {
		this.orderer = orderer;
	}

	@Basic
	@Column(name = "EMAIL", nullable = true, length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "CELL", nullable = true, length = 15)
	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	@Basic
	@Column(name = "CELL_PERMISSION", nullable = true, precision = 0)
	public Long getCellPermission() {
		return cellPermission;
	}

	public void setCellPermission(Long cellPermission) {
		this.cellPermission = cellPermission;
	}

	@Basic
	@Column(name = "NO_SHOW", nullable = true, precision = 0)
	public Long getNoShow() {
		return noShow;
	}

	public void setNoShow(Long noShow) {
		this.noShow = noShow;
	}

	@Basic
	@Column(name = "PLACED", nullable = true)
	public LocalDateTime getPlaced() {
		return placed;
	}

	public void setPlaced(LocalDateTime placed) {
		this.placed = placed;
	}

	@Basic
	@Column(name = "LAST_UPDATED", nullable = true)
	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Basic
	@Column(name = "PHASE", nullable = true, precision = 0)
	public Long getPhase() {
		return phase;
	}

	public void setPhase(Long phase) {
		this.phase = phase;
	}

	@Basic
	@Column(name = "EXTENSION", nullable = true, precision = 0)
	public Long getExtension() {
		return extension;
	}

	public void setExtension(Long extension) {
		this.extension = extension;
	}

	@Basic
	@Column(name = "PO_NUMBER", nullable = true, length = 30)
	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	@Basic
	@Column(name = "ORDER_DATE", nullable = true)
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		McOrdersEntity that = (McOrdersEntity) o;

		if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
		if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null) return false;
		if (mtgId != null ? !mtgId.equals(that.mtgId) : that.mtgId != null) return false;
		if (orderer != null ? !orderer.equals(that.orderer) : that.orderer != null) return false;
		if (email != null ? !email.equals(that.email) : that.email != null) return false;
		if (cell != null ? !cell.equals(that.cell) : that.cell != null) return false;
		if (cellPermission != null ? !cellPermission.equals(that.cellPermission) : that.cellPermission != null)
			return false;
		if (noShow != null ? !noShow.equals(that.noShow) : that.noShow != null) return false;
		if (placed != null ? !placed.equals(that.placed) : that.placed != null) return false;
		if (lastUpdated != null ? !lastUpdated.equals(that.lastUpdated) : that.lastUpdated != null) return false;
		if (phase != null ? !phase.equals(that.phase) : that.phase != null) return false;
		if (extension != null ? !extension.equals(that.extension) : that.extension != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = paCode != null ? paCode.hashCode() : 0;
		result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
		result = 31 * result + (mtgId != null ? mtgId.hashCode() : 0);
		result = 31 * result + (orderer != null ? orderer.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (cell != null ? cell.hashCode() : 0);
		result = 31 * result + (cellPermission != null ? cellPermission.hashCode() : 0);
		result = 31 * result + (noShow != null ? noShow.hashCode() : 0);
		result = 31 * result + (placed != null ? placed.hashCode() : 0);
		result = 31 * result + (lastUpdated != null ? lastUpdated.hashCode() : 0);
		result = 31 * result + (phase != null ? phase.hashCode() : 0);
		result = 31 * result + (extension != null ? extension.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "McOrdersEntity{" +
				"paCode='" + paCode + '\'' +
				", orderNumber='" + orderNumber + '\'' +
				", mtgId=" + mtgId +
				", orderer='" + orderer + '\'' +
				", email='" + email + '\'' +
				", cell='" + cell + '\'' +
				", cellPermission=" + cellPermission +
				", noShow=" + noShow +
				", placed=" + placed +
				", lastUpdated=" + lastUpdated +
				", phase=" + phase +
				", extension=" + extension +
				", poNumber='" + poNumber + '\'' +
				", orderDate=" + orderDate +
				'}';
	}
}
