package com.admi.data.entities;

import com.admi.data.entities.keys.CpcPartsOnHandEntityPK;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CPC_PARTS_ON_HAND", schema = "ADMI")
@IdClass(CpcPartsOnHandEntityPK.class)
public class CpcPartsOnHandEntity {
    private Long dealerId;
    private LocalDate dataDate;
    private String partNo;
    private Integer qoh;

    public CpcPartsOnHandEntity() {}

    public CpcPartsOnHandEntity(Long dealerId, LocalDate dataDate, String partNo, Integer qoh) {
        this.dealerId = dealerId;
        this.dataDate = dataDate;
        this.partNo = partNo;
        this.qoh = qoh;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DEALER_ID", nullable = false)
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Basic
    @Column(name = "DATA_DATE")
    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PART_NO", nullable = false, length = 30)
    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    @Basic
    @Column(name = "QOH")
    public Integer getQoh() {
        return qoh;
    }

    public void setQoh(Integer qoh) {
        this.qoh = qoh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcPartsOnHandEntity that = (CpcPartsOnHandEntity) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
        return result;
    }
}
