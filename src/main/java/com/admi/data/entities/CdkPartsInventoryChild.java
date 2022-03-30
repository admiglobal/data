package com.admi.data.entities;

import com.admi.data.entities.keys.CdkInventoryPK;
import com.admi.data.services.CdkImportService;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PartsInventory", namespace = "http://www.dmotorworks.com/pip-extract-parts")
@Entity
@Table(name = "CDK_PARTS_INVENTORY", schema = "ADMI")
@IdClass(CdkInventoryPK.class)
//@XmlRootElement(name = "PartsInventory")
public class CdkPartsInventoryChild implements Serializable {

	private String dealerId;

	private LocalDate inventoryDate;

	@JacksonXmlProperty(localName = "HostItemID")
	private String hostItemId;

	@JacksonXmlProperty(localName = "PartNumber")
	private String partNumber;

	@JacksonXmlProperty(localName = "Class")
	private String clazz;

	@JacksonXmlProperty(localName = "Description")
	private String description;

	@JacksonXmlProperty(localName = "BOPCK")
	private String bopck;

	@JacksonXmlProperty(localName = "Source")
	private String source;

//	@JacksonXmlProperty(localName = "SortKey1")
//	private String sortKey1;
//
//	@JacksonXmlProperty(localName = "SortKey2")
//	private String sortKey2;
//
//	@JacksonXmlProperty(localName = "SortKey3")
//	private String sortKey3;

	@JacksonXmlProperty(localName = "Pack")
	private Integer pack;

	@JacksonXmlProperty(localName = "Multiple")
	private Integer multiple;

	@JacksonXmlProperty(localName = "PriceUnit")
	private Integer priceUnit;

	@JacksonXmlProperty(localName = "PackMultipleFlag")
	private String packMultipleFlag;

	@JacksonXmlProperty(localName = "Cost")
	private Double cost;

	@JacksonXmlProperty(localName = "List")
	private Double list;

	@JacksonXmlProperty(localName = "Trade")
	private Double trade;

	@JacksonXmlProperty(localName = "Comp")
	private Double comp;

	@JacksonXmlProperty(localName = "Exchange")
	private Double exchange;

	@JacksonXmlProperty(localName = "ABCD")
	private String abcd;

	@JacksonXmlProperty(localName = "ArrivedFlag")
	private String arrivedFlag;

	@JacksonXmlProperty(localName = "SeasonCode1")
	private String seasonCode1;

	@JacksonXmlProperty(localName = "SeasonCode2")
	private String seasonCode2;

	@JacksonXmlProperty(localName = "SeasonCode3")
	private String seasonCode3;

	@JacksonXmlProperty(localName = "SeasonCode4")
	private String seasonCode4;

	@JacksonXmlProperty(localName = "Price6")
	private Double price6;

	@JacksonXmlProperty(localName = "Price7")
	private Double price7;

	@JacksonXmlProperty(localName = "PNC")
	private String pnc;

	@JacksonXmlProperty(localName = "RBCode")
	private String rbCode;

	@JacksonXmlProperty(localName = "ExtraDS")
	private Integer extraDs;

	@JacksonXmlProperty(localName = "UpdateCode")
	private String updateCode;

	@JacksonXmlProperty(localName = "CommentFlag")
	private String commentFlag;

	@JacksonXmlProperty(localName = "Appreciation")
	private Double appreciation;

	@JacksonXmlProperty(localName = "OldOnHand")
	private Integer oldOnHand;

	@JacksonXmlProperty(localName = "NewOrderQty")
	private Integer newOrderQty;

	@JacksonXmlProperty(localName = "Review")
	private String review;

	@JacksonXmlProperty(localName = "Bin1")
	private String bin1;

	@JacksonXmlProperty(localName = "Bin2")
	private String bin2;

	@JacksonXmlProperty(localName = "Bin3")
	private String bin3;

	@JacksonXmlProperty(localName = "OnHand")
	private Integer onHand;

	@JacksonXmlProperty(localName = "MemoPartFlag")
	private String memoPartFlag;

	@JacksonXmlProperty(localName = "DeletePartFlag")
	private String deletePartFlag;

	@JacksonXmlProperty(localName = "PerJob")
	private Integer perJob;

	@JacksonXmlProperty(localName = "BRP")
	private Integer brp;

	@JacksonXmlProperty(localName = "BSL")
	private Integer bsl;

	@JacksonXmlProperty(localName = "FullBin")
	private Integer fullBin;

	@JacksonXmlProperty(localName = "SpecialStatus")
	private String specialStatus;

	@JacksonXmlProperty(localName = "DateAdded")
	private String dateAdded;

	@JacksonXmlProperty(localName = "LastCountDate")
	private String lastCountDate;

	@JacksonXmlProperty(localName = "Price8")
	private Double price8;

	@JacksonXmlProperty(localName = "Price9")
	private Double price9;

	@JacksonXmlProperty(localName = "Price10")
	private Double price10;

	@JacksonXmlProperty(localName = "MonthsNoReceipt")
	private Integer monthsNoReceipt;

	@JacksonXmlProperty(localName = "MonthsNoSale")
	private Integer monthsNoSale;

	@JacksonXmlProperty(localName = "JanSales")
	private Integer janSales;

	@JacksonXmlProperty(localName = "JanSales2")
	private Integer janSales2;

	@JacksonXmlProperty(localName = "JanSales3")
	private Integer janSales3;

	@JacksonXmlProperty(localName = "FebSales")
	private Integer febSales;

	@JacksonXmlProperty(localName = "FebSales2")
	private Integer febSales2;

	@JacksonXmlProperty(localName = "FebSales3")
	private Integer febSales3;

	@JacksonXmlProperty(localName = "MarSales")
	private Integer marSales;

	@JacksonXmlProperty(localName = "MarSales2")
	private Integer marSales2;

	@JacksonXmlProperty(localName = "MarSales3")
	private Integer marSales3;

	@JacksonXmlProperty(localName = "AprSales")
	private Integer aprSales;

	@JacksonXmlProperty(localName = "AprSales2")
	private Integer aprSales2;

	@JacksonXmlProperty(localName = "AprSales3")
	private Integer aprSales3;

	@JacksonXmlProperty(localName = "MaySales")
	private Integer maySales;

	@JacksonXmlProperty(localName = "MaySales2")
	private Integer maySales2;

	@JacksonXmlProperty(localName = "MaySales3")
	private Integer maySales3;

	@JacksonXmlProperty(localName = "JunSales")
	private Integer junSales;

	@JacksonXmlProperty(localName = "JunSales2")
	private Integer junSales2;

	@JacksonXmlProperty(localName = "JunSales3")
	private Integer junSales3;

	@JacksonXmlProperty(localName = "JulSales")
	private Integer julSales;

	@JacksonXmlProperty(localName = "JulSales2")
	private Integer julSales2;

	@JacksonXmlProperty(localName = "JulSales3")
	private Integer julSales3;

	@JacksonXmlProperty(localName = "AugSales")
	private Integer augSales;

	@JacksonXmlProperty(localName = "AugSales2")
	private Integer augSales2;

	@JacksonXmlProperty(localName = "AugSales3")
	private Integer augSales3;

	@JacksonXmlProperty(localName = "SepSales")
	private Integer sepSales;

	@JacksonXmlProperty(localName = "SepSales2")
	private Integer sepSales2;

	@JacksonXmlProperty(localName = "SepSales3")
	private Integer sepSales3;

	@JacksonXmlProperty(localName = "OctSales")
	private Integer octSales;

	@JacksonXmlProperty(localName = "OctSales2")
	private Integer octSales2;

	@JacksonXmlProperty(localName = "OctSales3")
	private Integer octSales3;

	@JacksonXmlProperty(localName = "NovSales")
	private Integer novSales;

	@JacksonXmlProperty(localName = "NovSales2")
	private Integer novSales2;

	@JacksonXmlProperty(localName = "NovSales3")
	private Integer novSales3;

	@JacksonXmlProperty(localName = "DecSales")
	private Integer decSales;

	@JacksonXmlProperty(localName = "DecSales2")
	private Integer decSales2;

	@JacksonXmlProperty(localName = "DecSales3")
	private Integer decSales3;

	@JacksonXmlProperty(localName = "Month13Sales")
	private Integer month13Sales;

	@JacksonXmlProperty(localName = "Month14Sales")
	private Integer month14Sales;

	@JacksonXmlProperty(localName = "LastTransDate")
	private String lastTransDate;

	@JacksonXmlProperty(localName = "BaseCostLIFO")
	private Double baseCostLifo;

	@JacksonXmlProperty(localName = "BegQtyLIFO")
	private Integer begQtyLifo;

	@JacksonXmlProperty(localName = "MTDEmerPurchase")
	private Integer mtdEmerPurchase;

	@JacksonXmlProperty(localName = "MTDPlusAdj")
	private Integer mtdPlusAdj;

	@JacksonXmlProperty(localName = "MTDMinusAdj")
	private Integer mtdMinusAdj;

	@JacksonXmlProperty(localName = "MTDLostSales")
	private Integer mtdLostSales;

	@JacksonXmlProperty(localName = "ExcessQty")
	private Integer excessQty;

	@JacksonXmlProperty(localName = "Comment")
	private String comment;

	@JacksonXmlProperty(localName = "Misc")
	private String misc;

	@JacksonXmlProperty(localName = "UnitWeight")
	private Double unitWeight;

	@JacksonXmlProperty(localName = "CoreMatrix")
	private String coreMatrix;

	@JacksonXmlProperty(localName = "CoreSerialized")
	private String coreSerialized;

	@JacksonXmlProperty(localName = "DirtyBin1")
	private String dirtyBin1;

	@JacksonXmlProperty(localName = "DirtyOnHand")
	private Integer dirtyOnHand;

	@JacksonXmlProperty(localName = "Manufacturer")
	private String manufacturer;

	@JacksonXmlProperty(localName = "AccountingAccount")
	private String accountingAccount;

	@JacksonXmlProperty(localName = "SeasonCode5")
	private String seasonCode5;

	@JacksonXmlProperty(localName = "SeasonCode6")
	private String seasonCode6;

	@JacksonXmlProperty(localName = "ABCXYZ")
	private String abcxyz;

	@JacksonXmlProperty(localName = "Cntr")
	private String cntr;

	@JacksonXmlProperty(localName = "PriorYearSales1")
	private Integer priorYearSales1;

	@JacksonXmlProperty(localName = "PriorYearSales2")
	private Integer priorYearSales2;

	@JacksonXmlProperty(localName = "PriorYearSales3")
	private Integer priorYearSales3;

	@JacksonXmlProperty(localName = "PreSoldQuantity")
	private Integer preSoldQuantity;

	@JacksonXmlProperty(localName = "RequestedQuantity")
	private Integer requestedQuantity;

	@JacksonXmlProperty(localName = "DateLastReceipted")
	private String dateLastReceipted;

	@JacksonXmlProperty(localName = "ErrorLevel")
	private String errorLevel;

	@JacksonXmlProperty(localName = "ErrorMessage")
	private String errorMessage;

	@JacksonXmlProperty(localName = "LastSaleDate")
	private String lastSaleDate;

	@JacksonXmlProperty(localName = "OnOrderQty")
	private Integer onOrderQty;

	@JacksonXmlProperty(localName = "MFGControlled")
	private Integer mfgControlled;

	@JacksonXmlProperty(localName = "GuaranteesQuantity")
	private Integer guaranteesQuantity;

	@Id
	@Column(name = "DEALER_ID", nullable = false, length = 10)
	public String getDealerId() { return dealerId; }

	public void setDealerId(String dealerId) { this.dealerId = dealerId; }

	@Id
	@Column(name = "HOST_ITEM_ID", nullable = false, length = 40)
	public String getHostItemId() { return hostItemId; }

	public void setHostItemId(String hostItemId) { this.hostItemId = hostItemId; }

	@Id
	@Column(name = "INVENTORY_DATE", nullable = false)
	public LocalDate getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	@Basic
	@Column(name = "PART_NUMBER", nullable = true, length = 25)
	public String getPartNumber() { return partNumber; }

	public void setPartNumber(String partNumber) { this.partNumber = partNumber; }

	@Basic
	@Column(name = "CLAZZ", nullable = true, length = 8)
	public String getClazz() { return clazz; }

	public void setClazz(String clazz) { this.clazz = clazz; }

	@Basic
	@Column(name = "DESCRIPTION", nullable = true, length = 40)
	public String getDescription() { return description; }

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "BOPCK", nullable = true, length = 20)
	public String getBopck() {
		return bopck;
	}

	public void setBopck(String bopck) {
		this.bopck = bopck;
	}

	@Basic
	@Column(name = "SOURCE", nullable = true, length = 4)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

//	@Basic
//	@Column(name = "SORT_KEY_1", nullable = true, length = 15)
//	public String getSortKey1() { return sortKey1; }
//
//	public void setSortKey1(String sortKey1) {
//		this.sortKey1 = sortKey1;
//	}
//
//	@Basic
//	@Column(name = "SORT_KEY_2", nullable = true, length = 15)
//	public String getSortKey2() {
//		return sortKey2;
//	}
//
//	public void setSortKey2(String sortKey2) {
//		this.sortKey2 = sortKey2;
//	}
//
//	@Basic
//	@Column(name = "SORT_KEY_3", nullable = true, length = 15)
//	public String getSortKey3() {
//		return sortKey3;
//	}
//
//	public void setSortKey3(String sortKey3) {
//		this.sortKey3 = sortKey3;
//	}

	@Basic
	@Column(name = "PACK", nullable = true, precision = 0)
	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}

	@Basic
	@Column(name = "MULTIPLE", nullable = true, precision = 0)
	public Integer getMultiple() { return multiple; }

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	@Basic
	@Column(name = "PRICE_UNIT", nullable = true, precision = 0)
	public Integer getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Integer priceUnit) {
		this.priceUnit = priceUnit;
	}

	@Basic
	@Column(name = "PACK_MULTIPLE_FLAG", nullable = true, length = 1)
	public String getPackMultipleFlag() { return packMultipleFlag; }

	public void setPackMultipleFlag(String packMultipleFlag) {
		this.packMultipleFlag = packMultipleFlag;
	}

	@Basic
	@Column(name = "COST_", nullable = true, precision = 2)
	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Basic
	@Column(name = "LIST_", nullable = true, precision = 2)
	public Double getList() {
		return list;
	}

	public void setList(Double list) {
		this.list = list;
	}

	@Basic
	@Column(name = "TRADE", nullable = true, precision = 2)
	public Double getTrade() {
		return trade;
	}

	public void setTrade(Double trade) {
		this.trade = trade;
	}

	@Basic
	@Column(name = "COMP", nullable = true, precision = 2)
	public Double getComp() {
		return comp;
	}

	public void setComp(Double comp) {
		this.comp = comp;
	}

	@Basic
	@Column(name = "EXCHANGE", nullable = true, precision = 2)
	public Double getExchange() { return exchange; }

	public void setExchange(Double exchange) {
		this.exchange = exchange;
	}

	@Basic
	@Column(name = "ABCD", nullable = true, length = 12)
	public String getAbcd() {
		return abcd;
	}

	public void setAbcd(String abcd) {
		this.abcd = abcd;
	}

	@Basic
	@Column(name = "ARRIVED_FLAG", nullable = true, length = 1)
	public String getArrivedFlag() { return arrivedFlag; }

	public void setArrivedFlag(String arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}

	@Basic
	@Column(name = "SEASON_CODE_1", nullable = true, length = 3)
	public String getSeasonCode1() {
		return seasonCode1;
	}

	public void setSeasonCode1(String seasonCode1) {
		this.seasonCode1 = seasonCode1;
	}

	@Basic
	@Column(name = "SEASON_CODE_2", nullable = true, length = 3)
	public String getSeasonCode2() {
		return seasonCode2;
	}

	public void setSeasonCode2(String seasonCode2) {
		this.seasonCode2 = seasonCode2;
	}

	@Basic
	@Column(name = "SEASON_CODE_3", nullable = true, length = 3)
	public String getSeasonCode3() {
		return seasonCode3;
	}

	public void setSeasonCode3(String seasonCode3) {
		this.seasonCode3 = seasonCode3;
	}

	@Basic
	@Column(name = "SEASON_CODE_4", nullable = true, length = 3)
	public String getSeasonCode4() {
		return seasonCode4;
	}

	public void setSeasonCode4(String seasonCode4) { this.seasonCode4 = seasonCode4; }

	@Basic
	@Column(name = "PRICE_6", nullable = true, precision = 2)
	public Double getPrice6() { return price6; }

	public void setPrice6(Double price6) {
		this.price6 = price6;
	}

	@Basic
	@Column(name = "PRICE_7", nullable = true, precision = 2)
	public Double getPrice7() {
		return price7;
	}

	public void setPrice7(Double price7) {
		this.price7 = price7;
	}

	@Basic
	@Column(name = "PNC", nullable = true, length = 25)
	public String getPnc() {
		return pnc;
	}

	public void setPnc(String pnc) {
		this.pnc = pnc;
	}

	@Basic
	@Column(name = "RB_CODE", nullable = true, length = 5)
	public String getRbCode() {
		return rbCode;
	}

	public void setRbCode(String rbCode) {
		this.rbCode = rbCode;
	}

	@Basic
	@Column(name = "EXTRA_DS", nullable = true, precision = 0)
	public Integer getExtraDs() {
		return extraDs;
	}

	public void setExtraDs(Integer extraDs) {
		this.extraDs = extraDs;
	}

	@Basic
	@Column(name = "UPDATE_CODE", nullable = true, length = 2)
	public String getUpdateCode() {
		return updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	@Basic
	@Column(name = "COMMENT_FLAG", nullable = true, length = 1)
	public String getCommentFlag() {
		return commentFlag;
	}

	public void setCommentFlag(String commentFlag) {
		this.commentFlag = commentFlag;
	}

	@Basic
	@Column(name = "APPRECIATION", nullable = true, precision = 2)
	public Double getAppreciation() {
		return appreciation;
	}

	public void setAppreciation(Double appreciation) {
		this.appreciation = appreciation;
	}

	@Basic
	@Column(name = "OLD_ON_HAND", nullable = true, precision = 0)
	public Integer getOldOnHand() {
		return oldOnHand;
	}

	public void setOldOnHand(Integer oldOnHand) {
		this.oldOnHand = oldOnHand;
	}

	@Basic
	@Column(name = "NEW_ORDER_QTY", nullable = true, precision = 0)
	public Integer getNewOrderQty() {
		return newOrderQty;
	}

	public void setNewOrderQty(Integer newOrderQty) {
		this.newOrderQty = newOrderQty;
	}

	@Basic
	@Column(name = "REVIEW", nullable = true, length = 4)
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Basic
	@Column(name = "BIN_1", nullable = true, length = 8)
	public String getBin1() {
		return bin1;
	}

	public void setBin1(String bin1) {
		this.bin1 = bin1;
	}

	@Basic
	@Column(name = "BIN_2", nullable = true, length = 8)
	public String getBin2() {
		return bin2;
	}

	public void setBin2(String bin2) {
		this.bin2 = bin2;
	}

	@Basic
	@Column(name = "BIN_3", nullable = true, length = 8)
	public String getBin3() {
		return bin3;
	}

	public void setBin3(String bin3) {
		this.bin3 = bin3;
	}

	@Basic
	@Column(name = "ON_HAND", nullable = true, precision = 0)
	public Integer getOnHand() {
		return onHand;
	}

	public void setOnHand(Integer onHand) {
		this.onHand = onHand;
	}

	@Basic
	@Column(name = "MEMO_PART_FLAG", nullable = true, length = 1)
	public String getMemoPartFlag() { return memoPartFlag; }

	public void setMemoPartFlag(String memoPartFlag) {
		this.memoPartFlag = memoPartFlag;
	}

	@Basic
	@Column(name = "DELETE_PART_FLAG", nullable = true, length = 1)
	public String getDeletePartFlag() { return deletePartFlag; }

	public void setDeletePartFlag(String deletePartFlag) {
		this.deletePartFlag = deletePartFlag;
	}

	@Basic
	@Column(name = "PER_JOB", nullable = true, precision = 0)
	public Integer getPerJob() {
		return perJob;
	}

	public void setPerJob(Integer perJob) {
		this.perJob = perJob;
	}

	@Basic
	@Column(name = "BRP", nullable = true, precision = 0)
	public Integer getBrp() {
		return brp;
	}

	public void setBrp(Integer brp) {
		this.brp = brp;
	}

	@Basic
	@Column(name = "BSL", nullable = true, precision = 0)
	public Integer getBsl() {
		return bsl;
	}

	public void setBsl(Integer bsl) {
		this.bsl = bsl;
	}

	@Basic
	@Column(name = "FULL_BIN", nullable = true, precision = 0)
	public Integer getFullBin() {
		return fullBin;
	}

	public void setFullBin(Integer fullBin) {
		this.fullBin = fullBin;
	}

	@Basic
	@Column(name = "SPECIAL_STATUS", nullable = true, length = 3)
	public String getSpecialStatus() {
		return specialStatus;
	}

	public void setSpecialStatus(String specialStatus) {
		this.specialStatus = specialStatus;
	}

	@Basic
	@Column(name = "DATE_ADDED", nullable = true)
	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Basic
	@Column(name = "LAST_COUNT_DATE", nullable = true)
	public String getLastCountDate() {
		return lastCountDate;
	}

	public void setLastCountDate(String lastCountDate) {
		this.lastCountDate = lastCountDate;
	}

	@Basic
	@Column(name = "PRICE_8", nullable = true, precision = 2)
	public Double getPrice8() {
		return price8;
	}

	public void setPrice8(Double price8) {
		this.price8 = price8;
	}

	@Basic
	@Column(name = "PRICE_9", nullable = true, precision = 2)
	public Double getPrice9() {
		return price9;
	}

	public void setPrice9(Double price9) {
		this.price9 = price9;
	}

	@Basic
	@Column(name = "PRICE_10", nullable = true, precision = 2)
	public Double getPrice10() {
		return price10;
	}

	public void setPrice10(Double price10) {
		this.price10 = price10;
	}

	@Basic
	@Column(name = "MONTHS_NO_RECEIPT", nullable = true, precision = 0)
	public Integer getMonthsNoReceipt() {
		return monthsNoReceipt;
	}

	public void setMonthsNoReceipt(Integer monthsNoReceipt) {
		this.monthsNoReceipt = monthsNoReceipt;
	}

	@Basic
	@Column(name = "MONTHS_NO_SALE", nullable = true, precision = 0)
	public Integer getMonthsNoSale() {
		return monthsNoSale;
	}

	public void setMonthsNoSale(Integer monthsNoSale) {
		this.monthsNoSale = monthsNoSale;
	}

	@Basic
	@Column(name = "JAN_SALES", nullable = true, precision = 0)
	public Integer getJanSales() {
		return janSales;
	}

	public void setJanSales(Integer janSales) {
		this.janSales = janSales;
	}

	@Basic
	@Column(name = "JAN_SALES2", nullable = true, precision = 0)
	public Integer getJanSales2() {
		return janSales2;
	}

	public void setJanSales2(Integer janSales2) {
		this.janSales2 = janSales2;
	}

	@Basic
	@Column(name = "JAN_SALES3", nullable = true, precision = 0)
	public Integer getJanSales3() {
		return janSales3;
	}

	public void setJanSales3(Integer janSales3) {
		this.janSales3 = janSales3;
	}

	@Basic
	@Column(name = "FEB_SALES", nullable = true, precision = 0)
	public Integer getFebSales() {
		return febSales;
	}

	public void setFebSales(Integer febSales) {
		this.febSales = febSales;
	}

	@Basic
	@Column(name = "FEB_SALES2", nullable = true, precision = 0)
	public Integer getFebSales2() {
		return febSales2;
	}

	public void setFebSales2(Integer febSales2) {
		this.febSales2 = febSales2;
	}

	@Basic
	@Column(name = "FEB_SALES3", nullable = true, precision = 0)
	public Integer getFebSales3() {
		return febSales3;
	}

	public void setFebSales3(Integer febSales3) {
		this.febSales3 = febSales3;
	}

	@Basic
	@Column(name = "MAR_SALES", nullable = true, precision = 0)
	public Integer getMarSales() {
		return marSales;
	}

	public void setMarSales(Integer marSales) {
		this.marSales = marSales;
	}

	@Basic
	@Column(name = "MAR_SALES2", nullable = true, precision = 0)
	public Integer getMarSales2() {
		return marSales2;
	}

	public void setMarSales2(Integer marSales2) {
		this.marSales2 = marSales2;
	}

	@Basic
	@Column(name = "MAR_SALES3", nullable = true, precision = 0)
	public Integer getMarSales3() {
		return marSales3;
	}

	public void setMarSales3(Integer marSales3) {
		this.marSales3 = marSales3;
	}

	@Basic
	@Column(name = "APR_SALES", nullable = true, precision = 0)
	public Integer getAprSales() {
		return aprSales;
	}

	public void setAprSales(Integer aprSales) {
		this.aprSales = aprSales;
	}

	@Basic
	@Column(name = "APR_SALES2", nullable = true, precision = 0)
	public Integer getAprSales2() {
		return aprSales2;
	}

	public void setAprSales2(Integer aprSales2) {
		this.aprSales2 = aprSales2;
	}

	@Basic
	@Column(name = "APR_SALES3", nullable = true, precision = 0)
	public Integer getAprSales3() {
		return aprSales3;
	}

	public void setAprSales3(Integer aprSales3) {
		this.aprSales3 = aprSales3;
	}

	@Basic
	@Column(name = "MAY_SALES", nullable = true, precision = 0)
	public Integer getMaySales() {
		return maySales;
	}

	public void setMaySales(Integer maySales) {
		this.maySales = maySales;
	}

	@Basic
	@Column(name = "MAY_SALES2", nullable = true, precision = 0)
	public Integer getMaySales2() {
		return maySales2;
	}

	public void setMaySales2(Integer maySales2) {
		this.maySales2 = maySales2;
	}

	@Basic
	@Column(name = "MAY_SALES3", nullable = true, precision = 0)
	public Integer getMaySales3() {
		return maySales3;
	}

	public void setMaySales3(Integer maySales3) {
		this.maySales3 = maySales3;
	}

	@Basic
	@Column(name = "JUN_SALES", nullable = true, precision = 0)
	public Integer getJunSales() {
		return junSales;
	}

	public void setJunSales(Integer junSales) {
		this.junSales = junSales;
	}

	@Basic
	@Column(name = "JUN_SALES2", nullable = true, precision = 0)
	public Integer getJunSales2() { return junSales2; }

	public void setJunSales2(Integer junSales2) {
		this.junSales2 = junSales2;
	}

	@Basic
	@Column(name = "JUN_SALES3", nullable = true, precision = 0)
	public Integer getJunSales3() {
		return junSales3;
	}

	public void setJunSales3(Integer junSales3) {
		this.junSales3 = junSales3;
	}

	@Basic
	@Column(name = "JUL_SALES", nullable = true, precision = 0)
	public Integer getJulSales() {
		return julSales;
	}

	public void setJulSales(Integer julSales) {
		this.julSales = julSales;
	}

	@Basic
	@Column(name = "JUL_SALES2", nullable = true, precision = 0)
	public Integer getJulSales2() { return julSales2; }

	public void setJulSales2(Integer julSales2) {
		this.julSales2 = julSales2;
	}

	@Basic
	@Column(name = "JUL_SALES3", nullable = true, precision = 0)
	public Integer getJulSales3() {
		return julSales3;
	}

	public void setJulSales3(Integer julSales3) {
		this.julSales3 = julSales3;
	}

	@Basic
	@Column(name = "AUG_SALES", nullable = true, precision = 0)
	public Integer getAugSales() {
		return augSales;
	}

	public void setAugSales(Integer augSales) {
		this.augSales = augSales;
	}

	@Basic
	@Column(name = "AUG_SALES2", nullable = true, precision = 0)
	public Integer getAugSales2() { return augSales2; }

	public void setAugSales2(Integer augSales2) {
		this.augSales2 = augSales2;
	}

	@Basic
	@Column(name = "AUG_SALES3", nullable = true, precision = 0)
	public Integer getAugSales3() {
		return augSales3;
	}

	public void setAugSales3(Integer augSales3) {
		this.augSales3 = augSales3;
	}

	@Basic
	@Column(name = "SEP_SALES", nullable = true, precision = 0)
	public Integer getSepSales() {
		return sepSales;
	}

	public void setSepSales(Integer sepSales) {
		this.sepSales = sepSales;
	}

	@Basic
	@Column(name = "SEP_SALES2", nullable = true, precision = 0)
	public Integer getSepSales2() { return sepSales2; }

	public void setSepSales2(Integer sepSales2) {
		this.sepSales2 = sepSales2;
	}

	@Basic
	@Column(name = "SEP_SALES3", nullable = true, precision = 0)
	public Integer getSepSales3() {
		return sepSales3;
	}

	public void setSepSales3(Integer sepSales3) {
		this.sepSales3 = sepSales3;
	}

	@Basic
	@Column(name = "OCT_SALES", nullable = true, precision = 0)
	public Integer getOctSales() {
		return octSales;
	}

	public void setOctSales(Integer octSales) {
		this.octSales = octSales;
	}

	@Basic
	@Column(name = "OCT_SALES2", nullable = true, precision = 0)
	public Integer getOctSales2() {return octSales2; }

	public void setOctSales2(Integer octSales2) {
		this.octSales2 = octSales2;
	}

	@Basic
	@Column(name = "OCT_SALES3", nullable = true, precision = 0)
	public Integer getOctSales3() {
		return octSales3;
	}

	public void setOctSales3(Integer octSales3) {
		this.octSales3 = octSales3;
	}

	@Basic
	@Column(name = "NOV_SALES", nullable = true, precision = 0)
	public Integer getNovSales() {
		return novSales;
	}

	public void setNovSales(Integer novSales) {
		this.novSales = novSales;
	}

	@Basic
	@Column(name = "NOV_SALES2", nullable = true, precision = 0)
	public Integer getNovSales2() { return novSales2; }

	public void setNovSales2(Integer novSales2) {
		this.novSales2 = novSales2;
	}

	@Basic
	@Column(name = "NOV_SALES3", nullable = true, precision = 0)
	public Integer getNovSales3() {
		return novSales3;
	}

	public void setNovSales3(Integer novSales3) {
		this.novSales3 = novSales3;
	}

	@Basic
	@Column(name = "DEC_SALES", nullable = true, precision = 0)
	public Integer getDecSales() {
		return decSales;
	}

	public void setDecSales(Integer decSales) {
		this.decSales = decSales;
	}

	@Basic
	@Column(name = "DEC_SALES2", nullable = true, precision = 0)
	public Integer getDecSales2() { return decSales2; }

	public void setDecSales2(Integer decSales2) {
		this.decSales2 = decSales2;
	}

	@Basic
	@Column(name = "DEC_SALES3", nullable = true, precision = 0)
	public Integer getDecSales3() {
		return decSales3;
	}

	public void setDecSales3(Integer decSales3) {
		this.decSales3 = decSales3;
	}

	@Basic
	@Column(name = "MONTH_13_SALES", nullable = true, precision = 0)
	public Integer getMonth13Sales() {
		return month13Sales;
	}

	public void setMonth13Sales(Integer month13Sales) {
		this.month13Sales = month13Sales;
	}

	@Basic
	@Column(name = "MONTH_14_SALES", nullable = true, precision = 0)
	public Integer getMonth14Sales() {
		return month14Sales;
	}

	public void setMonth14Sales(Integer month14Sales) {
		this.month14Sales = month14Sales;
	}

	@Basic
	@Column(name = "LAST_TRANS_DATE", nullable = true)
	public String getLastTransDate() {
		return lastTransDate;
	}

	public void setLastTransDate(String lastTransDate) {
		this.lastTransDate = lastTransDate;
	}

	@Basic
	@Column(name = "BASE_COST_LIFO", nullable = true, precision = 2)
	public Double getBaseCostLifo() {
		return baseCostLifo;
	}

	public void setBaseCostLifo(Double baseCostLifo) {
		this.baseCostLifo = baseCostLifo;
	}

	@Basic
	@Column(name = "BEG_QTY_LIFO", nullable = true, precision = 0)
	public Integer getBegQtyLifo() {
		return begQtyLifo;
	}

	public void setBegQtyLifo(Integer begQtyLifo) {
		this.begQtyLifo = begQtyLifo;
	}

	@Basic
	@Column(name = "MTD_EMER_PURCHASE", nullable = true, precision = 0)
	public Integer getMtdEmerPurchase() {
		return mtdEmerPurchase;
	}

	public void setMtdEmerPurchase(Integer mtdEmerPurchase) {
		this.mtdEmerPurchase = mtdEmerPurchase;
	}

	@Basic
	@Column(name = "MTD_PLUS_ADJ", nullable = true, precision = 0)
	public Integer getMtdPlusAdj() {
		return mtdPlusAdj;
	}

	public void setMtdPlusAdj(Integer mtdPlusAdj) {
		this.mtdPlusAdj = mtdPlusAdj;
	}

	@Basic
	@Column(name = "MTD_MINUS_ADJ", nullable = true, precision = 0)
	public Integer getMtdMinusAdj() {
		return mtdMinusAdj;
	}

	public void setMtdMinusAdj(Integer mtdMinusAdj) {
		this.mtdMinusAdj = mtdMinusAdj;
	}

	@Basic
	@Column(name = "MTD_LOST_SALES", nullable = true, precision = 0)
	public Integer getMtdLostSales() {
		return mtdLostSales;
	}

	public void setMtdLostSales(Integer mtdLostSales) {
		this.mtdLostSales = mtdLostSales;
	}

	@Basic
	@Column(name = "EXCESS_QTY", nullable = true, precision = 0)
	public Integer getExcessQty() {
		return excessQty;
	}

	public void setExcessQty(Integer excessQty) {
		this.excessQty = excessQty;
	}

	@Basic
	@Column(name = "COMMENT_", nullable = true, length = 99)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Basic
	@Column(name = "MISC", nullable = true, length = 8)
	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	@Basic
	@Column(name = "UNIT_WEIGHT", nullable = true, precision = 2)
	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	@Basic
	@Column(name = "CORE_MATRIX", nullable = true, length = 15)
	public String getCoreMatrix() {
		return coreMatrix;
	}

	public void setCoreMatrix(String coreMatrix) {
		this.coreMatrix = coreMatrix;
	}

	@Basic
	@Column(name = "CORE_SERIALIZED", nullable = true, length = 1)
	public String getCoreSerialized() {
		return coreSerialized;
	}

	public void setCoreSerialized(String coreSerialized) {
		this.coreSerialized = coreSerialized;
	}

	@Basic
	@Column(name = "DIRTY_BIN_1", nullable = true, length = 8)
	public String getDirtyBin1() {
		return dirtyBin1;
	}

	public void setDirtyBin1(String dirtyBin1) {
		this.dirtyBin1 = dirtyBin1;
	}

	@Basic
	@Column(name = "DIRTY_ON_HAND", nullable = true, precision = 0)
	public Integer getDirtyOnHand() {
		return dirtyOnHand;
	}

	public void setDirtyOnHand(Integer dirtyOnHand) {
		this.dirtyOnHand = dirtyOnHand;
	}

	@Basic
	@Column(name = "MANUFACTURER", nullable = true, length = 2)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Basic
	@Column(name = "ACCOUNTING_ACCOUNT", nullable = true, length = 17)
	public String getAccountingAccount() {
		return accountingAccount;
	}

	public void setAccountingAccount(String accountingAccount) {
		this.accountingAccount = accountingAccount;
	}

	@Basic
	@Column(name = "SEASON_CODE_5", nullable = true, length = 3)
	public String getSeasonCode5() {
		return seasonCode5;
	}

	public void setSeasonCode5(String seasonCode5) {
		this.seasonCode5 = seasonCode5;
	}

	@Basic
	@Column(name = "SEASON_CODE_6", nullable = true, length = 3)
	public String getSeasonCode6() {
		return seasonCode6;
	}

	public void setSeasonCode6(String seasonCode6) {
		this.seasonCode6 = seasonCode6;
	}

	@Basic
	@Column(name = "ABCXYZ", nullable = true, length = 2)
	public String getAbcxyz() {
		return abcxyz;
	}

	public void setAbcxyz(String abcxyz) {
		this.abcxyz = abcxyz;
	}

	@Basic
	@Column(name = "CNTR", nullable = true, length = 4)
	public String getCntr() {
		return cntr;
	}

	public void setCntr(String cntr) {
		this.cntr = cntr;
	}

	@Basic
	@Column(name = "PRIOR_YEAR_SALES_1", nullable = true, precision = 0)
	public Integer getPriorYearSales1() {
		return priorYearSales1;
	}

	public void setPriorYearSales1(Integer priorYearSales1) {
		this.priorYearSales1 = priorYearSales1;
	}

	@Basic
	@Column(name = "PRIOR_YEAR_SALES_2", nullable = true, precision = 0)
	public Integer getPriorYearSales2() {
		return priorYearSales2;
	}

	public void setPriorYearSales2(Integer priorYearSales2) {
		this.priorYearSales2 = priorYearSales2;
	}

	@Basic
	@Column(name = "PRIOR_YEAR_SALES_3", nullable = true, precision = 0)
	public Integer getPriorYearSales3() {
		return priorYearSales3;
	}

	public void setPriorYearSales3(Integer priorYearSales3) {
		this.priorYearSales3 = priorYearSales3;
	}

	@Basic
	@Column(name = "PRE_SOLD_QUANTITY", nullable = true, precision = 0)
	public Integer getPreSoldQuantity() {
		return preSoldQuantity;
	}

	public void setPreSoldQuantity(Integer preSoldQuantity) {
		this.preSoldQuantity = preSoldQuantity;
	}

	@Basic
	@Column(name = "REQUESTED_QUANTITY", nullable = true, precision = 0)
	public Integer getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Integer requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	@Basic
	@Column(name = "DATE_LAST_RECEIPTED", nullable = true)
	public String getDateLastReceipted() {
		return dateLastReceipted;
	}

	public void setDateLastReceipted(String dateLastReceipted) {
		this.dateLastReceipted = dateLastReceipted;
	}

	@Basic
	@Column(name = "ERROR_LEVEL", nullable = true, length = 1)
	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	@Basic
	@Column(name = "ERROR_MESSAGE", nullable = true, length = 80)
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Basic
	@Column(name = "LAST_SALE_DATE", nullable = true)
	public String getLastSaleDate() {
		return lastSaleDate;
	}

	public void setLastSaleDate(String lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}

	@Basic
	@Column(name = "ON_ORDER_QTY", nullable = true, precision = 0)
	public Integer getOnOrderQty() {
		return onOrderQty;
	}

	public void setOnOrderQty(Integer onOrderQty) {
		this.onOrderQty = onOrderQty;
	}

	@Basic
	@Column(name = "MFG_CONTROLLED", nullable = true, precision = 0)
	public Integer getMfgControlled() {
		return mfgControlled;
	}

	public void setMfgControlled(Integer mfgControlled) {
		this.mfgControlled = mfgControlled;
	}

	@Basic
	@Column(name = "GUARANTEES_QUANTITY", nullable = true, precision = 0)
	public Integer getGuaranteesQuantity() { return guaranteesQuantity; }

	public void setGuaranteesQuantity(Integer guaranteesQuantity) { this.guaranteesQuantity = guaranteesQuantity; }


	public AipInventoryEntity toAipInventoryEntity(Long dealerId) {
		AipInventoryEntity aip = new AipInventoryEntity();

		aip.setDealerId(dealerId);
		aip.setPartNo(getModifiedPartNumber());
		aip.setCents(getPricing());
		aip.setQoh(Objects.nonNull(this.onHand) ? Math.toIntExact(this.onHand) : 0);
		aip.setDescription(this.description);
		aip.setStatus(getModifiedSpecialStatus());
		aip.setAdmiStatus(getAdmiStatus());
		aip.setLastSale(getLastReceiptOrSale(this.lastSaleDate, this.monthsNoSale));
		aip.setLastReceipt(getLastReceiptOrSale(this.dateLastReceipted, this.monthsNoReceipt));
		aip.setBin(this.bin1);
		aip.setSource(this.source);
		aip.setMfgControlled(getMfgControlledAsBoolean());
		aip.setDataDate(this.inventoryDate);
		aip.setManufacturer(this.manufacturer);
		aip.setEntryDate(Objects.nonNull(this.dateAdded) ? LocalDate.parse(this.dateAdded) : LocalDate.of(2000,1,1));


		return aip;
	}

	@Transient
	private Integer getPricing() {
		if (description != null
				&& (description.contains("MOTORCRAFT")
					|| description.contains("REFRIGERANT")
					|| description.contains("OIL - ENGINE"))) {
			return 1;
		}


		if (this.cost == null)
			return 0;
		else
			return (int) Math.round(this.cost * 100);
	}

	@Transient
	private String getAdmiStatus() {
		return CdkImportService.getAdmiStatus(this.specialStatus);
	}

	@Transient
	private String getModifiedSpecialStatus() {
		if (this.specialStatus == null)
			return "STOCK";
		else
			return this.specialStatus;
	}

	@Transient
	private Boolean getMfgControlledAsBoolean() {
		if (this.mfgControlled == null)
			return null;
		else
			return this.mfgControlled == 1;
	}

	@Transient
	private LocalDate getLastReceiptOrSale(String lastDate, Integer monthNoDate) {
		LocalDate date;

		if (Objects.nonNull(lastDate)) {
			date = LocalDate.parse(lastDate);
		} else {
			date = LocalDate.now().withDayOfMonth(1);

			if (monthNoDate != null)
				date = date.minusMonths(monthNoDate + 1);
		}
		return date;
	}

	@Transient
	private String getModifiedPartNumber() {
		return CdkImportService.getModifiedPartNumber(this.partNumber, this.hashCode());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CdkPartsInventoryChild that = (CdkPartsInventoryChild) o;
		return dealerId.equals(that.dealerId) &&
				inventoryDate.equals(that.inventoryDate) &&
				hostItemId.equals(that.hostItemId) &&
				Objects.equals(partNumber, that.partNumber) &&
				Objects.equals(clazz, that.clazz) &&
				Objects.equals(description, that.description) &&
				Objects.equals(bopck, that.bopck) &&
				Objects.equals(source, that.source) &&
//				Objects.equals(sortKey1, that.sortKey1) &&
//				Objects.equals(sortKey2, that.sortKey2) &&
//				Objects.equals(sortKey3, that.sortKey3) &&
				Objects.equals(pack, that.pack) &&
				Objects.equals(multiple, that.multiple) &&
				Objects.equals(priceUnit, that.priceUnit) &&
				Objects.equals(packMultipleFlag, that.packMultipleFlag) &&
				Objects.equals(cost, that.cost) &&
				Objects.equals(list, that.list) &&
				Objects.equals(trade, that.trade) &&
				Objects.equals(comp, that.comp) &&
				Objects.equals(exchange, that.exchange) &&
				Objects.equals(abcd, that.abcd) &&
				Objects.equals(arrivedFlag, that.arrivedFlag) &&
				Objects.equals(seasonCode1, that.seasonCode1) &&
				Objects.equals(seasonCode2, that.seasonCode2) &&
				Objects.equals(seasonCode3, that.seasonCode3) &&
				Objects.equals(seasonCode4, that.seasonCode4) &&
				Objects.equals(price6, that.price6) &&
				Objects.equals(price7, that.price7) &&
				Objects.equals(pnc, that.pnc) &&
				Objects.equals(rbCode, that.rbCode) &&
				Objects.equals(extraDs, that.extraDs) &&
				Objects.equals(updateCode, that.updateCode) &&
				Objects.equals(commentFlag, that.commentFlag) &&
				Objects.equals(appreciation, that.appreciation) &&
				Objects.equals(oldOnHand, that.oldOnHand) &&
				Objects.equals(newOrderQty, that.newOrderQty) &&
				Objects.equals(review, that.review) &&
				Objects.equals(bin1, that.bin1) &&
				Objects.equals(bin2, that.bin2) &&
				Objects.equals(bin3, that.bin3) &&
				Objects.equals(onHand, that.onHand) &&
				Objects.equals(memoPartFlag, that.memoPartFlag) &&
				Objects.equals(deletePartFlag, that.deletePartFlag) &&
				Objects.equals(perJob, that.perJob) &&
				Objects.equals(brp, that.brp) &&
				Objects.equals(bsl, that.bsl) &&
				Objects.equals(fullBin, that.fullBin) &&
				Objects.equals(specialStatus, that.specialStatus) &&
				Objects.equals(dateAdded, that.dateAdded) &&
				Objects.equals(lastCountDate, that.lastCountDate) &&
				Objects.equals(price8, that.price8) &&
				Objects.equals(price9, that.price9) &&
				Objects.equals(price10, that.price10) &&
				Objects.equals(monthsNoReceipt, that.monthsNoReceipt) &&
				Objects.equals(monthsNoSale, that.monthsNoSale) &&
				Objects.equals(janSales, that.janSales) &&
				Objects.equals(janSales2, that.janSales2) &&
				Objects.equals(janSales3, that.janSales3) &&
				Objects.equals(febSales, that.febSales) &&
				Objects.equals(febSales2, that.febSales2) &&
				Objects.equals(febSales3, that.febSales3) &&
				Objects.equals(marSales, that.marSales) &&
				Objects.equals(marSales2, that.marSales2) &&
				Objects.equals(marSales3, that.marSales3) &&
				Objects.equals(aprSales, that.aprSales) &&
				Objects.equals(aprSales2, that.aprSales2) &&
				Objects.equals(aprSales3, that.aprSales3) &&
				Objects.equals(maySales, that.maySales) &&
				Objects.equals(maySales2, that.maySales2) &&
				Objects.equals(maySales3, that.maySales3) &&
				Objects.equals(junSales, that.junSales) &&
				Objects.equals(junSales2, that.junSales2) &&
				Objects.equals(junSales3, that.junSales3) &&
				Objects.equals(julSales, that.julSales) &&
				Objects.equals(julSales2, that.julSales2) &&
				Objects.equals(julSales3, that.julSales3) &&
				Objects.equals(augSales, that.augSales) &&
				Objects.equals(augSales2, that.augSales2) &&
				Objects.equals(augSales3, that.augSales3) &&
				Objects.equals(sepSales, that.sepSales) &&
				Objects.equals(sepSales2, that.sepSales2) &&
				Objects.equals(sepSales3, that.sepSales3) &&
				Objects.equals(octSales, that.octSales) &&
				Objects.equals(octSales2, that.octSales2) &&
				Objects.equals(octSales3, that.octSales3) &&
				Objects.equals(novSales, that.novSales) &&
				Objects.equals(novSales2, that.novSales2) &&
				Objects.equals(novSales3, that.novSales3) &&
				Objects.equals(decSales, that.decSales) &&
				Objects.equals(decSales2, that.decSales2) &&
				Objects.equals(decSales3, that.decSales3) &&
				Objects.equals(month13Sales, that.month13Sales) &&
				Objects.equals(month14Sales, that.month14Sales) &&
				Objects.equals(lastTransDate, that.lastTransDate) &&
				Objects.equals(baseCostLifo, that.baseCostLifo) &&
				Objects.equals(begQtyLifo, that.begQtyLifo) &&
				Objects.equals(mtdEmerPurchase, that.mtdEmerPurchase) &&
				Objects.equals(mtdPlusAdj, that.mtdPlusAdj) &&
				Objects.equals(mtdMinusAdj, that.mtdMinusAdj) &&
				Objects.equals(mtdLostSales, that.mtdLostSales) &&
				Objects.equals(excessQty, that.excessQty) &&
				Objects.equals(comment, that.comment) &&
				Objects.equals(misc, that.misc) &&
				Objects.equals(unitWeight, that.unitWeight) &&
				Objects.equals(coreMatrix, that.coreMatrix) &&
				Objects.equals(coreSerialized, that.coreSerialized) &&
				Objects.equals(dirtyBin1, that.dirtyBin1) &&
				Objects.equals(dirtyOnHand, that.dirtyOnHand) &&
				Objects.equals(manufacturer, that.manufacturer) &&
				Objects.equals(accountingAccount, that.accountingAccount) &&
				Objects.equals(seasonCode5, that.seasonCode5) &&
				Objects.equals(seasonCode6, that.seasonCode6) &&
				Objects.equals(abcxyz, that.abcxyz) &&
				Objects.equals(cntr, that.cntr) &&
				Objects.equals(priorYearSales1, that.priorYearSales1) &&
				Objects.equals(priorYearSales2, that.priorYearSales2) &&
				Objects.equals(priorYearSales3, that.priorYearSales3) &&
				Objects.equals(preSoldQuantity, that.preSoldQuantity) &&
				Objects.equals(requestedQuantity, that.requestedQuantity) &&
				Objects.equals(dateLastReceipted, that.dateLastReceipted) &&
				Objects.equals(errorLevel, that.errorLevel) &&
				Objects.equals(errorMessage, that.errorMessage) &&
				Objects.equals(lastSaleDate, that.lastSaleDate) &&
				Objects.equals(onOrderQty, that.onOrderQty) &&
				Objects.equals(mfgControlled, that.mfgControlled) &&
				Objects.equals(guaranteesQuantity, that.guaranteesQuantity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, inventoryDate, hostItemId, partNumber, clazz, description, bopck, source,
//				sortKey1, sortKey2, sortKey3,
				pack, multiple, priceUnit, packMultipleFlag, cost, list, trade, comp, exchange, abcd, arrivedFlag, seasonCode1, seasonCode2, seasonCode3, seasonCode4, price6, price7, pnc, rbCode, extraDs, updateCode, commentFlag, appreciation, oldOnHand, newOrderQty, review, bin1, bin2, bin3, onHand, memoPartFlag, deletePartFlag, perJob, brp, bsl, fullBin, specialStatus, dateAdded, lastCountDate, price8, price9, price10, monthsNoReceipt, monthsNoSale, janSales, janSales2, janSales3, febSales, febSales2, febSales3, marSales, marSales2, marSales3, aprSales, aprSales2, aprSales3, maySales, maySales2, maySales3, junSales, junSales2, junSales3, julSales, julSales2, julSales3, augSales, augSales2, augSales3, sepSales, sepSales2, sepSales3, octSales, octSales2, octSales3, novSales, novSales2, novSales3, decSales, decSales2, decSales3, month13Sales, month14Sales, lastTransDate, baseCostLifo, begQtyLifo, mtdEmerPurchase, mtdPlusAdj, mtdMinusAdj, mtdLostSales, excessQty, comment, misc, unitWeight, coreMatrix, coreSerialized, dirtyBin1, dirtyOnHand, manufacturer, accountingAccount, seasonCode5, seasonCode6, abcxyz, cntr, priorYearSales1, priorYearSales2, priorYearSales3, preSoldQuantity, requestedQuantity, dateLastReceipted, errorLevel, errorMessage, lastSaleDate, onOrderQty, mfgControlled, guaranteesQuantity);
	}

	@Override
	@Transient
	public String toString() {
		return "PartsInventoryChild{" +
				"dealerId='" + dealerId + '\'' +
				", inventoryDate=" + inventoryDate +
				", hostItemId='" + hostItemId + '\'' +
				", partNumber='" + partNumber + '\'' +
				", clazz='" + clazz + '\'' +
				", description='" + description + '\'' +
				", bopck='" + bopck + '\'' +
				", source='" + source + '\'' +
				", pack=" + pack +
				", multiple=" + multiple +
				", priceUnit=" + priceUnit +
				", packMultipleFlag='" + packMultipleFlag + '\'' +
				", cost=" + cost +
				", list=" + list +
				", trade=" + trade +
				", comp=" + comp +
				", exchange=" + exchange +
				", abcd='" + abcd + '\'' +
				", arrivedFlag='" + arrivedFlag + '\'' +
				", seasonCode1='" + seasonCode1 + '\'' +
				", seasonCode2='" + seasonCode2 + '\'' +
				", seasonCode3='" + seasonCode3 + '\'' +
				", seasonCode4='" + seasonCode4 + '\'' +
				", price6=" + price6 +
				", price7=" + price7 +
				", pnc='" + pnc + '\'' +
				", rbCode='" + rbCode + '\'' +
				", extraDs=" + extraDs +
				", updateCode='" + updateCode + '\'' +
				", commentFlag='" + commentFlag + '\'' +
				", appreciation=" + appreciation +
				", oldOnHand=" + oldOnHand +
				", newOrderQty=" + newOrderQty +
				", review='" + review + '\'' +
				", bin1='" + bin1 + '\'' +
				", bin2='" + bin2 + '\'' +
				", bin3='" + bin3 + '\'' +
				", onHand=" + onHand +
				", memoPartFlag='" + memoPartFlag + '\'' +
				", deletePartFlag='" + deletePartFlag + '\'' +
				", perJob=" + perJob +
				", brp=" + brp +
				", bsl=" + bsl +
				", fullBin=" + fullBin +
				", specialStatus='" + specialStatus + '\'' +
				", dateAdded='" + dateAdded + '\'' +
				", lastCountDate='" + lastCountDate + '\'' +
				", price8=" + price8 +
				", price9=" + price9 +
				", price10=" + price10 +
				", monthsNoReceipt=" + monthsNoReceipt +
				", monthsNoSale=" + monthsNoSale +
				", janSales=" + janSales +
				", janSales2=" + janSales2 +
				", janSales3=" + janSales3 +
				", febSales=" + febSales +
				", febSales2=" + febSales2 +
				", febSales3=" + febSales3 +
				", marSales=" + marSales +
				", marSales2=" + marSales2 +
				", marSales3=" + marSales3 +
				", aprSales=" + aprSales +
				", aprSales2=" + aprSales2 +
				", aprSales3=" + aprSales3 +
				", maySales=" + maySales +
				", maySales2=" + maySales2 +
				", maySales3=" + maySales3 +
				", junSales=" + junSales +
				", junSales2=" + junSales2 +
				", junSales3=" + junSales3 +
				", julSales=" + julSales +
				", julSales2=" + julSales2 +
				", julSales3=" + julSales3 +
				", augSales=" + augSales +
				", augSales2=" + augSales2 +
				", augSales3=" + augSales3 +
				", sepSales=" + sepSales +
				", sepSales2=" + sepSales2 +
				", sepSales3=" + sepSales3 +
				", octSales=" + octSales +
				", octSales2=" + octSales2 +
				", octSales3=" + octSales3 +
				", novSales=" + novSales +
				", novSales2=" + novSales2 +
				", novSales3=" + novSales3 +
				", decSales=" + decSales +
				", decSales2=" + decSales2 +
				", decSales3=" + decSales3 +
				", month13Sales=" + month13Sales +
				", month14Sales=" + month14Sales +
				", lastTransDate='" + lastTransDate + '\'' +
				", baseCostLifo=" + baseCostLifo +
				", begQtyLifo=" + begQtyLifo +
				", mtdEmerPurchase=" + mtdEmerPurchase +
				", mtdPlusAdj=" + mtdPlusAdj +
				", mtdMinusAdj=" + mtdMinusAdj +
				", mtdLostSales=" + mtdLostSales +
				", excessQty=" + excessQty +
				", comment='" + comment + '\'' +
				", misc='" + misc + '\'' +
				", unitWeight=" + unitWeight +
				", coreMatrix='" + coreMatrix + '\'' +
				", coreSerialized='" + coreSerialized + '\'' +
				", dirtyBin1='" + dirtyBin1 + '\'' +
				", dirtyOnHand=" + dirtyOnHand +
				", manufacturer='" + manufacturer + '\'' +
				", accountingAccount='" + accountingAccount + '\'' +
				", seasonCode5='" + seasonCode5 + '\'' +
				", seasonCode6='" + seasonCode6 + '\'' +
				", abcxyz='" + abcxyz + '\'' +
				", cntr='" + cntr + '\'' +
				", priorYearSales1=" + priorYearSales1 +
				", priorYearSales2=" + priorYearSales2 +
				", priorYearSales3=" + priorYearSales3 +
				", preSoldQuantity=" + preSoldQuantity +
				", requestedQuantity=" + requestedQuantity +
				", dateLastReceipted='" + dateLastReceipted + '\'' +
				", errorLevel='" + errorLevel + '\'' +
				", errorMessage='" + errorMessage + '\'' +
				", lastSaleDate='" + lastSaleDate + '\'' +
				", onOrderQty=" + onOrderQty +
				", mfgControlled=" + mfgControlled +
				", guaranteesQuantity=" + guaranteesQuantity +
				'}';
	}
}
