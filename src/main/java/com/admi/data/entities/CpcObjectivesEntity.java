package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CPC_OBJECTIVES", schema = "ADMI")
public class CpcObjectivesEntity {
    private LocalDate objectiveMonth;
    private Double objectivePercent;
    private Double cpcPercent;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "OBJECTIVE_MONTH", nullable = false)
    public LocalDate getObjectiveMonth() {
        return objectiveMonth;
    }

    public void setObjectiveMonth(LocalDate objectiveMonth) {
        this.objectiveMonth = objectiveMonth;
    }

    @Basic
    @Column(name = "OBJECTIVE_PERCENT", nullable = true, precision = 2)
    public Double getObjectivePercent() {
        return objectivePercent;
    }

    public void setObjectivePercent(Double objectivePercent) {
        this.objectivePercent = objectivePercent;
    }

    @Basic
    @Column(name = "CPC_PERCENT", nullable = true, precision = 4)
    public Double getCpcPercent() {
        return cpcPercent;
    }

    public void setCpcPercent(Double cpcPercent) {
        this.cpcPercent = cpcPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcObjectivesEntity that = (CpcObjectivesEntity) o;

        if (objectiveMonth != null ? !objectiveMonth.equals(that.objectiveMonth) : that.objectiveMonth != null)
            return false;
        if (objectivePercent != null ? !objectivePercent.equals(that.objectivePercent) : that.objectivePercent != null)
            return false;
        if (cpcPercent != null ? !cpcPercent.equals(that.cpcPercent) : that.cpcPercent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objectiveMonth != null ? objectiveMonth.hashCode() : 0;
        result = 31 * result + (objectivePercent != null ? objectivePercent.hashCode() : 0);
        result = 31 * result + (cpcPercent != null ? cpcPercent.hashCode() : 0);
        return result;
    }
}
