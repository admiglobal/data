package com.admi.data.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "FORD_PT", schema = "ADMI")
public class PriceTapeEntity {
    private String partNo;
    private String tape;
    private String motorcraft;
    private String prefix;
    private String base;
    private String suffix;
    private String mcpre;
    private String mcbase;
    private String mcsuf;
    private String replcg1;
    private String replcg2;
    private BigDecimal fCost;
    private BigDecimal fcore;
    private BigDecimal pcValue;
    private Integer multQty;
    private String obsstatus;
    private String interchange;
    private String pipp;
    private String termEligibility;
    private LocalDate lstrtrndate;
    private Double pippProgPrice;
    private String brand;
    private Double discount;
    private String allowance;
    private Double flist;
    private Double jobber;
    private Double wd;
    private String prcchgdir;
    private String d2D;
    private Double d2DPrice;
    private Integer unitSellPack;
    private String description;
    private String productLine;
    private String marketProductLine;
    private String collision;
    private String lsd;
    private String newpart;
    private Double natfltprc;
    private String suppdirship;
    private String pwrtrncomp;
    private LocalDate prtreldate;
    private Integer sellpkqty;
    private String pdcType;
    private String doi;
    private Double fltprc;
    private Double govprc;
    private String reman;
    private Integer wgt;
    private String packingCode;
    private Double msip;
    private Integer calcedQty;
    private String pti;

    @Id
    @Column(name = "PARTNO")
    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    @Basic
    @Column(name = "TAPE")
    public String getTape() {
        return tape;
    }

    public void setTape(String tape) {
        this.tape = tape;
    }

    @Basic
    @Column(name = "MOTORCRAFT")
    public String getMotorcraft() {
        return motorcraft;
    }

    public void setMotorcraft(String motorcraft) {
        this.motorcraft = motorcraft;
    }

    @Basic
    @Column(name = "PREFIX")
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "BASE")
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    @Basic
    @Column(name = "SUFFIX")
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Basic
    @Column(name = "MCPRE")
    public String getMcpre() {
        return mcpre;
    }

    public void setMcpre(String mcpre) {
        this.mcpre = mcpre;
    }

    @Basic
    @Column(name = "MCBASE")
    public String getMcbase() {
        return mcbase;
    }

    public void setMcbase(String mcbase) {
        this.mcbase = mcbase;
    }

    @Basic
    @Column(name = "MCSUF")
    public String getMcsuf() {
        return mcsuf;
    }

    public void setMcsuf(String mcsuf) {
        this.mcsuf = mcsuf;
    }

    @Basic
    @Column(name = "REPLCG1")
    public String getReplcg1() {
        return replcg1;
    }

    public void setReplcg1(String replcg1) {
        this.replcg1 = replcg1;
    }

    @Basic
    @Column(name = "REPLCG2")
    public String getReplcg2() {
        return replcg2;
    }

    public void setReplcg2(String replcg2) {
        this.replcg2 = replcg2;
    }

    @Basic
    @Column(name = "FCOST")
    public BigDecimal getFCost() {
        return fCost;
    }

    public void setFCost(BigDecimal fCost) {
        this.fCost = fCost;
    }

    @Basic
    @Column(name = "FCORE")
    public BigDecimal getFcore() {
        return fcore;
    }

    public void setFcore(BigDecimal fcore) {
        this.fcore = fcore;
    }

    @Basic
    @Column(name = "PC_VALUE")
    public BigDecimal getPcValue() {
        return pcValue;
    }

    public void setPcValue(BigDecimal pcValue) {
        this.pcValue = pcValue;
    }

    @Basic
    @Column(name = "MULT_QTY")
    public Integer getMultQty() {
        return multQty;
    }

    public void setMultQty(Integer multQty) {
        this.multQty = multQty;
    }

    @Basic
    @Column(name = "OBSSTATUS")
    public String getObsstatus() {
        return obsstatus;
    }

    public void setObsstatus(String obsstatus) {
        this.obsstatus = obsstatus;
    }

    @Basic
    @Column(name = "INTERCHANGE")
    public String getInterchange() {
        return interchange;
    }

    public void setInterchange(String interchange) {
        this.interchange = interchange;
    }

    @Basic
    @Column(name = "PIPP")
    public String getPipp() {
        return pipp;
    }

    public void setPipp(String pipp) {
        this.pipp = pipp;
    }

    @Basic
    @Column(name = "TERMELIGIBILITY")
    public String getTermEligibility() {
        return termEligibility;
    }

    public void setTermEligibility(String termeligibility) {
        this.termEligibility = termeligibility;
    }

    @Basic
    @Column(name = "LSTRTRNDATE")
    public LocalDate getLstrtrndate() {
        return lstrtrndate;
    }

    public void setLstrtrndate(LocalDate lstrtrndate) {
        this.lstrtrndate = lstrtrndate;
    }

    @Basic
    @Column(name = "PIPP_PROG_PRICE")
    public Double getPippProgPrice() {
        return pippProgPrice;
    }

    public void setPippProgPrice(Double pippProgPrice) {
        this.pippProgPrice = pippProgPrice;
    }

    @Basic
    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "DISCOUNT")
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "ALLOWANCE")
    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    @Basic
    @Column(name = "FLIST")
    public Double getFlist() {
        return flist;
    }

    public void setFlist(Double flist) {
        this.flist = flist;
    }

    @Basic
    @Column(name = "JOBBER")
    public Double getJobber() {
        return jobber;
    }

    public void setJobber(Double jobber) {
        this.jobber = jobber;
    }

    @Basic
    @Column(name = "WD")
    public Double getWd() {
        return wd;
    }

    public void setWd(Double wd) {
        this.wd = wd;
    }

    @Basic
    @Column(name = "PRCCHGDIR")
    public String getPrcchgdir() {
        return prcchgdir;
    }

    public void setPrcchgdir(String prcchgdir) {
        this.prcchgdir = prcchgdir;
    }

    @Basic
    @Column(name = "D2D")
    public String getD2D() {
        return d2D;
    }

    public void setD2D(String d2D) {
        this.d2D = d2D;
    }

    @Basic
    @Column(name = "D2D_PRICE")
    public Double getD2DPrice() {
        return d2DPrice;
    }

    public void setD2DPrice(Double d2DPrice) {
        this.d2DPrice = d2DPrice;
    }

    @Basic
    @Column(name = "UNIT_SELL_PACK")
    public Integer getUnitSellPack() {
        return unitSellPack;
    }

    public void setUnitSellPack(Integer unitSellPack) {
        this.unitSellPack = unitSellPack;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "PRODUCT_LINE")
    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    @Basic
    @Column(name = "MARKET_PRODUCT_LINE")
    public String getMarketProductLine() {
        return marketProductLine;
    }

    public void setMarketProductLine(String marketProductLine) {
        this.marketProductLine = marketProductLine;
    }

    @Basic
    @Column(name = "COLLISION")
    public String getCollision() {
        return collision;
    }

    public void setCollision(String collision) {
        this.collision = collision;
    }

    @Basic
    @Column(name = "LSD")
    public String getLsd() {
        return lsd;
    }

    public void setLsd(String lsd) {
        this.lsd = lsd;
    }

    @Basic
    @Column(name = "NEWPART")
    public String getNewpart() {
        return newpart;
    }

    public void setNewpart(String newpart) {
        this.newpart = newpart;
    }

    @Basic
    @Column(name = "NATFLTPRC")
    public Double getNatfltprc() {
        return natfltprc;
    }

    public void setNatfltprc(Double natfltprc) {
        this.natfltprc = natfltprc;
    }

    @Basic
    @Column(name = "SUPPDIRSHIP")
    public String getSuppdirship() {
        return suppdirship;
    }

    public void setSuppdirship(String suppdirship) {
        this.suppdirship = suppdirship;
    }

    @Basic
    @Column(name = "PWRTRNCOMP")
    public String getPwrtrncomp() {
        return pwrtrncomp;
    }

    public void setPwrtrncomp(String pwrtrncomp) {
        this.pwrtrncomp = pwrtrncomp;
    }

    @Basic
    @Column(name = "PRTRELDATE")
    public LocalDate getPrtreldate() {
        return prtreldate;
    }

    public void setPrtreldate(LocalDate prtreldate) {
        this.prtreldate = prtreldate;
    }

    @Basic
    @Column(name = "SELLPKQTY")
    public Integer getSellpkqty() {
        return sellpkqty;
    }

    public void setSellpkqty(Integer sellpkqty) {
        this.sellpkqty = sellpkqty;
    }

    @Basic
    @Column(name = "PDC_TYPE")
    public String getPdcType() {
        return pdcType;
    }

    public void setPdcType(String pdcType) {
        this.pdcType = pdcType;
    }

    @Basic
    @Column(name = "DOI")
    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Basic
    @Column(name = "FLTPRC")
    public Double getFltprc() {
        return fltprc;
    }

    public void setFltprc(Double fltprc) {
        this.fltprc = fltprc;
    }

    @Basic
    @Column(name = "GOVPRC")
    public Double getGovprc() {
        return govprc;
    }

    public void setGovprc(Double govprc) {
        this.govprc = govprc;
    }

    @Basic
    @Column(name = "REMAN")
    public String getReman() {
        return reman;
    }

    public void setReman(String reman) {
        this.reman = reman;
    }

    @Basic
    @Column(name = "WGT")
    public Integer getWgt() {
        return wgt;
    }

    public void setWgt(Integer wgt) {
        this.wgt = wgt;
    }

    @Basic
    @Column(name = "PACKING_CODE")
    public String getPackingCode() {
        return packingCode;
    }

    public void setPackingCode(String packingCode) {
        this.packingCode = packingCode;
    }

    @Basic
    @Column(name = "MSIP")
    public Double getMsip() {
        return msip;
    }

    public void setMsip(Double msip) {
        this.msip = msip;
    }

    @Basic
    @Column(name = "CALCED_QTY")
    public Integer getCalcedQty() {
        return calcedQty;
    }

    public void setCalcedQty(Integer calcedQty) {
        this.calcedQty = calcedQty;
    }

    @Basic
    @Column(name = "PTI")
    public String getPti() {
        return pti;
    }

    public void setPti(String pti) {
        this.pti = pti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceTapeEntity that = (PriceTapeEntity) o;

        if (partNo != null ? !partNo.equals(that.partNo) : that.partNo != null) return false;
        if (tape != null ? !tape.equals(that.tape) : that.tape != null) return false;
        if (motorcraft != null ? !motorcraft.equals(that.motorcraft) : that.motorcraft != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (base != null ? !base.equals(that.base) : that.base != null) return false;
        if (suffix != null ? !suffix.equals(that.suffix) : that.suffix != null) return false;
        if (mcpre != null ? !mcpre.equals(that.mcpre) : that.mcpre != null) return false;
        if (mcbase != null ? !mcbase.equals(that.mcbase) : that.mcbase != null) return false;
        if (mcsuf != null ? !mcsuf.equals(that.mcsuf) : that.mcsuf != null) return false;
        if (replcg1 != null ? !replcg1.equals(that.replcg1) : that.replcg1 != null) return false;
        if (replcg2 != null ? !replcg2.equals(that.replcg2) : that.replcg2 != null) return false;
        if (fCost != null ? !fCost.equals(that.fCost) : that.fCost != null) return false;
        if (fcore != null ? !fcore.equals(that.fcore) : that.fcore != null) return false;
        if (pcValue != null ? !pcValue.equals(that.pcValue) : that.pcValue != null) return false;
        if (multQty != null ? !multQty.equals(that.multQty) : that.multQty != null) return false;
        if (obsstatus != null ? !obsstatus.equals(that.obsstatus) : that.obsstatus != null) return false;
        if (interchange != null ? !interchange.equals(that.interchange) : that.interchange != null) return false;
        if (pipp != null ? !pipp.equals(that.pipp) : that.pipp != null) return false;
        if (termEligibility != null ? !termEligibility.equals(that.termEligibility) : that.termEligibility != null)
            return false;
        if (lstrtrndate != null ? !lstrtrndate.equals(that.lstrtrndate) : that.lstrtrndate != null) return false;
        if (pippProgPrice != null ? !pippProgPrice.equals(that.pippProgPrice) : that.pippProgPrice != null)
            return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
        if (allowance != null ? !allowance.equals(that.allowance) : that.allowance != null) return false;
        if (flist != null ? !flist.equals(that.flist) : that.flist != null) return false;
        if (jobber != null ? !jobber.equals(that.jobber) : that.jobber != null) return false;
        if (wd != null ? !wd.equals(that.wd) : that.wd != null) return false;
        if (prcchgdir != null ? !prcchgdir.equals(that.prcchgdir) : that.prcchgdir != null) return false;
        if (d2D != null ? !d2D.equals(that.d2D) : that.d2D != null) return false;
        if (d2DPrice != null ? !d2DPrice.equals(that.d2DPrice) : that.d2DPrice != null) return false;
        if (unitSellPack != null ? !unitSellPack.equals(that.unitSellPack) : that.unitSellPack != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (productLine != null ? !productLine.equals(that.productLine) : that.productLine != null) return false;
        if (marketProductLine != null ? !marketProductLine.equals(that.marketProductLine) : that.marketProductLine != null)
            return false;
        if (collision != null ? !collision.equals(that.collision) : that.collision != null) return false;
        if (lsd != null ? !lsd.equals(that.lsd) : that.lsd != null) return false;
        if (newpart != null ? !newpart.equals(that.newpart) : that.newpart != null) return false;
        if (natfltprc != null ? !natfltprc.equals(that.natfltprc) : that.natfltprc != null) return false;
        if (suppdirship != null ? !suppdirship.equals(that.suppdirship) : that.suppdirship != null) return false;
        if (pwrtrncomp != null ? !pwrtrncomp.equals(that.pwrtrncomp) : that.pwrtrncomp != null) return false;
        if (prtreldate != null ? !prtreldate.equals(that.prtreldate) : that.prtreldate != null) return false;
        if (sellpkqty != null ? !sellpkqty.equals(that.sellpkqty) : that.sellpkqty != null) return false;
        if (pdcType != null ? !pdcType.equals(that.pdcType) : that.pdcType != null) return false;
        if (doi != null ? !doi.equals(that.doi) : that.doi != null) return false;
        if (fltprc != null ? !fltprc.equals(that.fltprc) : that.fltprc != null) return false;
        if (govprc != null ? !govprc.equals(that.govprc) : that.govprc != null) return false;
        if (reman != null ? !reman.equals(that.reman) : that.reman != null) return false;
        if (wgt != null ? !wgt.equals(that.wgt) : that.wgt != null) return false;
        if (packingCode != null ? !packingCode.equals(that.packingCode) : that.packingCode != null) return false;
        if (msip != null ? !msip.equals(that.msip) : that.msip != null) return false;
        if (calcedQty != null ? !calcedQty.equals(that.calcedQty) : that.calcedQty != null) return false;
        if (pti != null ? !pti.equals(that.pti) : that.pti != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partNo != null ? partNo.hashCode() : 0;
        result = 31 * result + (tape != null ? tape.hashCode() : 0);
        result = 31 * result + (motorcraft != null ? motorcraft.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        result = 31 * result + (mcpre != null ? mcpre.hashCode() : 0);
        result = 31 * result + (mcbase != null ? mcbase.hashCode() : 0);
        result = 31 * result + (mcsuf != null ? mcsuf.hashCode() : 0);
        result = 31 * result + (replcg1 != null ? replcg1.hashCode() : 0);
        result = 31 * result + (replcg2 != null ? replcg2.hashCode() : 0);
        result = 31 * result + (fCost != null ? fCost.hashCode() : 0);
        result = 31 * result + (fcore != null ? fcore.hashCode() : 0);
        result = 31 * result + (pcValue != null ? pcValue.hashCode() : 0);
        result = 31 * result + (multQty != null ? multQty.hashCode() : 0);
        result = 31 * result + (obsstatus != null ? obsstatus.hashCode() : 0);
        result = 31 * result + (interchange != null ? interchange.hashCode() : 0);
        result = 31 * result + (pipp != null ? pipp.hashCode() : 0);
        result = 31 * result + (termEligibility != null ? termEligibility.hashCode() : 0);
        result = 31 * result + (lstrtrndate != null ? lstrtrndate.hashCode() : 0);
        result = 31 * result + (pippProgPrice != null ? pippProgPrice.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (allowance != null ? allowance.hashCode() : 0);
        result = 31 * result + (flist != null ? flist.hashCode() : 0);
        result = 31 * result + (jobber != null ? jobber.hashCode() : 0);
        result = 31 * result + (wd != null ? wd.hashCode() : 0);
        result = 31 * result + (prcchgdir != null ? prcchgdir.hashCode() : 0);
        result = 31 * result + (d2D != null ? d2D.hashCode() : 0);
        result = 31 * result + (d2DPrice != null ? d2DPrice.hashCode() : 0);
        result = 31 * result + (unitSellPack != null ? unitSellPack.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        result = 31 * result + (marketProductLine != null ? marketProductLine.hashCode() : 0);
        result = 31 * result + (collision != null ? collision.hashCode() : 0);
        result = 31 * result + (lsd != null ? lsd.hashCode() : 0);
        result = 31 * result + (newpart != null ? newpart.hashCode() : 0);
        result = 31 * result + (natfltprc != null ? natfltprc.hashCode() : 0);
        result = 31 * result + (suppdirship != null ? suppdirship.hashCode() : 0);
        result = 31 * result + (pwrtrncomp != null ? pwrtrncomp.hashCode() : 0);
        result = 31 * result + (prtreldate != null ? prtreldate.hashCode() : 0);
        result = 31 * result + (sellpkqty != null ? sellpkqty.hashCode() : 0);
        result = 31 * result + (pdcType != null ? pdcType.hashCode() : 0);
        result = 31 * result + (doi != null ? doi.hashCode() : 0);
        result = 31 * result + (fltprc != null ? fltprc.hashCode() : 0);
        result = 31 * result + (govprc != null ? govprc.hashCode() : 0);
        result = 31 * result + (reman != null ? reman.hashCode() : 0);
        result = 31 * result + (wgt != null ? wgt.hashCode() : 0);
        result = 31 * result + (packingCode != null ? packingCode.hashCode() : 0);
        result = 31 * result + (msip != null ? msip.hashCode() : 0);
        result = 31 * result + (calcedQty != null ? calcedQty.hashCode() : 0);
        result = 31 * result + (pti != null ? pti.hashCode() : 0);
        return result;
    }
}
