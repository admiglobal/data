package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AIP_STATUS_TOTALS", schema = "ADMI")
@IdClass(StatusTotalsEntityPK.class)
public class StatusTotalsEntity {
    private Long dealerId;
    private Long dataDate;
    private String status;
    private Float total;
    private Integer currentDms;
    private LocalDate updatedDate;
    private Integer graphNumber;

    public StatusTotalsEntity() {};

    public StatusTotalsEntity(Long dealerId, Long dataDate, String status, Float total, Integer graphNumber, Integer currentDms) {
        this.dealerId = dealerId;
        this.dataDate = dataDate;
        this.status = status;
        this.total = total;
        this.graphNumber = graphNumber;
        this.currentDms = currentDms;
        this.updatedDate = LocalDate.now();
    }

    @Id
    @Column(name = "DEALER_ID", nullable = false, precision = 0)
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Id
    @Column(name = "DATA_DATE", nullable = false, precision = 0)
    public Long getDataDate() {
        return dataDate;
    }

    public void setDataDate(Long dataDate) {
        this.dataDate = dataDate;
    }

    @Id
    @Column(name = "STATUS", nullable = true)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "TOTAL", nullable = true, precision = 2)
    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Basic
    @Column(name = "CURRENT_DMS", nullable = true, precision = 0)
    public Integer getCurrentDms() {
        return currentDms;
    }

    public void setCurrentDms(Integer currentDms) {
        this.currentDms = currentDms;
    }

    @Basic
    @Column(name = "UPDATED_DATE", nullable = true)
    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Basic
    @Column(name = "GRAPH_NUMBER", nullable = true, precision = 0)
    public Integer getGraphNumber() {
        return graphNumber;
    }

    public void setGraphNumber(Integer graphNumber) {
        this.graphNumber = graphNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusTotalsEntity that = (StatusTotalsEntity) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (currentDms != null ? !currentDms.equals(that.currentDms) : that.currentDms != null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (graphNumber != null ? !graphNumber.equals(that.graphNumber) : that.graphNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (currentDms != null ? currentDms.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (graphNumber != null ? graphNumber.hashCode() : 0);
        return result;
    }
}
