package com.admi.data.entities.keys;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class CpcPartsListsEntityPK implements Serializable {
    private String partsList;
    private Short rank;

    @Column(name = "PARTS_LIST", nullable = false, length = 3)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getPartsList() {
        return partsList;
    }

    public void setPartsList(String partsList) {
        this.partsList = partsList;
    }

    @Column(name = "RANK", nullable = false, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Short getRank() {
        return rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcPartsListsEntityPK that = (CpcPartsListsEntityPK) o;

        if (partsList != null ? !partsList.equals(that.partsList) : that.partsList != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partsList != null ? partsList.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        return result;
    }
}