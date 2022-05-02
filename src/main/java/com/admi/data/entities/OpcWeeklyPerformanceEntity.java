package com.admi.data.entities;

import com.admi.data.entities.keys.OpcWeeklyPerformanceEntityPK;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "OPC_WEEKLY_PERFORMANCE", schema = "ADMI")
@IdClass(OpcWeeklyPerformanceEntityPK.class)
public class OpcWeeklyPerformanceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PA_CODE")
    private String paCode;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SNAPSHOT_DATE")
    private LocalDate snapshotDate;

    @Basic
    @Column(name = "QOH")
    private Integer qoh;

    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

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

        OpcWeeklyPerformanceEntity that = (OpcWeeklyPerformanceEntity) o;

        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
        if (snapshotDate != null ? !snapshotDate.equals(that.snapshotDate) : that.snapshotDate != null) return false;
        if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paCode != null ? paCode.hashCode() : 0;
        result = 31 * result + (snapshotDate != null ? snapshotDate.hashCode() : 0);
        result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpcWeeklyPerformanceEntity{" +
                "paCode='" + paCode + '\'' +
                ", snapshotDate=" + snapshotDate +
                ", qoh=" + qoh +
                '}';
    }
}
