package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FORD_DEALER_INVENTORY", schema = "ADMI")
public class FordDealerInventoryEntity {
    @Basic
    @Column(name = "DEALER_ID")
    private Long dealerId;
    @Id
    @Column(name = "PARTNO")
    private String partno;
    @Basic
    @Column(name = "CENTS")
    private Long cents;
    @Basic
    @Column(name = "QOH")
    private Long qoh;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic
    @Column(name = "STATUS")
    private String status;
    @Basic
    @Column(name = "LAST_SALE")
    private LocalDate lastSale;
    @Basic
    @Column(name = "LAST_RECEIPT")
    private LocalDate lastReceipt;
    @Basic
    @Column(name = "BIN")
    private String bin;
    @Basic
    @Column(name = "SOURCE")
    private String source;
    @Basic
    @Column(name = "MFG_CONTROLLED")
    private Boolean mfgControlled;
    @Basic
    @Column(name = "DATA_DATE")
    private LocalDate dataDate;
    @Basic
    @Column(name = "ADMI_STATUS")
    private String admiStatus;
    @Basic
    @Column(name = "MANUFACTURER")
    private String manufacturer;
    @Basic
    @Column(name = "QOO")
    private Long qoo;
    @Basic
    @Column(name = "TWELVE_MONTH_SALES")
    private Long twelveMonthSales;
    @Basic
    @Column(name = "ENTRY_DATE")
    private LocalDate entryDate;
    @Basic
    @Column(name = "PA_CODE")
    private String paCode;

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getPartno() {
        return partno;
    }

    public void setPartno(String partno) {
        this.partno = partno;
    }

    public Long getCents() {
        return cents;
    }

    public void setCents(Long cents) {
        this.cents = cents;
    }

    public Long getQoh() {
        return qoh;
    }

    public void setQoh(Long qoh) {
        this.qoh = qoh;
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

    public LocalDate getLastSale() {
        return lastSale;
    }

    public void setLastSale(LocalDate lastSale) {
        this.lastSale = lastSale;
    }

    public LocalDate getLastReceipt() {
        return lastReceipt;
    }

    public void setLastReceipt(LocalDate lastReceipt) {
        this.lastReceipt = lastReceipt;
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

    public Boolean getMfgControlled() {
        return mfgControlled;
    }

    public void setMfgControlled(Boolean mfgControlled) {
        this.mfgControlled = mfgControlled;
    }

    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    public String getAdmiStatus() {
        return admiStatus;
    }

    public void setAdmiStatus(String admiStatus) {
        this.admiStatus = admiStatus;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Long getQoo() {
        return qoo;
    }

    public void setQoo(Long qoo) {
        this.qoo = qoo;
    }

    public Long getTwelveMonthSales() {
        return twelveMonthSales;
    }

    public void setTwelveMonthSales(Long twelveMonthSales) {
        this.twelveMonthSales = twelveMonthSales;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

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
}
