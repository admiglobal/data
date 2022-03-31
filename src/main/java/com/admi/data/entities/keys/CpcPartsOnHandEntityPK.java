package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class CpcPartsOnHandEntityPK implements Serializable {
    private Long dealerId;
    private String partNo;

    @Column(name = "DEALER_ID", nullable = false)
    @Id
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Column(name = "PART_NO", nullable = false, length = 30)
    @Id
    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcPartsOnHandEntityPK that = (CpcPartsOnHandEntityPK) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (partNo != null ? partNo.hashCode() : 0);
        return result;
    }
}
