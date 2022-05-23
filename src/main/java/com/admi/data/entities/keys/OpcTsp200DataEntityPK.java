package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class OpcTsp200DataEntityPK implements Serializable {
    private String paCode;
    private String partNumber;

    @Column(name = "PA_CODE")
    @Id
    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    @Column(name = "PART_NUMBER")
    @Id
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpcTsp200DataEntityPK that = (OpcTsp200DataEntityPK) o;

        if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
        if (partNumber != null ? !partNumber.equals(that.partNumber) : that.partNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paCode != null ? paCode.hashCode() : 0;
        result = 31 * result + (partNumber != null ? partNumber.hashCode() : 0);
        return result;
    }
}
