/**
 * This class represents a row in the received CDK Excel file
 * @author Julia Betzig +JMJ+
 * @version 2022-02-15
 */
package com.admi.data.dto;

import java.time.LocalDate;

public class CdkDto {

    private String partNo;
    private String description;
    private Long costCents;
    private String bin;
    private String source;
    private Long quantityOnHand;
    private Long quantityOnOrder;
    private String status;
    private Long mns;
    private Long mnr;
    private LocalDate saleDate;
    private LocalDate rDate;
    private LocalDate entry;
    private Long jan;
    private Long feb;
    private Long mar;
    private Long apr;
    private Long may;
    private Long jun;
    private Long jul;
    private Long aug;
    private Long sep;
    private Long oct;
    private Long nov;
    private Long dec;
    private Long yrsl;

    public CdkDto() {}

    /**
     * Returns the argument string, having removed any non-alphanumeric characters.
     * If the input is null, returns an empty string.
     * @param string An arbitrary string
     * @return A string containing only a-z, A-Z, and 0-9
     */
    private String makeAlphanumeric(String string){
        if (string != null) {
            return string.replaceAll("[^a-zA-Z0-9]", "");
        } else {
            return "";
        }
    }

    /**
     * Makes the given part number alphanumeric.
     * If given a null value, sets the part number as an empty string.
     * @param partNo
     */
    public void setPartNo(String partNo) {
        this.partNo = makeAlphanumeric(partNo);
    }

    public String getPartNo() {
        return partNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCostCents() {
        return costCents;
    }

//    public void setCostCents(Long costCents) {
//        this.costCents = costCents;
//    }

    /**
     * Since costCents is a long value only stored in cents, this method converts a double into the needed Long cents.
     * @param cost The cost of the part in decimal format (e.g. 2.45). Sets costCents to 245
     */
    public void setCostCents(double cost){
        this.costCents = Math.round(cost * 100);
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

    public Long getQuantityOnHand() {
        return quantityOnHand;
    }

    /**
     * Receive as a String, since the value is sometimes a non-number.
     * In this case, sets to 0.
     * @param oh the String value received from the Excel cell. If non-number, sets as 0
     */
    public void setQuantityOnHand(String oh) {
        this.quantityOnHand = getFirstMonthsData(oh);
    }

    public Long getQuantityOnOrder() {
        return quantityOnOrder;
    }

    /**
     * Receive as a String, since not always a clean number format.
     * @param oo the String value received from the Excel cell. Parsed into a long.
     */
    public void setQuantityOnOrder(String oo) {
        this.quantityOnOrder = getFirstMonthsData(oo);
    }

    /**
     * Takes a string of the format "0 0 0 ..." and returns the first number before a space.
     * @param multiMonthString A string of the format "0 0", "0 0 0", etc.
     * @return The first number in the argument, as a Long value. If unable to parse long, returns a 0
     */
    public Long getFirstMonthsData(String multiMonthString){
        try{
            return Long.parseLong(multiMonthString.split(" ")[0]);
        } catch(NumberFormatException nfe){
            System.out.println("Unable to parse string " + multiMonthString + " into a Long value");
            nfe.printStackTrace();
            return 0L;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMns() {
        return mns;
    }

    public void setMns(Long mns) {
        this.mns = mns;
    }

    public Long getMnr() {
        return mnr;
    }

    public void setMnr(Long mnr) {
        this.mnr = mnr;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public LocalDate getrDate() {
        return rDate;
    }

    public void setrDate(LocalDate rDate) {
        this.rDate = rDate;
    }

    public LocalDate getEntry() {
        return entry;
    }

    public void setEntry(LocalDate entry) {
        this.entry = entry;
    }

    public Long getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = getFirstMonthsData(jan);
    }

    public Long getFeb() {
        return feb;
    }

    public void setFeb(String feb) {
        this.feb = getFirstMonthsData(feb);
    }

    public Long getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = getFirstMonthsData(mar);
    }

    public Long getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = getFirstMonthsData(apr);
    }

    public Long getMay() {
        return may;
    }

    public void setMay(String may) {
        this.may = getFirstMonthsData(may);
    }

    public Long getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = getFirstMonthsData(jun);
    }

    public Long getJul() {
        return jul;
    }

    public void setJul(String jul) {
        this.jul = getFirstMonthsData(jul);
    }

    public Long getAug() {
        return aug;
    }

    public void setAug(String aug) {
        this.aug = getFirstMonthsData(aug);
    }

    public Long getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = getFirstMonthsData(sep);
    }

    public Long getOct() {
        return oct;
    }

    public void setOct(String oct) {
        this.oct = getFirstMonthsData(oct);
    }

    public Long getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = getFirstMonthsData(nov);
    }

    public Long getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = getFirstMonthsData(dec);
    }

    public Long getYrsl() {
        return yrsl;
    }

    public void setYrsl(Long yrsl) {
        this.yrsl = yrsl;
    }

    @Override
    public String toString() {
        return "CdkDto{" +
                "partNo='" + partNo + '\'' +
                ", description='" + description + '\'' +
                ", costCents=" + costCents +
                ", bin='" + bin + '\'' +
                ", source='" + source + '\'' +
                ", quantityOnHand=" + quantityOnHand +
                ", quantityOnOrder=" + quantityOnOrder +
                ", status='" + status + '\'' +
                ", mns=" + mns +
                ", mnr=" + mnr +
                ", saleDate=" + saleDate +
                ", rDate=" + rDate +
                ", entry=" + entry +
                ", jan='" + jan + '\'' +
                ", feb='" + feb + '\'' +
                ", mar='" + mar + '\'' +
                ", apr='" + apr + '\'' +
                ", may='" + may + '\'' +
                ", jun='" + jun + '\'' +
                ", jul='" + jul + '\'' +
                ", aug='" + aug + '\'' +
                ", sep='" + sep + '\'' +
                ", oct='" + oct + '\'' +
                ", nov='" + nov + '\'' +
                ", dec='" + dec + '\'' +
                ", yrsl=" + yrsl +
                '}';
    }
}
