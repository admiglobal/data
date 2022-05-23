package com.admi.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "OPC_TSP_200", schema = "ADMI")
public class OpcTsp200Entity {
    private Integer rank;
    private String ocPartNumber;
    private String servicePartNumber;
    private String partDescription;
    private Double dealerNet;

    @Id
    @Column(name = "RANK")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "OC_PART_NUMBER")
    public String getOcPartNumber() {
        return ocPartNumber;
    }

    public void setOcPartNumber(String ocPartNumber) {
        this.ocPartNumber = ocPartNumber;
    }

    @Basic
    @Column(name = "SERVICE_PART_NUMBER")
    public String getServicePartNumber() {
        return servicePartNumber;
    }

    public void setServicePartNumber(String servicePartNumber) {
        this.servicePartNumber = servicePartNumber;
    }

    @Basic
    @Column(name = "PART_DESCRIPTION")
    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    @Basic
    @Column(name = "DEALER_NET")
    public Double getDealerNet() {
        return dealerNet;
    }

    public void setDealerNet(Double dealerNet) {
        this.dealerNet = dealerNet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpcTsp200Entity that = (OpcTsp200Entity) o;

        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (ocPartNumber != null ? !ocPartNumber.equals(that.ocPartNumber) : that.ocPartNumber != null) return false;
        if (servicePartNumber != null ? !servicePartNumber.equals(that.servicePartNumber) : that.servicePartNumber != null)
            return false;
        if (partDescription != null ? !partDescription.equals(that.partDescription) : that.partDescription != null)
            return false;
        if (dealerNet != null ? !dealerNet.equals(that.dealerNet) : that.dealerNet != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rank != null ? rank.hashCode() : 0;
        result = 31 * result + (ocPartNumber != null ? ocPartNumber.hashCode() : 0);
        result = 31 * result + (servicePartNumber != null ? servicePartNumber.hashCode() : 0);
        result = 31 * result + (partDescription != null ? partDescription.hashCode() : 0);
        result = 31 * result + (dealerNet != null ? dealerNet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpcTsp200Entity{" +
                "rank=" + rank +
                ", ocPartNumber='" + ocPartNumber + '\'' +
                ", servicePartNumber='" + servicePartNumber + '\'' +
                ", partDescription='" + partDescription + '\'' +
                '}';
    }
}
