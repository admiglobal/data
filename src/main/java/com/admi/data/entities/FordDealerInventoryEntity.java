package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FORD_DEALER_INVENTORY", schema = "ADMI")
public class FordDealerInventoryEntity {
    private Long dealerId;
    private String partno;
    private Long cents;
    private Long qoh;
    private String description;
    private String status;
    private LocalDate lastSale;
    private LocalDate lastReceipt;
    private String bin;
    private String source;
    private Boolean mfgControlled;
    private LocalDate dataDate;
    private String admiStatus;
    private String manufacturer;
    private Long qoo;
    private Long twelveMonthSales;
    private LocalDate entryDate;
    private String paCode;

    @Basic
    @Column(name = "DEALER_ID")
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Id
    @Column(name = "PARTNO")
    public String getPartno() {
        return partno;
    }

    public void setPartno(String partno) {
        this.partno = partno;
    }

    @Basic
    @Column(name = "CENTS")
    public Long getCents() {
        return cents;
    }

    public void setCents(Long cents) {
        this.cents = cents;
    }

    @Basic
    @Column(name = "QOH")
    public Long getQoh() {
        return qoh;
    }

    public void setQoh(Long qoh) {
        this.qoh = qoh;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "LAST_SALE")
    public LocalDate getLastSale() {
        return lastSale;
    }

    public void setLastSale(LocalDate lastSale) {
        this.lastSale = lastSale;
    }

    @Basic
    @Column(name = "LAST_RECEIPT")
    public LocalDate getLastReceipt() {
        return lastReceipt;
    }

    public void setLastReceipt(LocalDate lastReceipt) {
        this.lastReceipt = lastReceipt;
    }

    @Basic
    @Column(name = "BIN")
    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    @Basic
    @Column(name = "SOURCE")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "MFG_CONTROLLED")
    public Boolean getMfgControlled() {
        return mfgControlled;
    }

    public void setMfgControlled(Boolean mfgControlled) {
        this.mfgControlled = mfgControlled;
    }

    @Basic
    @Column(name = "DATA_DATE")
    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    @Basic
    @Column(name = "ADMI_STATUS")
    public String getAdmiStatus() {
        return admiStatus;
    }

    public void setAdmiStatus(String admiStatus) {
        this.admiStatus = admiStatus;
    }

    @Basic
    @Column(name = "MANUFACTURER")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Basic
    @Column(name = "QOO")
    public Long getQoo() {
        return qoo;
    }

    public void setQoo(Long qoo) {
        this.qoo = qoo;
    }

    @Basic
    @Column(name = "TWELVE_MONTH_SALES")
    public Long getTwelveMonthSales() {
        return twelveMonthSales;
    }

    public void setTwelveMonthSales(Long twelveMonthSales) {
        this.twelveMonthSales = twelveMonthSales;
    }

    @Basic
    @Column(name = "ENTRY_DATE")
    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Basic
    @Column(name = "PA_CODE")
    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FordDealerInventoryEntity that = (FordDealerInventoryEntity) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (partno != null ? !partno.equals(that.partno) : that.partno != null) return false;
        if (cents != null ? !cents.equals(that.cents) : that.cents != null) return false;
        if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (lastSale != null ? !lastSale.equals(that.lastSale) : that.lastSale != null) return false;
        if (lastReceipt != null ? !lastReceipt.equals(that.lastReceipt) : that.lastReceipt != null) return false;
        if (bin != null ? !bin.equals(that.bin) : that.bin != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (mfgControlled != null ? !mfgControlled.equals(that.mfgControlled) : that.mfgControlled != null)
            return false;
        if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
        if (admiStatus != null ? !admiStatus.equals(that.admiStatus) : that.admiStatus != null) return false;
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) return false;
        if (qoo != null ? !qoo.equals(that.qoo) : that.qoo != null) return false;
        if (twelveMonthSales != null ? !twelveMonthSales.equals(that.twelveMonthSales) : that.twelveMonthSales != null)
            return false;
        if (entryDate != null ? !entryDate.equals(that.entryDate) : that.entryDate != null) return false;
        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (partno != null ? partno.hashCode() : 0);
        result = 31 * result + (cents != null ? cents.hashCode() : 0);
        result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastSale != null ? lastSale.hashCode() : 0);
        result = 31 * result + (lastReceipt != null ? lastReceipt.hashCode() : 0);
        result = 31 * result + (bin != null ? bin.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (mfgControlled != null ? mfgControlled.hashCode() : 0);
        result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
        result = 31 * result + (admiStatus != null ? admiStatus.hashCode() : 0);
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (qoo != null ? qoo.hashCode() : 0);
        result = 31 * result + (twelveMonthSales != null ? twelveMonthSales.hashCode() : 0);
        result = 31 * result + (entryDate != null ? entryDate.hashCode() : 0);
        result = 31 * result + (paCode != null ? paCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FordDealerInventoryEntity{" +
                "dealerId=" + dealerId +
                ", partno='" + partno + '\'' +
                ", cents=" + cents +
                ", qoh=" + qoh +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", lastSale=" + lastSale +
                ", lastReceipt=" + lastReceipt +
                ", bin='" + bin + '\'' +
                ", source='" + source + '\'' +
                ", mfgControlled=" + mfgControlled +
                ", dataDate=" + dataDate +
                ", admiStatus='" + admiStatus + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", qoo=" + qoo +
                ", twelveMonthSales=" + twelveMonthSales +
                ", entryDate=" + entryDate +
                ", paCode='" + paCode + '\'' +
                '}';
    }
}
