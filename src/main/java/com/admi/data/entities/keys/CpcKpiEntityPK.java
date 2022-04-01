package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

public class CpcKpiEntityPK implements Serializable {
    private Long dealerId;
    private LocalDate dataDate;

    @Column(name = "DEALER_ID", nullable = false, length = 6)
    @Id
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Column(name = "DATA_DATE", nullable = false)
    @Id
    public LocalDate getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDate dataDate) {
        this.dataDate = dataDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcKpiEntityPK that = (CpcKpiEntityPK) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
        return result;
    }
}
