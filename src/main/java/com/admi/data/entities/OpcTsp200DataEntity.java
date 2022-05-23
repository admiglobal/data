package com.admi.data.entities;

import com.admi.data.entities.keys.OpcTsp200DataEntityPK;

import javax.persistence.*;

@Entity
@Table(name = "OPC_TSP_200_DATA", schema = "ADMI")
@IdClass(OpcTsp200DataEntityPK.class)
public class OpcTsp200DataEntity {
    private String paCode;
    private String partNumber;
    private Integer qoh;
    private Integer partCostCents;

    @Id
    @Column(name = "PA_CODE")
    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    @Id
    @Column(name = "PART_NUMBER")
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
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
    @Column(name = "PART_COST_CENTS")
    public Integer getPartCostCents() {
        return partCostCents;
    }

    public void setPartCostCents(Integer partCostCents) {
        this.partCostCents = partCostCents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpcTsp200DataEntity that = (OpcTsp200DataEntity) o;

        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
        if (partNumber != null ? !partNumber.equals(that.partNumber) : that.partNumber != null) return false;
        if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;
        if (partCostCents != null ? !partCostCents.equals(that.partCostCents) : that.partCostCents != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paCode != null ? paCode.hashCode() : 0;
        result = 31 * result + (partNumber != null ? partNumber.hashCode() : 0);
        result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
        result = 31 * result + (partCostCents != null ? partCostCents.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpcTsp200DataEntity{" +
                "paCode='" + paCode + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", qoh=" + qoh +
                ", partCostCents=" + partCostCents +
                '}';
    }
}
