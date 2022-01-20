package com.admi.data.dto;

import com.admi.data.entities.AipInventoryEntity;
import com.sun.istack.NotNull;

import java.time.LocalDate;

public class RRDto {

	private String partNo;
	private Long costCents;
	private Long quantityOnHand;
	private String description;
	private String status;
	private LocalDate lastSaleDate;
	private LocalDate lastReceiptDate;
	private String bin;
	private String source;
	private String make;
	private Boolean mfgControlled;
	private Long min;
	private Long max;
	private Long bestStockingLevel;
	private Long quantityPerRepair;
	private Long history6;
	private Long history12;
	private Long history24;

	public RRDto(){}

	public RRDto(String partNo, Long costCents, Long quantityOnHand, String description, String status, LocalDate lastSaleDate, LocalDate lastReceiptDate, String bin, String source, String make, Boolean mfgControlled, Long min, Long max, Long bestStockingLevel, Long quantityPerRepair, Long history6, Long history12, Long history24) {
		this.partNo = partNo;
		this.costCents = costCents;
		this.quantityOnHand = quantityOnHand;
		this.description = description;
		this.status = status;
		this.lastSaleDate = lastSaleDate;
		this.lastReceiptDate = lastReceiptDate;
		this.bin = bin;
		this.source = source;
		this.make = make;
		this.mfgControlled = mfgControlled;
		this.min = min;
		this.max = max;
		this.bestStockingLevel = bestStockingLevel;
		this.quantityPerRepair = quantityPerRepair;
		this.history6 = history6;
		this.history12 = history12;
		this.history24 = history24;
	}

	public AipInventoryEntity toAipInventory(@NotNull Long dealerId, @NotNull LocalDate date, Boolean setMfgConrolled) {

		AipInventoryEntity inv = new AipInventoryEntity();

		inv.setDealerId(dealerId);
		inv.setPartNo(this.getAlphaNumPartNo());
		inv.setCents(this.costCents == null ? null : Math.toIntExact(this.costCents));
		inv.setQoh(this.quantityOnHand == null ? null : Math.toIntExact(this.quantityOnHand));
		inv.setDescription(this.description);
		inv.setStatus(this.getDmsStatus());
		inv.setAdmiStatus(this.getAdmiStatus());
		inv.setLastSale(getDefaultDateIfNull(this.lastSaleDate));
		inv.setLastReceipt(getDefaultDateIfNull(this.lastReceiptDate));
		inv.setBin(this.bin);
		inv.setSource(this.source);

		if (setMfgConrolled)
			inv.setMfgControlled(this.mfgControlled);
		else
			inv.setMfgControlled(Boolean.valueOf(null));

		inv.setDataDate(date);
		inv.setManufacturer(this.make);

		return inv;
	}

	private String getAlphaNumPartNo() {
		if (this.partNo != null) {
			return this.partNo.replaceAll("[^a-zA-Z0-9]", "");
		} else {
			return null;
		}
	}

	private String getAdmiStatus() {
		if (this.status == null || this.status.equals("RB")) {
			return "S";
		} else {
			return "N";
		}
	}

	private String getDmsStatus() {
		if (this.status == null) {
			return "STOCK";
		} else {
			return this.status;
		}
	}

	private LocalDate getDefaultDateIfNull(LocalDate date) {
		if (date == null) {
			return LocalDate.of(2000,1,1);
		} else {
			return date;
		}
	}

	private Long getDefaultLongIfNull(Long number) {
		if (number == null) {
			return 0L;
		} else {
			return number;
		}
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Long getCostCents() {
		return costCents;
	}

	public void setCostCents(Long costCents) {
		this.costCents = costCents;
	}

	public Long getQuantityOnHand() {
		return quantityOnHand;
	}

	public void setQuantityOnHand(Long quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getLastSaleDate() {
		return lastSaleDate;
	}

	public void setLastSaleDate(LocalDate lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}

	public LocalDate getLastReceiptDate() {
		return lastReceiptDate;
	}

	public void setLastReceiptDate(LocalDate lastReceiptDate) {
		this.lastReceiptDate = lastReceiptDate;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Boolean getMfgControlled() {
		return mfgControlled;
	}

	public void setMfgControlled(Boolean mfgControlled) {
		this.mfgControlled = mfgControlled;
	}

	public void setMfgControlled(String manuControlled) {
		if (manuControlled.equalsIgnoreCase("Y")) {
			this.mfgControlled = true;
		} else if (manuControlled.equalsIgnoreCase("N")) {
			this.mfgControlled = false;
		} else {
			this.mfgControlled = null;
		}
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getBestStockingLevel() {
		return bestStockingLevel;
	}

	public void setBestStockingLevel(Long bestStockingLevel) {
		this.bestStockingLevel = bestStockingLevel;
	}

	public Long getQuantityPerRepair() {
		return quantityPerRepair;
	}

	public void setQuantityPerRepair(Long quantityPerRepair) {
		this.quantityPerRepair = quantityPerRepair;
	}

	public Long getHistory6() {
		return history6;
	}

	public void setHistory6(Long history6) {
		this.history6 = history6;
	}

	public Long getHistory12() {
		return history12;
	}

	public void setHistory12(Long history12) {
		this.history12 = history12;
	}

	public Long getHistory24() {
		return history24;
	}

	public void setHistory24(Long history24) {
		this.history24 = history24;
	}

	@Override
	public String toString() {
		return "RRDto{" +
				"partNo='" + partNo + '\'' +
				", costCents=" + costCents +
				", quantityOnHand=" + quantityOnHand +
				", description='" + description + '\'' +
				", status='" + status + '\'' +
				", lastSaleDate=" + lastSaleDate +
				", lastReceiptDate=" + lastReceiptDate +
				", bin='" + bin + '\'' +
				", source='" + source + '\'' +
				", make='" + make + '\'' +
				", mfgControlled=" + mfgControlled +
				", min=" + min +
				", max=" + max +
				", bestStockingLevel=" + bestStockingLevel +
				", quantityPerRepair=" + quantityPerRepair +
				", history6=" + history6 +
				", history12=" + history12 +
				", history24=" + history24 +
				'}';
	}
}
