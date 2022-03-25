package com.admi.data.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class StatusTotalsEntityPK implements Serializable {
    private Long dealerId;
    private Long dataDate;
    private String status;

    @Column(name = "DEALER_ID", nullable = false, precision = 0)
    @Id
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Column(name = "DATA_DATE", nullable = false, precision = 0)
    @Id
    public Long getDataDate() {
        return dataDate;
    }

    public void setDataDate(Long dataDate) {
        this.dataDate = dataDate;
    }

    @Column(name = "STATUS", nullable = false)
    @Id
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusTotalsEntityPK that = (StatusTotalsEntityPK) o;

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
