package com.admi.data.entities;

import com.admi.data.entities.keys.CpcKpiEntityPK;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CPC_KPI", schema = "ADMI")
@IdClass(CpcKpiEntityPK.class)
public class CpcKpiEntity {
    private Long dealerId;
    private LocalDate dataDate;
    private Short tierList;
    private Long totalCollisionSku;
    private Long totalNonCollisionSku;
    private Long totalCpcListSku;
    private BigDecimal totalCollisionInvestment;
    private BigDecimal totalNonCollisionInvestment;
    private BigDecimal totalCpcListInvestment;
    private BigDecimal cpcObjective;

    public CpcKpiEntity() {

    }

    public CpcKpiEntity(Long dealerId, LocalDate dataDate, Short tierList, Long totalCollisionSku, Long totalNonCollisionSku, Long totalCpcListSku, BigDecimal totalCollisionInvestment, BigDecimal totalNonCollisionInvestment, BigDecimal totalCpcListInvestment, BigDecimal cpcObjective) {
        this.dealerId = dealerId;
        this.dataDate = dataDate;
        this.tierList = tierList;
        this.totalCollisionSku = totalCollisionSku;
        this.totalNonCollisionSku = totalNonCollisionSku;
        this.totalCpcListSku = totalCpcListSku;
        this.totalCollisionInvestment = totalCollisionInvestment;
        this.totalNonCollisionInvestment = totalNonCollisionInvestment;
        this.totalCpcListInvestment = totalCpcListInvestment;
        this.cpcObjective = cpcObjective;
    }

    @Id
    @Column(name = "DEALER_ID", nullable = false)
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Id
    @Column(name = "DATA_DATE", nullable = false)
    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    @Basic
    @Column(name = "TIER_LIST")
    public Short getTierList() {
        return tierList;
    }

    public void setTierList(Short tierList) {
        this.tierList = tierList;
    }

    @Basic
    @Column(name = "TOTAL_COLLISION_SKU")
    public Long getTotalCollisionSku() {
        return totalCollisionSku;
    }

    public void setTotalCollisionSku(Long totalCollisionSku) {
        this.totalCollisionSku = totalCollisionSku;
    }

    @Basic
    @Column(name = "TOTAL_NON_COLLISION_SKU")
    public Long getTotalNonCollisionSku() {
        return totalNonCollisionSku;
    }

    public void setTotalNonCollisionSku(Long totalNonCollisionSku) {
        this.totalNonCollisionSku = totalNonCollisionSku;
    }

    @Basic
    @Column(name = "TOTAL_CPC_LIST_SKU")
    public Long getTotalCpcListSku() {
        return totalCpcListSku;
    }

    public void setTotalCpcListSku(Long totalCpcListSku) {
        this.totalCpcListSku = totalCpcListSku;
    }

    @Basic
    @Column(name = "TOTAL_COLLISION_INVESTMENT", precision = 2)
    public BigDecimal getTotalCollisionInvestment() {
        return totalCollisionInvestment;
    }

    public void setTotalCollisionInvestment(BigDecimal totalCollisionPurchases) {
        this.totalCollisionInvestment = totalCollisionPurchases;
    }

    @Basic
    @Column(name = "TOTAL_NON_COLLISION_INVESTMENT", precision = 2)
    public BigDecimal getTotalNonCollisionInvestment() {
        return totalNonCollisionInvestment;
    }

    public void setTotalNonCollisionInvestment(BigDecimal totalNonCollisionPurchases) {
        this.totalNonCollisionInvestment = totalNonCollisionPurchases;
    }

    @Basic
    @Column(name = "TOTAL_CPC_LIST_INVESTMENT", precision = 2)
    public BigDecimal getTotalCpcListInvestment() {
        return totalCpcListInvestment;
    }

    public void setTotalCpcListInvestment(BigDecimal totalCpcListPurchases) {
        this.totalCpcListInvestment = totalCpcListPurchases;
    }

    @Basic
    @Column(name = "CPC_OBJECTIVE", precision = 2)
    public BigDecimal getCpcObjective() {
        return cpcObjective;
    }

    public void setCpcObjective(BigDecimal cpcObjective) {
        this.cpcObjective = cpcObjective;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcKpiEntity that = (CpcKpiEntity) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
        if (tierList != null ? !tierList.equals(that.tierList) : that.tierList != null) return false;
        if (totalCollisionSku != null ? !totalCollisionSku.equals(that.totalCollisionSku) : that.totalCollisionSku != null)
            return false;
        if (totalNonCollisionSku != null ? !totalNonCollisionSku.equals(that.totalNonCollisionSku) : that.totalNonCollisionSku != null)
            return false;
        if (totalCpcListSku != null ? !totalCpcListSku.equals(that.totalCpcListSku) : that.totalCpcListSku != null)
            return false;
        if (totalCollisionInvestment != null ? !totalCollisionInvestment.equals(that.totalCollisionInvestment) : that.totalCollisionInvestment != null)
            return false;
        if (totalNonCollisionInvestment != null ? !totalNonCollisionInvestment.equals(that.totalNonCollisionInvestment) : that.totalNonCollisionInvestment != null)
            return false;
        if (totalCpcListInvestment != null ? !totalCpcListInvestment.equals(that.totalCpcListInvestment) : that.totalCpcListInvestment != null)
            return false;
        if (cpcObjective != null ? !cpcObjective.equals(that.cpcObjective) : that.cpcObjective != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
        result = 31 * result + (tierList != null ? tierList.hashCode() : 0);
        result = 31 * result + (totalCollisionSku != null ? totalCollisionSku.hashCode() : 0);
        result = 31 * result + (totalNonCollisionSku != null ? totalNonCollisionSku.hashCode() : 0);
        result = 31 * result + (totalCpcListSku != null ? totalCpcListSku.hashCode() : 0);
        result = 31 * result + (totalCollisionInvestment != null ? totalCollisionInvestment.hashCode() : 0);
        result = 31 * result + (totalNonCollisionInvestment != null ? totalNonCollisionInvestment.hashCode() : 0);
        result = 31 * result + (totalCpcListInvestment != null ? totalCpcListInvestment.hashCode() : 0);
        result = 31 * result + (cpcObjective != null ? cpcObjective.hashCode() : 0);
        return result;
    }
}
