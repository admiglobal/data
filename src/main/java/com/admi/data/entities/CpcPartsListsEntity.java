package com.admi.data.entities;

import com.admi.data.entities.keys.CpcPartsListsEntityPK;

import javax.persistence.*;

@Entity
@Table(name = "CPC_PARTS_LISTS", schema = "ADMI")
@IdClass(CpcPartsListsEntityPK.class)
public class CpcPartsListsEntity {
    private String partsList;
    private Short rank;
    private String primarySku;
    private String prefix;
    private String base;
    private String suffix;
    private String alternateSku;

    @Id
    @Column(name = "PARTS_LIST", nullable = false, length = 2)
    public String getPartsList() {
        return partsList;
    }

    public void setPartsList(String partsList) {
        this.partsList = partsList;
    }

    @Id
    @Column(name = "RANK", nullable = false)
    public Short getRank() {
        return rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "PRIMARY_SKU", length = 30)
    public String getPrimarySku() {
        return primarySku;
    }

    public void setPrimarySku(String primarySku) {
        this.primarySku = primarySku;
    }

    @Basic
    @Column(name = "PREFIX", length = 10)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "BASE", length = 10)
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Basic
    @Column(name = "SUFFIX", length = 10)
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Basic
    @Column(name = "ALTERNATE_SKU", length = 90)
    public String getAlternateSku() {
        return alternateSku;
    }

    public void setAlternateSku(String alternateSku) {
        this.alternateSku = alternateSku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpcPartsListsEntity that = (CpcPartsListsEntity) o;

        if (partsList != null ? !partsList.equals(that.partsList) : that.partsList != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (primarySku != null ? !primarySku.equals(that.primarySku) : that.primarySku != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (base != null ? !base.equals(that.base) : that.base != null) return false;
        if (suffix != null ? !suffix.equals(that.suffix) : that.suffix != null) return false;
        if (alternateSku != null ? !alternateSku.equals(that.alternateSku) : that.alternateSku != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partsList != null ? partsList.hashCode() : 0;
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (primarySku != null ? primarySku.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        result = 31 * result + (alternateSku != null ? alternateSku.hashCode() : 0);
        return result;
    }
}
