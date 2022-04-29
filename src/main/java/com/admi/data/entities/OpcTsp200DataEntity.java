package com.admi.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "OPC_TSP_200_DATA", schema = "ADMI")
public class OpcTsp200DataEntity {
    @Id
    @Column(name = "PA_CODE")
    private String paCode;
    @Basic
    @Column(name = "PART_NUMBER")
    private String partNumber;
    @Basic
    @Column(name = "QOH")
    private Long qoh;

    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Long getQoh() {
        return qoh;
    }

    public void setQoh(Long qoh) {
        this.qoh = qoh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpcTsp200DataEntity that = (OpcTsp200DataEntity) o;

        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
        if (partNumber != null ? !partNumber.equals(that.partNumber) : that.partNumber != null) return false;
        if (qoh != null ? !qoh.equals(that.qoh) : that.qoh != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paCode != null ? paCode.hashCode() : 0;
        result = 31 * result + (partNumber != null ? partNumber.hashCode() : 0);
        result = 31 * result + (qoh != null ? qoh.hashCode() : 0);
        return result;
    }
}
