/**
 * This class represents a row in the received CDK Excel file
 * @author Julia Betzig +JMJ+
 * @version 2022-02-15
 */
package com.admi.data.dto;

import com.admi.data.entities.AipInventoryEntity;
import com.admi.data.services.CdkImportService;
import com.sun.istack.NotNull;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Objects;

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

    public AipInventoryEntity toAipInventory(@NotNull Long dealerId, @NotNull LocalDate date) {

        AipInventoryEntity inv = new AipInventoryEntity();

        //can't have null part numbers: make stand-in of format "XXXX-[hashCode]"
        String partNo = this.getPartNo();
        if(partNo == null || partNo.equals("")){
            partNo = "XXXX-" + this.hashCode();
        }

        inv.setDealerId(dealerId);
        inv.setPartNo(partNo);
        inv.setCents(this.costCents == null ? null : Math.toIntExact(this.costCents));
        Integer qoh = this.quantityOnHand == null ? null : Math.toIntExact(this.quantityOnHand);
        inv.setQoh(qoh);
        inv.setDescription(this.description);
        inv.setStatus(this.getStatus());
        inv.setLastSale(this.getLastSaleDate());
        inv.setLastReceipt(this.getLastReceiptDate());
        inv.setBin(this.bin);
        inv.setSource(this.source);
        //mfgControlled
        inv.setDataDate(date);
        inv.setAdmiStatus(CdkImportService.getAdmiStatus(this.getStatus()));
        //manufacturer
        inv.setQoo(this.quantityOnOrder == null ? null : Math.toIntExact(this.quantityOnOrder));
        inv.setTwelveMonthSales(this.yrsl == null ? null : Math.toIntExact(this.yrsl));
        inv.setEntryDate(this.getEntry());

        if(!partNo.equals("XXXX-961614017")){
            System.out.println("Qoh set to: " + qoh + ". Qoh value is now: " + inv.getQoh());
            System.out.println("Importing CdkDto: QOH=" + this.quantityOnHand + "; DTO: " + this);
//            int qoh = this.quantityOnHand == null ? null : Math.toIntExact(this.quantityOnHand);
            System.out.println("QOH translation: " + qoh);
            System.out.println("^ Imported to: QOH=" + inv.getQoh() + "; AipInventoryEntity: " + inv);
        }

        return inv;
    }

    private LocalDate getDefaultDateIfNull(LocalDate date) {
        if (date == null) {
            return LocalDate.of(2000,1,1);
        } else {
            return date;
        }
    }

    public boolean isBlankRow(){
        return partNo == null
                && description == null
                && costCents == null
                && bin == null
                && source == null
                && quantityOnHand == null
                && quantityOnOrder == null
                && status == null
                && mns == null
                && mnr == null
                && saleDate == null
                && rDate == null
                && entry == null
                && jan == null
                && feb == null
                && mar == null
                && apr == null
                && may == null
                && jun == null
                && jul == null
                && aug == null
                && sep == null
                && oct == null
                && nov == null
                && dec == null
                && yrsl == null;
    }

    /**
     * Returns the argument string, having removed any non-alphanumeric characters.
     * @param string An arbitrary string
     * @return A string containing only a-z, A-Z, and 0-9
     */
    private String makeAlphanumeric(String string){
        if (string != null) {
            return string.replaceAll("[^a-zA-Z0-9]", "");
        } else {
            return null;
        }
    }

    /**
     * Makes the given part number alphanumeric, if not null.
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

    /**
     * Since costCents is a long value only stored in cents, this method converts a double into the needed Long cents.
     * @param cost The cost of the part in decimal format (e.g. 2.45). Sets costCents to 245
     */
    public void setCostCents(Double cost){
        if(cost != null){
            this.costCents = Math.round(cost * 100);
        }
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
        if(oh == null){
            return;
        }

        //if it contains something besides numbers and spaces, set as 0 (likely MEMO or DEL)
        if(oh.matches("[0-9 ]*?")){
            this.quantityOnHand = getFirstMonthsData(oh);
        } else{
            this.quantityOnHand = 0L;
        }
    }

    public Long getQuantityOnOrder() {
        return quantityOnOrder;
    }

    /**
     * Receive as a String, since not always a clean number format.
     * @param oo the String value received from the Excel cell. Parsed into a long.
     */
    public void setQuantityOnOrder(String oo) {
        if(oo != null){
            this.quantityOnOrder = getFirstMonthsData(oo);
        }
    }

    /**
     * Takes a string of the format "0 0 0 ..." and returns the first number before a space.
     * This format represents previous months' data, separated by spaces.
     * Rounds non-integers.
     * @param multiMonthString A string of the format "0 0", "0 0 0", etc.
     * @return The first number in the argument, as a Long value. If unable to parse long, returns a 0
     */
    public Long getFirstMonthsData(String multiMonthString){
        if(multiMonthString == null){
            return null;
        }

        try{
            double d = Double.parseDouble(multiMonthString.split(" ")[0]);
            return Math.round(d);
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

    /**
     * Returns the Last Receipt Date appropriate for the AIP_INVENTORY table based on rDate.
     * If rDate is null, uses mnr (months no receipt) and translates it into a LocalDate.
     * @return the last receipt date for this part
     */
    public LocalDate getLastReceiptDate(){
        if(this.rDate != null){
            return this.rDate;
        } else{
            LocalDate now = LocalDate.now();
            if(mnr == null){
                return now;
            } else{
                LocalDate then = now.minusMonths(this.mnr);
                //assume worst-case scenario of 1st of the month
                return LocalDate.of(then.getYear(), then.getMonth(), 1);
            }
        }
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

    /**
     * Returns the Last Sale Date appropriate for the AIP_INVENTORY table based on saleDate.
     * If saleDate is null, uses mns (months no sale) and translates it into a LocalDate.
     * @return the last sale date for this part
     */
    public LocalDate getLastSaleDate(){
        if(this.saleDate != null){
            return this.saleDate;
        } else{
            LocalDate now = LocalDate.now();
            if(mns == null){
                return now;
            } else{
                LocalDate then = now.minusMonths(this.mns);
                //assume worst-case scenario of 1st of the month
                return LocalDate.of(then.getYear(), then.getMonth(), 1);
            }
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CdkDto cdkDto = (CdkDto) o;
        return Objects.equals(partNo, cdkDto.partNo) && Objects.equals(description, cdkDto.description) && Objects.equals(costCents, cdkDto.costCents) && Objects.equals(bin, cdkDto.bin) && Objects.equals(source, cdkDto.source) && Objects.equals(quantityOnHand, cdkDto.quantityOnHand) && Objects.equals(quantityOnOrder, cdkDto.quantityOnOrder) && Objects.equals(status, cdkDto.status) && Objects.equals(mns, cdkDto.mns) && Objects.equals(mnr, cdkDto.mnr) && Objects.equals(saleDate, cdkDto.saleDate) && Objects.equals(rDate, cdkDto.rDate) && Objects.equals(entry, cdkDto.entry) && Objects.equals(jan, cdkDto.jan) && Objects.equals(feb, cdkDto.feb) && Objects.equals(mar, cdkDto.mar) && Objects.equals(apr, cdkDto.apr) && Objects.equals(may, cdkDto.may) && Objects.equals(jun, cdkDto.jun) && Objects.equals(jul, cdkDto.jul) && Objects.equals(aug, cdkDto.aug) && Objects.equals(sep, cdkDto.sep) && Objects.equals(oct, cdkDto.oct) && Objects.equals(nov, cdkDto.nov) && Objects.equals(dec, cdkDto.dec) && Objects.equals(yrsl, cdkDto.yrsl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNo, description, costCents, bin, source, quantityOnHand, quantityOnOrder, status, mns, mnr, saleDate, rDate, entry, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, yrsl);
    }
}
