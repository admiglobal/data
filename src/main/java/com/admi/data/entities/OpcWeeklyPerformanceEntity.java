package com.admi.data.entities;

import com.admi.data.entities.keys.OpcWeeklyPerformanceEntityPK;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "OPC_WEEKLY_PERFORMANCE", schema = "ADMI", catalog = "")
@IdClass(OpcWeeklyPerformanceEntityPK.class)
public class OpcWeeklyPerformanceEntity {
    private String paCode;
    private LocalDate snapshotDate;
    private Integer qoh;
    private Integer opcValueCents;
    private Integer totalSku;
    private Integer totalValueCents;
    private Integer otherOcSku;
    private Integer otherOcValueCents;
    private Integer mcSku;
    private Integer mcValueCents;
    private Integer fordSku;
    private Integer fordValueCents;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PA_CODE")
    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SNAPSHOT_DATE")
    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        try{
            this.snapshotDate = snapshotDate;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Basic
    @Column(name = "QOH")
    public Integer getQoh() {
        return qoh;
    }

    public void setQoh(Integer qoh) {
        this.qoh = qoh;
    }

    @Basic
    @Column(name = "OPC_VALUE_CENTS")
    public Integer getOpcValueCents() {
        return opcValueCents;
    }

    public void setOpcValueCents(Integer opcValueCents) {
        this.opcValueCents = opcValueCents;
    }

    @Basic
    @Column(name = "TOTAL_SKU")
    public Integer getTotalSku() {
        return totalSku;
    }

    public void setTotalSku(Integer totalSku) {
        this.totalSku = totalSku;
    }

    @Basic
    @Column(name = "TOTAL_VALUE_CENTS")
    public Integer getTotalValueCents() {
        return totalValueCents;
    }

    public void setTotalValueCents(Integer totalValueCents) {
        this.totalValueCents = totalValueCents;
    }

    @Basic
    @Column(name = "OTHER_OC_SKU")
    public Integer getOtherOcSku() {
        return otherOcSku;
    }

    public void setOtherOcSku(Integer otherOcSku) {
        this.otherOcSku = otherOcSku;
    }

    @Basic
    @Column(name = "OTHER_OC_VALUE_CENTS")
    public Integer getOtherOcValueCents() {
        return otherOcValueCents;
    }

    public void setOtherOcValueCents(Integer otherOcValueCents) {
        this.otherOcValueCents = otherOcValueCents;
    }

    @Basic
    @Column(name = "MC_SKU")
    public Integer getMcSku() {
        return mcSku;
    }

    public void setMcSku(Integer mcSku) {
        this.mcSku = mcSku;
    }

    @Basic
    @Column(name = "MC_VALUE_CENTS")
    public Integer getMcValueCents() {
        return mcValueCents;
    }

    public void setMcValueCents(Integer mcValueCents) {
        this.mcValueCents = mcValueCents;
    }

    @Basic
    @Column(name = "FORD_SKU")
    public Integer getFordSku() {
        return fordSku;
    }

    public void setFordSku(Integer fordSku) {
        this.fordSku = fordSku;
    }

    @Basic
    @Column(name = "FORD_VALUE_CENTS")
    public Integer getFordValueCents() {
        return fordValueCents;
    }

    public void setFordValueCents(Integer fordValueCents) {
        this.fordValueCents = fordValueCents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpcWeeklyPerformanceEntity that = (OpcWeeklyPerformanceEntity) o;

        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
        if (snapshotDate != null ? !snapshotDate.equals(that.snapshotDate) : that.snapshotDate != null) return false;
        if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;
        if (opcValueCents != null ? !opcValueCents.equals(that.opcValueCents) : that.opcValueCents != null)
            return false;
        if (totalSku != null ? !totalSku.equals(that.totalSku) : that.totalSku != null) return false;
        if (totalValueCents != null ? !totalValueCents.equals(that.totalValueCents) : that.totalValueCents != null)
            return false;
        if (otherOcSku != null ? !otherOcSku.equals(that.otherOcSku) : that.otherOcSku != null) return false;
        if (otherOcValueCents != null ? !otherOcValueCents.equals(that.otherOcValueCents) : that.otherOcValueCents != null)
            return false;
        if (mcSku != null ? !mcSku.equals(that.mcSku) : that.mcSku != null) return false;
        if (mcValueCents != null ? !mcValueCents.equals(that.mcValueCents) : that.mcValueCents != null) return false;
        if (fordSku != null ? !fordSku.equals(that.fordSku) : that.fordSku != null) return false;
        if (fordValueCents != null ? !fordValueCents.equals(that.fordValueCents) : that.fordValueCents != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paCode != null ? paCode.hashCode() : 0;
        result = 31 * result + (snapshotDate != null ? snapshotDate.hashCode() : 0);
        result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
        result = 31 * result + (opcValueCents != null ? opcValueCents.hashCode() : 0);
        result = 31 * result + (totalSku != null ? totalSku.hashCode() : 0);
        result = 31 * result + (totalValueCents != null ? totalValueCents.hashCode() : 0);
        result = 31 * result + (otherOcSku != null ? otherOcSku.hashCode() : 0);
        result = 31 * result + (otherOcValueCents != null ? otherOcValueCents.hashCode() : 0);
        result = 31 * result + (mcSku != null ? mcSku.hashCode() : 0);
        result = 31 * result + (mcValueCents != null ? mcValueCents.hashCode() : 0);
        result = 31 * result + (fordSku != null ? fordSku.hashCode() : 0);
        result = 31 * result + (fordValueCents != null ? fordValueCents.hashCode() : 0);
        return result;
    }
}
