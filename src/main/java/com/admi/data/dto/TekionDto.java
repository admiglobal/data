package com.admi.data.dto;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.services.CdkImportService;
import com.admi.data.services.SpreadsheetService;

import java.time.LocalDate;

public class TekionDto {

    private String partNumber;
    private String partDescription;
    private String stockingStatus;
    private Integer onHandQty;
    private Integer excessStockQty;
    private Integer onHoldQty;
    private Integer onOrderQty;
    private String bins;
    private Double partCostDollars;
    private Double coreCostDollars;
    private Double totalPartCostDollars;
    private Double totalExtendedCostDollars;
    private Double totalExcessStockCost;
    private Integer sourceCode;
    private String manuallyControlled;
    private String manufacturer;
    private String manufacturerControlCode;
    private String manufacturerControlled;
    private String groupNumber;
    private String partsClassificationCode;
    private Integer monthNoSale;
    private Integer monthNoReceipt;
    private Integer twelveMonthSaleQty;
    private Integer jan;
    private Integer feb;
    private Integer mar;
    private Integer apr;
    private Integer may;
    private Integer jun;
    private Integer jul;
    private Integer aug;
    private Integer sep;
    private Integer oct;
    private Integer nov;
    private Integer dec;
    private String manualOrder;
    private LocalDate createdDate;
    private LocalDate lastTransactionTime;
    private LocalDate lastSaleDate;
    private LocalDate lastReceiptDate;

    public TekionDto() {}

    public AipInventoryEntity toAipInventory(Long dealerId, LocalDate date) {

        AipInventoryEntity inv = new AipInventoryEntity();

//        inv.setDealerId(dealerId);
//        inv.setPartNo(SpreadsheetService.getPartNumberDbFormat(partNo, this.hashCode()));
//        inv.setCents(this.costCents == null ? 0 : Math.toIntExact(this.costCents));
//        inv.setQoh(this.quantityOnHand == null ? null : Math.toIntExact(this.quantityOnHand));
//        inv.setDescription(this.description);
//        inv.setStatus(getModifiedSpecialStatus());
//        inv.setLastSale(this.getLastSaleDate());
//        inv.setLastReceipt(this.getLastReceiptDate());
//        inv.setBin(this.bin);
//        inv.setSource(this.source);
//        //mfgControlled
//        inv.setDataDate(date);
//        inv.setAdmiStatus(CdkImportService.getAdmiStatus(this.getStatus()));
//        //manufacturer
//        inv.setQoo(this.quantityOnOrder == null ? null : Math.toIntExact(this.quantityOnOrder));
//        inv.setTwelveMonthSales(this.getTwelveMonthSales());
//        inv.setEntryDate(this.getEntry());
//        inv.setYtdMonthsWithSales(this.calculateYtdMonthsWithSales());

        return inv;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getStockingStatus() {
        return stockingStatus;
    }

    public void setStockingStatus(String stockingStatus) {
        this.stockingStatus = stockingStatus;
    }

    public Integer getOnHandQty() {
        return onHandQty;
    }

    public void setOnHandQty(Integer onHandQty) {
        this.onHandQty = onHandQty;
    }

    public Integer getExcessStockQty() {
        return excessStockQty;
    }

    public void setExcessStockQty(Integer excessStockQty) {
        this.excessStockQty = excessStockQty;
    }

    public Integer getOnHoldQty() {
        return onHoldQty;
    }

    public void setOnHoldQty(Integer onHoldQty) {
        this.onHoldQty = onHoldQty;
    }

    public Integer getOnOrderQty() {
        return onOrderQty;
    }

    public void setOnOrderQty(Integer onOrderQty) {
        this.onOrderQty = onOrderQty;
    }

    public String getBins() {
        return bins;
    }

    public void setBins(String bins) {
        this.bins = bins;
    }

    public Double getPartCostDollars() {
        return partCostDollars;
    }

    public void setPartCostDollars(Double partCostDollars) {
        this.partCostDollars = partCostDollars;
    }

    public Double getCoreCostDollars() {
        return coreCostDollars;
    }

    public void setCoreCostDollars(Double coreCostDollars) {
        this.coreCostDollars = coreCostDollars;
    }


    public Double getTotalPartCostDollars() {
        return totalPartCostDollars;
    }

    public void setTotalPartCostDollars(Double totalPartCostDollars) {
        this.totalPartCostDollars = totalPartCostDollars;
    }


    public Double getTotalExtendedCostDollars() {
        return totalExtendedCostDollars;
    }

    public void setTotalExtendedCostDollars(Double totalExtendedCostDollars) {
        this.totalExtendedCostDollars = totalExtendedCostDollars;
    }

    public Double getTotalExcessStockCost() {
        return totalExcessStockCost;
    }

    public void setTotalExcessStockCost(Double totalExcessStockCost) {
        this.totalExcessStockCost = totalExcessStockCost;
    }

    public Integer getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(Integer sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getManuallyControlled() {
        return manuallyControlled;
    }

    public void setManuallyControlled(String manuallyControlled) {
        this.manuallyControlled = manuallyControlled;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerControlCode() {
        return manufacturerControlCode;
    }

    public void setManufacturerControlCode(String manufacturerControlCode) {
        this.manufacturerControlCode = manufacturerControlCode;
    }

    public String getManufacturerControlled() {
        return manufacturerControlled;
    }

    public void setManufacturerControlled(String manufacturerControlled) {
        this.manufacturerControlled = manufacturerControlled;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getPartsClassificationCode() {
        return partsClassificationCode;
    }

    public void setPartsClassificationCode(String partsClassificationCode) {
        this.partsClassificationCode = partsClassificationCode;
    }

    public Integer getMonthNoSale() {
        return monthNoSale;
    }

    public void setMonthNoSale(Integer monthNoSale) {
        this.monthNoSale = monthNoSale;
    }

    public Integer getMonthNoReceipt() {
        return monthNoReceipt;
    }

    public void setMonthNoReceipt(Integer monthNoReceipt) {
        this.monthNoReceipt = monthNoReceipt;
    }

    public Integer getTwelveMonthSaleQty() {
        return twelveMonthSaleQty;
    }

    public void setTwelveMonthSaleQty(Integer twelveMonthSaleQty) {
        this.twelveMonthSaleQty = twelveMonthSaleQty;
    }

    public Integer getJan() {
        return jan;
    }

    public void setJan(Integer jan) {
        this.jan = jan;
    }

    public Integer getFeb() {
        return feb;
    }

    public void setFeb(Integer feb) {
        this.feb = feb;
    }

    public Integer getMar() {
        return mar;
    }

    public void setMar(Integer mar) {
        this.mar = mar;
    }

    public Integer getApr() {
        return apr;
    }

    public void setApr(Integer apr) {
        this.apr = apr;
    }

    public Integer getMay() {
        return may;
    }

    public void setMay(Integer may) {
        this.may = may;
    }

    public Integer getJun() {
        return jun;
    }

    public void setJun(Integer jun) {
        this.jun = jun;
    }

    public Integer getJul() {
        return jul;
    }

    public void setJul(Integer jul) {
        this.jul = jul;
    }

    public Integer getAug() {
        return aug;
    }

    public void setAug(Integer aug) {
        this.aug = aug;
    }

    public Integer getSep() {
        return sep;
    }

    public void setSep(Integer sep) {
        this.sep = sep;
    }

    public Integer getOct() {
        return oct;
    }

    public void setOct(Integer oct) {
        this.oct = oct;
    }

    public Integer getNov() {
        return nov;
    }

    public void setNov(Integer nov) {
        this.nov = nov;
    }

    public Integer getDec() {
        return dec;
    }

    public void setDec(Integer dec) {
        this.dec = dec;
    }

    public String getManualOrder() {
        return manualOrder;
    }

    public void setManualOrder(String manualOrder) {
        this.manualOrder = manualOrder;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastTransactionTime() {
        return lastTransactionTime;
    }

    public void setLastTransactionTime(LocalDate lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
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

    @Override
    public String toString() {
        return "TekionDto{" +
                "partNumber='" + partNumber + '\'' +
                ", partDescription='" + partDescription + '\'' +
                ", stockingStatus='" + stockingStatus + '\'' +
                ", onHandQty=" + onHandQty +
//                ", excessStockQty=" + excessStockQty +
//                ", onHoldQty=" + onHoldQty +
//                ", onOrderQty=" + onOrderQty +
//                ", bins='" + bins + '\'' +
//                ", partCostDollars=" + partCostDollars +
//                ", coreCostDollars=" + coreCostDollars +
//                ", totalExtendedCostDollars=" + totalExtendedCostDollars +
//                ", totalExcessStockCost=" + totalExcessStockCost +
//                ", sourceCode=" + sourceCode +
//                ", manuallyControlled='" + manuallyControlled + '\'' +
//                ", manufacturer='" + manufacturer + '\'' +
//                ", manufacturerControlCode='" + manufacturerControlCode + '\'' +
//                ", groupNumber='" + groupNumber + '\'' +
//                ", partsClassificationCode='" + partsClassificationCode + '\'' +
//                ", monthNoSale=" + monthNoSale +
//                ", monthNoReceipt=" + monthNoReceipt +
//                ", twelveMonthSaleQty=" + twelveMonthSaleQty +
//                ", jan=" + jan +
//                ", feb=" + feb +
//                ", mar=" + mar +
//                ", apr=" + apr +
//                ", may=" + may +
//                ", jun=" + jun +
//                ", jul=" + jul +
//                ", aug=" + aug +
//                ", sep=" + sep +
//                ", oct=" + oct +
//                ", nov=" + nov +
//                ", dec=" + dec +
//                ", manualOrder='" + manualOrder + '\'' +
//                ", createdDate=" + createdDate +
//                ", lastTransactionTime=" + lastTransactionTime +
//                ", lastSaleDate=" + lastSaleDate +
//                ", lastReceiptDate=" + lastReceiptDate +
                '}';
    }
}
