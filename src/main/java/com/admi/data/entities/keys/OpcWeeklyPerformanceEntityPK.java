package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

public class OpcWeeklyPerformanceEntityPK implements Serializable {
    private String paCode;
    private LocalDate snapshotDate;

    @Column(name = "PA_CODE")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    @Column(name = "SNAPSHOT_DATE")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        try{
            this.snapshotDate = snapshotDate;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpcWeeklyPerformanceEntityPK that = (OpcWeeklyPerformanceEntityPK) o;

        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
        if (snapshotDate != null ? !snapshotDate.equals(that.snapshotDate) : that.snapshotDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paCode != null ? paCode.hashCode() : 0;
        result = 31 * result + (snapshotDate != null ? snapshotDate.hashCode() : 0);
        return result;
    }
}
