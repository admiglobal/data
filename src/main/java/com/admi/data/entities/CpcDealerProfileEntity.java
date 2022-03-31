package com.admi.data.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CPC_DEALER_PROFILE", schema = "ADMI")
public class CpcDealerProfileEntity {
    private Long dealerId;
    private String partsList;
    private Short tierList;
    private String primaryPaCode;
    private String secondaryPaCode;
    private Integer salesCode;

    public CpcDealerProfileEntity() {

    }

    public CpcDealerProfileEntity(Long dealerId, String partsList, Short tierList) {
        this.dealerId = dealerId;
        this.partsList = partsList;
        this.tierList = tierList;
    }

    @Basic
    @Id
    @Column(name = "DEALER_ID", nullable = true, precision = 0)
    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Basic
    @Column(name = "PARTS_LIST", nullable = true, length = 2)
    public String getPartsList() {
        return partsList;
    }

    public void setPartsList(String partsList) {
        this.partsList = partsList;
    }

    @Basic
    @Column(name = "TIER_LIST", nullable = true, precision = 0)
    public Short getTierList() {
        return tierList;
    }

    public void setTierList(Short tierList) {
        this.tierList = tierList;
    }

    @Basic
    @Column(name = "PRIMARY_PA_CODE", nullable = true, length = 5)
    public String getPrimaryPaCode() {
        return primaryPaCode;
    }

    public void setPrimaryPaCode(String primaryPaCode) {
        this.primaryPaCode = primaryPaCode;
    }

    @Basic
    @Column(name = "SECONDARY_PA_CODE", nullable = true, length = 5)
    public String getSecondaryPaCode() {
        return secondaryPaCode;
    }

    public void setSecondaryPaCode(String secondaryPaCode) {
        this.secondaryPaCode = secondaryPaCode;
    }

    @Basic
    @Column(name = "SALES_CODE", nullable = true, precision = 0)
    public Integer getSalesCode() {
        return salesCode;
    }

    public void setSalesCode(Integer salesCode) {
        this.salesCode = salesCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CpcDealerProfileEntity)) return false;
        CpcDealerProfileEntity that = (CpcDealerProfileEntity) o;
        return dealerId.equals(that.dealerId) && Objects.equals(partsList, that.partsList) && Objects.equals(tierList, that.tierList) && Objects.equals(primaryPaCode, that.primaryPaCode) && Objects.equals(secondaryPaCode, that.secondaryPaCode) && Objects.equals(salesCode, that.salesCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealerId, partsList, tierList, primaryPaCode, secondaryPaCode, salesCode);
    }
}
