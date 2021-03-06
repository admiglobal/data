package com.admi.data.entities;

import com.admi.data.entities.keys.TipKpiEntityPK;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TIP_KPI", schema = "ADMI")
@IdClass(TipKpiEntityPK.class)
public class TipKpiEntity {
    private Long dealerId;
    private LocalDateTime dataDate;
    private Integer lines;
    private Integer orderTotal;
    private Integer totalStockParts;
    private Integer totalStockPartsQoh;

    public TipKpiEntity() {}

    public TipKpiEntity(Long dealerId, LocalDateTime dataDate, Integer lines, Integer orderTotal, Integer totalStockParts, Integer totalStockPartsQoh) {
        this.dealerId = dealerId;
        this.dataDate = dataDate;
        this.lines = lines;
        this.orderTotal = orderTotal;
        this.totalStockParts = totalStockParts;
        this.totalStockPartsQoh = totalStockPartsQoh;
    }

    public TipKpiEntity(Long dealerId, LocalDateTime dataDate, Integer totalStockParts, Integer totalStockPartsQoh) {
        this.dealerId = dealerId;
        this.dataDate = dataDate;
        this.totalStockParts = totalStockParts;
        this.totalStockPartsQoh = totalStockPartsQoh;
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
    @Column(name = "DATA_DATE", nullable = false)
    public LocalDateTime getDataDate() {
        return dataDate;
    }

    public void setDataDate(LocalDateTime dataDate) {
        this.dataDate = dataDate;
    }

    @Basic
    @Column(name = "LINES", nullable = true, precision = 0)
    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }

    @Basic
    @Column(name = "ORDER_TOTAL", nullable = true, precision = 0)
    public Integer getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Integer orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Basic
    @Column(name = "TOTAL_STOCK_PARTS", nullable = true, precision = 0)
    public Integer getTotalStockParts() {
        return totalStockParts;
    }

    public void setTotalStockParts(Integer totalStockParts) {
        this.totalStockParts = totalStockParts;
    }

    @Basic
    @Column(name = "TOTAL_STOCK_PARTS_QOH", nullable = true, precision = 0)
    public Integer getTotalStockPartsQoh() {
        return totalStockPartsQoh;
    }

    public void setTotalStockPartsQoh(Integer totalStockPartsQoh) {
        this.totalStockPartsQoh = totalStockPartsQoh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipKpiEntity that = (TipKpiEntity) o;

        if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
        if (dataDate != null ? !dataDate.equals(that.dataDate) : that.dataDate != null) return false;
        if (lines != null ? !lines.equals(that.lines) : that.lines != null) return false;
        if (orderTotal != null ? !orderTotal.equals(that.orderTotal) : that.orderTotal != null) return false;
        if (totalStockParts != null ? !totalStockParts.equals(that.totalStockParts) : that.totalStockParts != null)
            return false;
        if (totalStockPartsQoh != null ? !totalStockPartsQoh.equals(that.totalStockPartsQoh) : that.totalStockPartsQoh != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dealerId != null ? dealerId.hashCode() : 0;
        result = 31 * result + (dataDate != null ? dataDate.hashCode() : 0);
        result = 31 * result + (lines != null ? lines.hashCode() : 0);
        result = 31 * result + (orderTotal != null ? orderTotal.hashCode() : 0);
        result = 31 * result + (totalStockParts != null ? totalStockParts.hashCode() : 0);
        result = 31 * result + (totalStockPartsQoh != null ? totalStockPartsQoh.hashCode() : 0);
        return result;
    }
}
