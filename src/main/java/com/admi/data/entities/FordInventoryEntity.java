package com.admi.data.entities;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "FORD_DEALER_INVENTORY", schema = "ADMI")
public class FordInventoryEntity {
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

    private DealerMasterEntity dealer;
    private FordPtEntity ptEntity;
    private FordRimHistoryEntity rimHistory;


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

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinFormula(value = "SELECT dm.pa_code \n" +
//            "FROM DEALER_MASTER dm \n" +
//            "WHERE PA_CODE = PA_CODE \n" +
//            "  AND PRIMARY_MANUFACTURER_ID = 1 \n" +
//            "  AND TERMINATION_DATE is null")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(column = @JoinColumn(name = "PA_CODE", referencedColumnName = "PA_CODE", insertable = false, updatable = false)),
            @JoinColumnOrFormula(
                    formula = @JoinFormula(
                            value = "SELECT PA_CODE \n" +
                                    "FROM DEALER_MASTER \n" +
                                    "where PA_CODE = PA_CODE \n" +
                                    "  and PRIMARY_MANUFACTURER_ID = 1 \n" +
                                    "  and TERMINATION_DATE is null",
                            referencedColumnName = "PA_CODE")
            )
    })
    public DealerMasterEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerMasterEntity dealer) {
        this.dealer = dealer;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumnsOrFormulas({
            @JoinColumnOrFormula(column = @JoinColumn(name = "PARTNO", referencedColumnName = "PARTNO", insertable = false, updatable = false)),
            @JoinColumnOrFormula(
                    formula = @JoinFormula(
                            value = "SELECT PARTNO \n" +
                                    "FROM FORD_PT \n" +
                                    "WHERE PARTNO = PARTNO \n" +
                                    "  AND TAPE = 'FORD_US'",
                            referencedColumnName = "PARTNO")
            )
    })
    public FordPtEntity getPtEntity() {
        return ptEntity;
    }

    public void setPtEntity(FordPtEntity ptEntity) {
        this.ptEntity = ptEntity;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "PA_CODE", referencedColumnName = "PA_CODE", insertable = false, updatable = false),
            @JoinColumn(name = "PARTNO", referencedColumnName = "PART_NUMBER", insertable = false, updatable = false)
    })
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumnsOrFormulas({
//            @JoinColumnOrFormula(column = @JoinColumn(name = "PA_CODE", referencedColumnName = "PA_CODE", insertable = false, updatable = false)),
//            @JoinColumnOrFormula(column = @JoinColumn(name = "PARTNO", referencedColumnName = "PART_NUMBER", insertable = false, updatable = false)),
//            @JoinColumnOrFormula(
//                    formula = @JoinFormula(
//                            value = "SELECT PART_NUMBER \n" +
//                                    "FROM FORD_DEALER_RIM_HISTORY \n" +
//                                    "WHERE PA_CODE = PA_CODE \n" +
//                                    "  and PART_NUMBER = PARTNO",
//                            referencedColumnName = "PART_NUMBER")
//            )
//    })
    public FordRimHistoryEntity getRimHistory() {
        return rimHistory;
    }

    public void setRimHistory(FordRimHistoryEntity rimHistory) {
        this.rimHistory = rimHistory;
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
        FordInventoryEntity that = (FordInventoryEntity) o;
        return Objects.equals(paCode, that.paCode) && Objects.equals(partno, that.partno) && Objects.equals(cents, that.cents) && Objects.equals(qoh, that.qoh) && Objects.equals(status, that.status) && Objects.equals(lastSale, that.lastSale) && Objects.equals(lastReceipt, that.lastReceipt) && Objects.equals(mfgControlled, that.mfgControlled) && Objects.equals(dataDate, that.dataDate) && Objects.equals(admiStatus, that.admiStatus) && Objects.equals(qoo, that.qoo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paCode, partno, cents, qoh, status, lastSale, lastReceipt, mfgControlled, dataDate, admiStatus, qoo);
    }


    @Override
    public String toString() {
        return "FordInventoryEntity{" +
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
                ", dealer=" + dealer.toString() +
                ", ptEntity=" + ptEntity.toString() +
                ", rimHistory=" + rimHistory.toString() +
                '}';
    }
}
