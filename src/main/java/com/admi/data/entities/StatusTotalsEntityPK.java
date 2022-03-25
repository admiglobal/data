package com.admi.data.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class StatusTotalsEntityPK implements Serializable {
    @Column(name = "DEALER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dealerId;
    @Column(name = "DATA_DATE")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dataDate;
    @Column(name = "STATUS")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String status;

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public int getDataDate() {
        return dataDate;
    }

    public void setDataDate(int dataDate) {
        this.dataDate = dataDate;
    }

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

        if (dealerId != that.dealerId) return false;
        if (dataDate != that.dataDate) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId;
        result = 31 * result + dataDate;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
