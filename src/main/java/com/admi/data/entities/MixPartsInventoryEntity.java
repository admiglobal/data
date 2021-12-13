package com.admi.data.entities;

import com.admi.data.enums.MixSource;
import org.bouncycastle.util.Arrays;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "MIX_PARTS_INVENTORY", schema = "ADMI")
public class MixPartsInventoryEntity {
	private Integer mixId;
	private Long dealerId;
	private String documentId;
	private LocalDateTime documentDateTime;
	private Integer qtyOnHand;
	private Long qtySold;
	private Integer qtyLostSale;
	private Long qtyOnOrder;
	private Long qtyReorderPoint;
	private Long qtyTwelveMonthSales;
	private Long qtyTwelveMonthLostSale;
	private String binLocation;
	private Long backOrderQty;
	private LocalDate lastSoldDate;
	private String partSubSourceCode;
	private String stockingStatusCode;
	private LocalDate systemSetupDate;
	private Long qtyBestStockingLevel;
	private Long orderQtyReceived;
	private LocalDate lastReceiptDate;
	private LocalDate dateOfLastOrder;
	private LocalDate lastPhysicalInventory;
	private Long lastReceiptQty;
	private String partNumber;
	private String partItemDescription;
	private String partManufacturer;
	private Long packageQty;
	private Boolean eligibleForReturn;
	private String partSourceCode;
	private String partSourceDescription;
	private String supercededFrom;
	private String supercededTo;
	private Double corePriceAmount;
	private Double partCost;
	private Double unitListPrice;
	private Double cost;
	private Double list;
	private Double standardMsrp;
	private Double unitPrice;
	private Double dealerCost;
	private Double tradeIn;
	private Double actualWholesalePrice;

	private LocalDate inventoryDate;

	public MixPartsInventoryEntity() {}

	public AipInventoryEntity toAipInventoryEntity(MixSource dms) {
		AipInventoryEntity aip = new AipInventoryEntity();

		aip.setDealerId(this.dealerId);
		aip.setPartNo(this.partNumber);
		aip.setCents(getPricing());
		aip.setQoh(this.qtyOnHand);
		aip.setDescription(this.partItemDescription);
		aip.setStatus(this.stockingStatusCode);
		aip.setAdmiStatus(getAdmiStatus(dms));
		aip.setLastSale(this.lastSoldDate);
		aip.setLastReceipt(this.lastReceiptDate);
		aip.setBin(this.binLocation);
		aip.setSource(this.partSourceCode);
		aip.setMfgControlled(null);
		aip.setDataDate(this.inventoryDate);
		aip.setManufacturer(this.partManufacturer);

		return aip;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MIX_ID_S")
	@SequenceGenerator(sequenceName = "MIX_ID_S", allocationSize = 1, name = "MIX_ID_S")
	@Column(name = "MIX_ID", nullable = false, precision = 0)
	public Integer getMixId() {
		return mixId;
	}

	public void setMixId(Integer mixId) {
		this.mixId = mixId;
	}

	@Basic
	@Column(name = "DEALER_ID", nullable = true, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	@Basic
	@Column(name = "DOCUMENT_ID", nullable = true, length = 150)
	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	@Basic
	@Column(name = "DOCUMENT_DATE_TIME", nullable = true)
	public LocalDateTime getDocumentDateTime() {
		return documentDateTime;
	}

	public void setDocumentDateTime(LocalDateTime documentDateTime) {
		this.documentDateTime = documentDateTime;
	}

	@Basic
	@Column(name = "QTY_ON_HAND", nullable = true, precision = 0)
	public Integer getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(Integer qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

	@Basic
	@Column(name = "QTY_SOLD", nullable = true, precision = 0)
	public Long getQtySold() {
		return qtySold;
	}

	public void setQtySold(Long qtySold) {
		this.qtySold = qtySold;
	}

	@Basic
	@Column(name = "QTY_LOST_SALE", nullable = true, precision = 0)
	public Integer getQtyLostSale() {
		return qtyLostSale;
	}

	public void setQtyLostSale(Integer qtyLostSale) {
		this.qtyLostSale = qtyLostSale;
	}

	@Basic
	@Column(name = "QTY_ON_ORDER", nullable = true, precision = 0)
	public Long getQtyOnOrder() {
		return qtyOnOrder;
	}

	public void setQtyOnOrder(Long qtyOnOrder) {
		this.qtyOnOrder = qtyOnOrder;
	}

	@Basic
	@Column(name = "QTY_REORDER_POINT", nullable = true, precision = 0)
	public Long getQtyReorderPoint() {
		return qtyReorderPoint;
	}

	public void setQtyReorderPoint(Long qtyReorderPoint) {
		this.qtyReorderPoint = qtyReorderPoint;
	}

	@Basic
	@Column(name = "QTY_TWELVE_MONTH_SALES", nullable = true, precision = 0)
	public Long getQtyTwelveMonthSales() {
		return qtyTwelveMonthSales;
	}

	public void setQtyTwelveMonthSales(Long qtyTwelveMonthSales) {
		this.qtyTwelveMonthSales = qtyTwelveMonthSales;
	}

	@Basic
	@Column(name = "QTY_TWELVE_MONTH_LOST_SALE", nullable = true, precision = 0)
	public Long getQtyTwelveMonthLostSale() {
		return qtyTwelveMonthLostSale;
	}

	public void setQtyTwelveMonthLostSale(Long qtyTwelveMonthLostSale) {
		this.qtyTwelveMonthLostSale = qtyTwelveMonthLostSale;
	}

	@Basic
	@Column(name = "BIN_LOCATION", nullable = true, length = 30)
	public String getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(String binLocation) {
		this.binLocation = binLocation;
	}

	@Basic
	@Column(name = "BACK_ORDER_QTY", nullable = true, precision = 0)
	public Long getBackOrderQty() {
		return backOrderQty;
	}

	public void setBackOrderQty(Long backOrderQty) {
		this.backOrderQty = backOrderQty;
	}

	@Basic
	@Column(name = "LAST_SOLD_DATE", nullable = true)
	public LocalDate getLastSoldDate() {
		return lastSoldDate;
	}

	public void setLastSoldDate(LocalDate lastSoldDate) {
		this.lastSoldDate = lastSoldDate;
	}

	@Basic
	@Column(name = "PART_SUB_SOURCE_CODE", nullable = true, length = 3)
	public String getPartSubSourceCode() {
		return partSubSourceCode;
	}

	public void setPartSubSourceCode(String partSubSourceCode) {
		this.partSubSourceCode = partSubSourceCode;
	}

	@Basic
	@Column(name = "STOCKING_STATUS_CODE", nullable = true, length = 25)
	public String getStockingStatusCode() {
		return stockingStatusCode;
	}

	public void setStockingStatusCode(String stockingStatusCode) {
		this.stockingStatusCode = stockingStatusCode;
	}

	@Basic
	@Column(name = "SYSTEM_SETUP_DATE", nullable = true)
	public LocalDate getSystemSetupDate() {
		return systemSetupDate;
	}

	public void setSystemSetupDate(LocalDate systemSetupDate) {
		this.systemSetupDate = systemSetupDate;
	}

	@Basic
	@Column(name = "QTY_BEST_STOCKING_LEVEL", nullable = true, length = 3)
	public Long getQtyBestStockingLevel() {
		return qtyBestStockingLevel;
	}

	public void setQtyBestStockingLevel(Long qtyBestStockingLevel) {
		this.qtyBestStockingLevel = qtyBestStockingLevel;
	}

	@Basic
	@Column(name = "ORDER_QTY_RECEIVED", nullable = true, precision = 0)
	public Long getOrderQtyReceived() {
		return orderQtyReceived;
	}

	public void setOrderQtyReceived(Long orderQtyReceived) {
		this.orderQtyReceived = orderQtyReceived;
	}

	@Basic
	@Column(name = "LAST_RECEIPT_DATE", nullable = true)
	public LocalDate getLastReceiptDate() {
		return lastReceiptDate;
	}

	public void setLastReceiptDate(LocalDate lastReceiptDate) {
		this.lastReceiptDate = lastReceiptDate;
	}

	@Basic
	@Column(name = "DATE_OF_LAST_ORDER", nullable = true)
	public LocalDate getDateOfLastOrder() {
		return dateOfLastOrder;
	}

	public void setDateOfLastOrder(LocalDate dateOfLastOrder) {
		this.dateOfLastOrder = dateOfLastOrder;
	}

	@Basic
	@Column(name = "LAST_PHYSICAL_INVENTORY", nullable = true)
	public LocalDate getLastPhysicalInventory() {
		return lastPhysicalInventory;
	}

	public void setLastPhysicalInventory(LocalDate lastPhysicalInventory) {
		this.lastPhysicalInventory = lastPhysicalInventory;
	}

	@Basic
	@Column(name = "LAST_RECEIPT_QTY", nullable = true, precision = 0)
	public Long getLastReceiptQty() {
		return lastReceiptQty;
	}

	public void setLastReceiptQty(Long lastReceiptQty) {
		this.lastReceiptQty = lastReceiptQty;
	}

	@Basic
	@Column(name = "PART_NUMBER", nullable = true, length = 20)
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	@Basic
	@Column(name = "PART_ITEM_DESCRIPTION", nullable = true, length = 15)
	public String getPartItemDescription() {
		return partItemDescription;
	}

	public void setPartItemDescription(String partItemDescription) {
		this.partItemDescription = partItemDescription;
	}

	@Basic
	@Column(name = "PART_MANUFACTURER", nullable = true, length = 2)
	public String getPartManufacturer() {
		return partManufacturer;
	}

	public void setPartManufacturer(String partManufacturer) {
		this.partManufacturer = partManufacturer;
	}

	@Basic
	@Column(name = "PACKAGE_QTY", nullable = true, precision = 0)
	public Long getPackageQty() {
		return packageQty;
	}

	public void setPackageQty(Long packageQty) {
		this.packageQty = packageQty;
	}

	@Basic
	@Column(name = "ELIGIBLE_FOR_RETURN", nullable = true, precision = 0)
	public Boolean getEligibleForReturn() {
		return eligibleForReturn;
	}

	public void setEligibleForReturn(Boolean eligibleForReturn) {
		this.eligibleForReturn = eligibleForReturn;
	}

	@Basic
	@Column(name = "PART_SOURCE_CODE", nullable = true, length = 1)
	public String getPartSourceCode() {
		return partSourceCode;
	}

	public void setPartSourceCode(String partSourceCode) {
		this.partSourceCode = partSourceCode;
	}

	@Basic
	@Column(name = "PART_SOURCE_DESCRIPTION", nullable = true, length = 15)
	public String getPartSourceDescription() {
		return partSourceDescription;
	}

	public void setPartSourceDescription(String partSourceDescription) {
		this.partSourceDescription = partSourceDescription;
	}

	@Basic
	@Column(name = "SUPERCEDED_FROM", nullable = true, length = 20)
	public String getSupercededFrom() {
		return supercededFrom;
	}

	public void setSupercededFrom(String supercededFrom) {
		this.supercededFrom = supercededFrom;
	}

	@Basic
	@Column(name = "SUPERCEDED_TO", nullable = true, length = 20)
	public String getSupercededTo() {
		return supercededTo;
	}

	public void setSupercededTo(String supercededTo) {
		this.supercededTo = supercededTo;
	}

	@Basic
	@Column(name = "CORE_PRICE_AMOUNT", nullable = true, precision = 2)
	public Double getCorePriceAmount() {
		return corePriceAmount;
	}

	public void setCorePriceAmount(Double corePriceAmount) {
		this.corePriceAmount = corePriceAmount;
	}

	@Basic
	@Column(name = "PART_COST", nullable = true, precision = 2)
	public Double getPartCost() {
		return partCost;
	}

	public void setPartCost(Double partCost) {
		this.partCost = partCost;
	}

	@Basic
	@Column(name = "UNIT_LIST_PRICE", nullable = true, precision = 2)
	public Double getUnitListPrice() {
		return unitListPrice;
	}

	public void setUnitListPrice(Double unitListPrice) {
		this.unitListPrice = unitListPrice;
	}

	@Basic
	@Column(name = "COST", nullable = true, precision = 2)
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Basic
	@Column(name = "LIST", nullable = true, precision = 2)
	public Double getList() {
		return list;
	}

	public void setList(Double list) {
		this.list = list;
	}

	@Basic
	@Column(name = "STANDARD_MSRP", nullable = true, precision = 2)
	public Double getStandardMsrp() {
		return standardMsrp;
	}

	public void setStandardMsrp(Double standardMsrp) {
		this.standardMsrp = standardMsrp;
	}

	@Basic
	@Column(name = "UNIT_PRICE", nullable = true, precision = 2)
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Basic
	@Column(name = "DEALER_COST", nullable = true, precision = 2)
	public Double getDealerCost() {
		return dealerCost;
	}

	public void setDealerCost(Double dealerCost) {
		this.dealerCost = dealerCost;
	}

	@Basic
	@Column(name = "TRADE_IN", nullable = true, precision = 2)
	public Double getTradeIn() {
		return tradeIn;
	}

	public void setTradeIn(Double tradeIn) {
		this.tradeIn = tradeIn;
	}

	@Basic
	@Column(name = "ACTUAL_WHOLESALE_PRICE", nullable = true, precision = 2)
	public Double getActualWholesalePrice() {
		return actualWholesalePrice;
	}

	public void setActualWholesalePrice(Double actualWholesalePrice) {
		this.actualWholesalePrice = actualWholesalePrice;
	}

	@Basic
	@Column(name = "INVENTORY_DATE", nullable = true)
	public LocalDate getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	@Transient
	private LocalDate parse(String date) {
		LocalDate newDate;
		if (date != null) {
			try {
				newDate = LocalDate.parse(date);
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} else {
			return null;
		}
		return newDate;
	}

	@Transient
	private Integer getPricing() {
		if (this.cost == null && this.partCost == null)
			return 0;
		else if (this.cost == null)
			return (int) Math.round(this.partCost * 100);
		else
			return (int) Math.round(this.cost * 100);
	}

	@Transient
	private String getAdmiStatus(MixSource dms) {
		List<String> automateStatus = List.of("STOCKED");
		List<String> autosoftStatus = List.of("Y");
		List<String> pbsStatus = List.of("S", "STK");


		String status = "N";

		if (this.stockingStatusCode != null) {
			switch(dms) {
				case AUTOMATE:
					if (automateStatus.contains(this.stockingStatusCode))
						status = "S";
					break;
				case AUTOSOFT:
					if (autosoftStatus.contains(this.stockingStatusCode))
						status = "S";
					break;
				case PBS:
					if (pbsStatus.contains(this.stockingStatusCode))
						status = "S";
					break;
				case DOMINION:
				case QUORUM:
					System.out.println("Found " + dms.getSourceName() + " dealer. Look into " + this.dealerId);
					break;
			}
		}

		return status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MixPartsInventoryEntity that = (MixPartsInventoryEntity) o;
		return Objects.equals(mixId, that.mixId) &&
				Objects.equals(dealerId, that.dealerId) &&
				Objects.equals(documentId, that.documentId) &&
				Objects.equals(documentDateTime, that.documentDateTime) &&
				Objects.equals(qtyOnHand, that.qtyOnHand) &&
				Objects.equals(qtySold, that.qtySold) &&
				Objects.equals(qtyLostSale, that.qtyLostSale) &&
				Objects.equals(qtyOnOrder, that.qtyOnOrder) &&
				Objects.equals(qtyReorderPoint, that.qtyReorderPoint) &&
				Objects.equals(qtyTwelveMonthSales, that.qtyTwelveMonthSales) &&
				Objects.equals(qtyTwelveMonthLostSale, that.qtyTwelveMonthLostSale) &&
				Objects.equals(binLocation, that.binLocation) &&
				Objects.equals(backOrderQty, that.backOrderQty) &&
				Objects.equals(lastSoldDate, that.lastSoldDate) &&
				Objects.equals(partSubSourceCode, that.partSubSourceCode) &&
				Objects.equals(stockingStatusCode, that.stockingStatusCode) &&
				Objects.equals(systemSetupDate, that.systemSetupDate) &&
				Objects.equals(qtyBestStockingLevel, that.qtyBestStockingLevel) &&
				Objects.equals(orderQtyReceived, that.orderQtyReceived) &&
				Objects.equals(lastReceiptDate, that.lastReceiptDate) &&
				Objects.equals(dateOfLastOrder, that.dateOfLastOrder) &&
				Objects.equals(lastPhysicalInventory, that.lastPhysicalInventory) &&
				Objects.equals(lastReceiptQty, that.lastReceiptQty) &&
				Objects.equals(partNumber, that.partNumber) &&
				Objects.equals(partItemDescription, that.partItemDescription) &&
				Objects.equals(partManufacturer, that.partManufacturer) &&
				Objects.equals(packageQty, that.packageQty) &&
				Objects.equals(eligibleForReturn, that.eligibleForReturn) &&
				Objects.equals(partSourceCode, that.partSourceCode) &&
				Objects.equals(partSourceDescription, that.partSourceDescription) &&
				Objects.equals(supercededFrom, that.supercededFrom) &&
				Objects.equals(supercededTo, that.supercededTo) &&
				Objects.equals(corePriceAmount, that.corePriceAmount) &&
				Objects.equals(partCost, that.partCost) &&
				Objects.equals(unitListPrice, that.unitListPrice) &&
				Objects.equals(cost, that.cost) &&
				Objects.equals(list, that.list) &&
				Objects.equals(standardMsrp, that.standardMsrp) &&
				Objects.equals(unitPrice, that.unitPrice) &&
				Objects.equals(dealerCost, that.dealerCost) &&
				Objects.equals(tradeIn, that.tradeIn) &&
				Objects.equals(actualWholesalePrice, that.actualWholesalePrice) &&
				Objects.equals(inventoryDate, that.inventoryDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(mixId, dealerId, documentId, documentDateTime, qtyOnHand, qtySold, qtyLostSale, qtyOnOrder, qtyReorderPoint, qtyTwelveMonthSales, qtyTwelveMonthLostSale, binLocation, backOrderQty, lastSoldDate, partSubSourceCode, stockingStatusCode, systemSetupDate, qtyBestStockingLevel, orderQtyReceived, lastReceiptDate, dateOfLastOrder, lastPhysicalInventory, lastReceiptQty, partNumber, partItemDescription, partManufacturer, packageQty, eligibleForReturn, partSourceCode, partSourceDescription, supercededFrom, supercededTo, corePriceAmount, partCost, unitListPrice, cost, list, standardMsrp, unitPrice, dealerCost, tradeIn, actualWholesalePrice, inventoryDate);
	}

	@Override
	@Transient
	public String toString() {
		return "MixPartsInventoryEntity{" +
				"mixId=" + mixId +
				", dealerId=" + dealerId +
				", documentId='" + documentId + '\'' +
				", documentDateTime=" + documentDateTime +
				", qtyOnHand=" + qtyOnHand +
				", qtySold=" + qtySold +
				", qtyLostSale=" + qtyLostSale +
				", qtyOnOrder=" + qtyOnOrder +
				", qtyReorderPoint=" + qtyReorderPoint +
				", qtyTwelveMonthSales=" + qtyTwelveMonthSales +
				", qtyTwelveMonthLostSale=" + qtyTwelveMonthLostSale +
				", binLocation='" + binLocation + '\'' +
				", backOrderQty=" + backOrderQty +
				", lastSoldDate=" + lastSoldDate +
				", partSubSourceCode='" + partSubSourceCode + '\'' +
				", stockingStatusCode='" + stockingStatusCode + '\'' +
				", systemSetupDate=" + systemSetupDate +
				", qtyBestStockingLevel=" + qtyBestStockingLevel +
				", orderQtyReceived=" + orderQtyReceived +
				", lastReceiptDate=" + lastReceiptDate +
				", dateOfLastOrder=" + dateOfLastOrder +
				", lastPhysicalInventory=" + lastPhysicalInventory +
				", lastReceiptQty=" + lastReceiptQty +
				", partNumber='" + partNumber + '\'' +
				", partItemDescription='" + partItemDescription + '\'' +
				", partManufacturer='" + partManufacturer + '\'' +
				", packageQty=" + packageQty +
				", eligibleForReturn=" + eligibleForReturn +
				", partSourceCode='" + partSourceCode + '\'' +
				", partSourceDescription='" + partSourceDescription + '\'' +
				", supercededFrom='" + supercededFrom + '\'' +
				", supercededTo='" + supercededTo + '\'' +
				", corePriceAmount=" + corePriceAmount +
				", partCost=" + partCost +
				", unitListPrice=" + unitListPrice +
				", cost=" + cost +
				", list=" + list +
				", standardMsrp=" + standardMsrp +
				", unitPrice=" + unitPrice +
				", dealerCost=" + dealerCost +
				", tradeIn=" + tradeIn +
				", actualWholesalePrice=" + actualWholesalePrice +
				", inventoryDate=" + inventoryDate +
				'}';
	}
}
