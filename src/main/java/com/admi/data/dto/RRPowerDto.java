/**
 * This class represents a row in the received CDK Excel file
 * @author Julia Betzig +JMJ+
 * @version 2022-02-15
 */
package com.admi.data.dto;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.services.RRPowerImportService;
import com.admi.data.services.SpreadsheetService;
import com.sun.istack.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public class RRPowerDto {

    private Long oct;
    private Long nov;
    private Long dec;
    private Long jan;
    private Long feb;
    private Long mar;
    private Long twelveMonthSalesYr1;
    private Long net;
    private Long quantityOnHand;
    private Double onHandValue;
    private Long backorder;
    private Long outstanding;
    private Long minimum;
    private Long maximum;
    private String source;
    private String partNumber;
    private String group;
    private String bin;
    private LocalDate entry;
    private LocalDate lastSaleDate;
    private String overallStatus;
    private LocalDate receiptDate;
    private String rimState;
    private Long apr;
    private Long may;
    private Long marYr2;
    private Long sep;
    private String description;
    private Long aug;
    private Long jul;
    private Long jun;


    public RRPowerDto() {}

    public AipInventoryEntity toAipInventory(@NotNull Long dealerId, @NotNull LocalDate date) {

        AipInventoryEntity inv = new AipInventoryEntity();

        inv.setDealerId(dealerId);
        inv.setPartNo(SpreadsheetService.getModifiedPartNumber(partNumber, this.hashCode()));
        inv.setCents((net == null) ? null : Math.toIntExact(net));
        inv.setQoh(quantityOnHand == null ? null : Math.toIntExact(this.quantityOnHand));
        inv.setDescription(this.description);
        inv.setStatus(this.overallStatus);
        inv.setAdmiStatus(this.getAdmiStatus());
        inv.setLastSale(this.lastSaleDate);
        inv.setLastReceipt(this.receiptDate);
        inv.setBin(this.bin);
        inv.setSource(this.source);
//        //mfgControlled //TODO - Check if rimState column in DTO gives us mfgControlled info for AipInventoryEntity
        inv.setDataDate(date);
//        //manufacturer
        inv.setQoo(this.backorder == null ? null : Math.toIntExact(backorder));
        inv.setTwelveMonthSales(twelveMonthSalesYr1 == null ? null : Math.toIntExact(twelveMonthSalesYr1));
        inv.setEntryDate(entry);

        return inv;
    }

    //TODO - double check what the ZEROG status and blanks should translate to in ADMI terms
    private String getAdmiStatus() {
        if(overallStatus == null){ return "N"; }

        switch(overallStatus){
            case "STOCK":
                return "S";
            case "N-STK":
            case "ZEROG":
            default:
                return "N";
        }
    }

    private LocalDate getDefaultDateIfNull(LocalDate date) {
        if (date == null) {
            return LocalDate.of(2000,1,1);
        } else {
            return date;
        }
    }

    public boolean isBlankRow(){
        return     oct == null
                && nov == null
                && dec == null
                && jan == null
                && feb == null
                && mar == null
                && twelveMonthSalesYr1 == null
                && net == null
                && quantityOnHand == null
                && onHandValue == null
                && backorder == null
                && outstanding == null
                && minimum == null
                && maximum == null
                && source == null
                && partNumber == null
                && group == null
                && entry == null
                && lastSaleDate == null
                && overallStatus == null
                && receiptDate == null
                && rimState == null
                && apr == null
                && may == null
                && marYr2 == null
                && sep == null
                && description == null
                && aug == null
                && jul == null
                && jun == null;
    }

    public Long getOct() {
        return oct;
    }

    public void setOct(Long oct) {
        this.oct = oct;
    }

    public Long getNov() {
        return nov;
    }

    public void setNov(Long nov) {
        this.nov = nov;
    }

    public Long getDec() {
        return dec;
    }

    public void setDec(Long dec) {
        this.dec = dec;
    }

    public Long getJan() {
        return jan;
    }

    public void setJan(Long jan) {
        this.jan = jan;
    }

    public Long getFeb() {
        return feb;
    }

    public void setFeb(Long feb) {
        this.feb = feb;
    }

    public Long getMar() {
        return mar;
    }

    public void setMar(Long mar) {
        this.mar = mar;
    }

    public Long getTwelveMonthSalesYr1() {
        return twelveMonthSalesYr1;
    }

    public void setTwelveMonthSalesYr1(Long twelveMonthSalesYr1) {
        this.twelveMonthSalesYr1 = twelveMonthSalesYr1;
    }

    public Long getNet() {
        return net;
    }

    public void setNet(Long net) {
        this.net = net;
    }

    public Long getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Long quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Double getOnHandValue() {
        return onHandValue;
    }

    public void setOnHandValue(Double onHandValue) {
        this.onHandValue = onHandValue;
    }

    public Long getBackorder() {
        return backorder;
    }

    public void setBackorder(Long backorder) {
        this.backorder = backorder;
    }

    public Long getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Long outstanding) {
        this.outstanding = outstanding;
    }

    public Long getMinimum() {
        return minimum;
    }

    public void setMinimum(Long minimum) {
        this.minimum = minimum;
    }

    public Long getMaximum() {
        return maximum;
    }

    public void setMaximum(Long maximum) {
        this.maximum = maximum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public LocalDate getEntry() {
        return entry;
    }

    public void setEntry(LocalDate entry) {
        this.entry = entry;
    }

    public LocalDate getLastSaleDate() {
        return lastSaleDate;
    }

    public void setLastSaleDate(LocalDate lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }

    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getRimState() {
        return rimState;
    }

    public void setRimState(String rimState) {
        this.rimState = rimState;
    }

    public Long getApr() {
        return apr;
    }

    public void setApr(Long apr) {
        this.apr = apr;
    }

    public Long getMay() {
        return may;
    }

    public void setMay(Long may) {
        this.may = may;
    }

    public Long getMarYr2() {
        return marYr2;
    }

    public void setMarYr2(Long marYr2) {
        this.marYr2 = marYr2;
    }

    public Long getSep() {
        return sep;
    }

    public void setSep(Long sep) {
        this.sep = sep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAug() {
        return aug;
    }

    public void setAug(Long aug) {
        this.aug = aug;
    }

    public Long getJul() {
        return jul;
    }

    public void setJul(Long jul) {
        this.jul = jul;
    }

    public Long getJun() {
        return jun;
    }

    public void setJun(Long jun) {
        this.jun = jun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RRPowerDto that = (RRPowerDto) o;
        return Objects.equals(oct, that.oct) && Objects.equals(nov, that.nov) && Objects.equals(dec, that.dec) && Objects.equals(jan, that.jan) && Objects.equals(feb, that.feb) && Objects.equals(mar, that.mar) && Objects.equals(twelveMonthSalesYr1, that.twelveMonthSalesYr1) && Objects.equals(net, that.net) && Objects.equals(quantityOnHand, that.quantityOnHand) && Objects.equals(onHandValue, that.onHandValue) && Objects.equals(backorder, that.backorder) && Objects.equals(outstanding, that.outstanding) && Objects.equals(minimum, that.minimum) && Objects.equals(maximum, that.maximum) && Objects.equals(source, that.source) && Objects.equals(partNumber, that.partNumber) && Objects.equals(group, that.group) && Objects.equals(bin, that.bin) && Objects.equals(entry, that.entry) && Objects.equals(lastSaleDate, that.lastSaleDate) && Objects.equals(overallStatus, that.overallStatus) && Objects.equals(receiptDate, that.receiptDate) && Objects.equals(rimState, that.rimState) && Objects.equals(apr, that.apr) && Objects.equals(may, that.may) && Objects.equals(marYr2, that.marYr2) && Objects.equals(sep, that.sep) && Objects.equals(description, that.description) && Objects.equals(aug, that.aug) && Objects.equals(jul, that.jul) && Objects.equals(jun, that.jun);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oct, nov, dec, jan, feb, mar, twelveMonthSalesYr1, net, quantityOnHand, onHandValue, backorder, outstanding, minimum, maximum, source, partNumber, group, bin, entry, lastSaleDate, overallStatus, receiptDate, rimState, apr, may, marYr2, sep, description, aug, jul, jun);
    }

    @Override
    public String toString() {
        return "RRPowerDto{" +
                "oct=" + oct +
                ", nov=" + nov +
                ", dec=" + dec +
                ", jan=" + jan +
                ", feb=" + feb +
                ", mar=" + mar +
                ", twelveMonthSalesYr1=" + twelveMonthSalesYr1 +
                ", net=" + net +
                ", quantityOnHand=" + quantityOnHand +
                ", onHandValue=" + onHandValue +
                ", backorder=" + backorder +
                ", outstanding=" + outstanding +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", source=" + source +
                ", partNumber='" + partNumber + '\'' +
                ", group='" + group + '\'' +
                ", bin='" + bin + '\'' +
                ", entry=" + entry +
                ", lastSaleDate=" + lastSaleDate +
                ", overallStatus='" + overallStatus + '\'' +
                ", receiptDate=" + receiptDate +
                ", rimState='" + rimState + '\'' +
                ", apr=" + apr +
                ", may=" + may +
                ", marYr2=" + marYr2 +
                ", sep=" + sep +
                ", description='" + description + '\'' +
                ", aug=" + aug +
                ", jul=" + jul +
                ", jun=" + jun +
                '}';
    }
}
