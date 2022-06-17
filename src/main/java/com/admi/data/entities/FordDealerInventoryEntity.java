package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FORD_DEALER_INVENTORY", schema = "ADMI")
public class FordDealerInventoryEntity {
	private String paCode;
    private String partno;
    private Integer cents;
	private Integer qoh;
    private String status;
    private LocalDate lastSale;
    private LocalDate lastReceipt;
    private Boolean mfgControlled;
    private LocalDate dataDate;
    private String admiStatus;
    private Integer qoo;

    @Basic
    @Column(name = "PA_CODE", nullable = true, length = 6)
    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    @Basic
    @Id
    @Column(name = "PARTNO", nullable = false, length = 30)
    public String getPartno() {
        return partno;
    }

    public void setPartno(String partno) {
        this.partno = partno;
    }

    @Basic
    @Column(name = "CENTS", nullable = true, precision = 0)
    public Integer getCents() {
        return cents;
    }

	public void setCents(Integer cents) {
		this.cents = cents;
	}

    @Basic
    @Column(name = "QOH", nullable = true, precision = 0)
    public Integer getQoh() {
        return qoh;
    }

	public void setQoh(Integer qoh) {
		this.qoh = qoh;
	}

    @Basic
    @Column(name = "STATUS", nullable = true, length = 20)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "LAST_SALE", nullable = true)
    public LocalDate getLastSale() {
        return lastSale;
    }

    public void setLastSale(LocalDate lastSale) {
        this.lastSale = lastSale;
    }

    @Basic
    @Column(name = "LAST_RECEIPT", nullable = true)
    public LocalDate getLastReceipt() {
        return lastReceipt;
    }

    public void setLastReceipt(LocalDate lastReceipt) {
        this.lastReceipt = lastReceipt;
    }

    @Basic
    @Column(name = "MFG_CONTROLLED", nullable = true, precision = 0)
    public Boolean getMfgControlled() {
        return mfgControlled;
    }

    public void setMfgControlled(Boolean mfgControlled) {
        this.mfgControlled = mfgControlled;
    }

    @Basic
    @Column(name = "DATA_DATE", nullable = true)
    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    @Basic
    @Column(name = "ADMI_STATUS", nullable = true, length = 1)
    public String getAdmiStatus() {
        return admiStatus;
    }

    public void setAdmiStatus(String admiStatus) {
        this.admiStatus = admiStatus;
    }

    @Basic
    @Column(name = "QOO", nullable = true, precision = 0)
    public Integer getQoo() {
        return qoo;
    }

	public void setQoo(Integer qoo) {
		this.qoo = qoo;
	}

    @Transient
    public LocalDate getLastSaleOrReceipt() {
        if (lastReceipt != null) {
            return lastReceipt;
        } else if (lastSale != null) {
            return lastSale;
        } else {
            return LocalDate.of(2000,1,1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FordDealerInventoryEntity that = (FordDealerInventoryEntity) o;
        return Objects.equals(paCode, that.paCode) && Objects.equals(partno, that.partno) && Objects.equals(cents, that.cents) && Objects.equals(qoh, that.qoh) && Objects.equals(status, that.status) && Objects.equals(lastSale, that.lastSale) && Objects.equals(lastReceipt, that.lastReceipt) && Objects.equals(mfgControlled, that.mfgControlled) && Objects.equals(dataDate, that.dataDate) && Objects.equals(admiStatus, that.admiStatus) && Objects.equals(qoo, that.qoo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paCode, partno, cents, qoh, status, lastSale, lastReceipt, mfgControlled, dataDate, admiStatus, qoo);
    }

    @Override
    public String toString() {
        return "FordDealerInventoryEntity{" +
                "paCode='" + paCode + '\'' +
                ", partno='" + partno + '\'' +
                ", cents=" + cents +
                ", qoh=" + qoh +
                ", status='" + status + '\'' +
                ", lastSale=" + lastSale +
                ", lastReceipt=" + lastReceipt +
                ", mfgControlled=" + mfgControlled +
                ", dataDate=" + dataDate +
                ", admiStatus='" + admiStatus + '\'' +
                ", qoo=" + qoo +
                '}';
    }
}
