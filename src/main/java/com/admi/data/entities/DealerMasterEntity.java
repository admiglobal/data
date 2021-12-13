package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "DEALER_MASTER", schema = "ADMI")
public class DealerMasterEntity {
	private long dealerId;
	private String locationCode;
	private String paCode;
	private String dealershipName;
	private String dealershipAddress1;
	private String dealershipAddress2;
	private String dealershipCity;
	private String dealershipState;
	private String dealershipZip;
	private String dealershipZipplus4;
	private String dealershipCountry;
	private String useShippingAddressForMaili;
	private String mailingAddress1;
	private String mailingAddress2;
	private String mailingCity;
	private String mailingState;
	private String mailingZip;
	private String mailingZipplus4;
	private String mailingCountry;
	private String heavyTruckFlag;
	private Integer fordRegionNumber;
	private String primaryEmailAddress;
	private Integer primaryManufacturerId;
	private Integer fordSegment;
	private Integer admiDealerType;
	private Integer responsibleConsultantId;
	private Integer graphMonths;
	private LocalDate graphStartDate;
	private String referenceDealer;
	private String fordHvcRegionCode;
	private String doNotSolicitFlag;
	private String solicitationComments;
	private String fordDominantMarketArea;
	private String fordMajorMarketArea;
	private String fordFcsdMarketArea;
	private String fordFcsdZone;
	private Integer lastUpdatedBy;
	private LocalDateTime lastUpdatedDate;
	private LocalDate reactDate;
	private String testDealerFlag;
	private LocalDateTime imaBlock;
	private String electronicReporting;
	private String hyperion;
	private Integer hyperionDist;
	private String dspServiceId;
	private String dealerSalesCode;
	private LocalDateTime effectiveDate;
	private LocalDateTime terminationDate;
	private String primaryOrSecondary;
	private String nissRegionCode;
	private String salesCode;
	private String golddId;
	private String billToPerson;
	private String lm;
	private String minorityCode;
	private String regionMarket;
	private String selectDealer;
	private String mon;
	private String tue;
	private String wed;
	private String thu;
	private String fri;
	private String previousDealership;
	private Long returnWeek;
	private Long utc;
	private String localDlrName;

	@Id
	@Column(name = "DEALER_ID", nullable = false, precision = 0)
	public long getDealerId() {
		return dealerId;
	}

	public void setDealerId(long dealerId) {
		this.dealerId = dealerId;
	}

	@Basic
	@Column(name = "LOCATION_CODE", nullable = true, length = 20)
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Basic
	@Column(name = "PA_CODE", nullable = true, length = 20)
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	@Basic
	@Column(name = "DEALERSHIP_NAME", nullable = true, length = 75)
	public String getDealershipName() {
		return dealershipName;
	}

	public void setDealershipName(String dealershipName) {
		this.dealershipName = dealershipName;
	}

	@Basic
	@Column(name = "DEALERSHIP_ADDRESS1", nullable = true, length = 90)
	public String getDealershipAddress1() {
		return dealershipAddress1;
	}

	public void setDealershipAddress1(String dealershipAddress1) {
		this.dealershipAddress1 = dealershipAddress1;
	}

	@Basic
	@Column(name = "DEALERSHIP_ADDRESS2", nullable = true, length = 90)
	public String getDealershipAddress2() {
		return dealershipAddress2;
	}

	public void setDealershipAddress2(String dealershipAddress2) {
		this.dealershipAddress2 = dealershipAddress2;
	}

	@Basic
	@Column(name = "DEALERSHIP_CITY", nullable = true, length = 50)
	public String getDealershipCity() {
		return dealershipCity;
	}

	public void setDealershipCity(String dealershipCity) {
		this.dealershipCity = dealershipCity;
	}

	@Basic
	@Column(name = "DEALERSHIP_STATE", nullable = true, length = 20)
	public String getDealershipState() {
		return dealershipState;
	}

	public void setDealershipState(String dealershipState) {
		this.dealershipState = dealershipState;
	}

	@Basic
	@Column(name = "DEALERSHIP_ZIP", nullable = true, length = 5)
	public String getDealershipZip() {
		return dealershipZip;
	}

	public void setDealershipZip(String dealershipZip) {
		this.dealershipZip = dealershipZip;
	}

	@Basic
	@Column(name = "DEALERSHIP_ZIPPLUS4", nullable = true, length = 4)
	public String getDealershipZipplus4() {
		return dealershipZipplus4;
	}

	public void setDealershipZipplus4(String dealershipZipplus4) {
		this.dealershipZipplus4 = dealershipZipplus4;
	}

	@Basic
	@Column(name = "DEALERSHIP_COUNTRY", nullable = true, length = 50)
	public String getDealershipCountry() {
		return dealershipCountry;
	}

	public void setDealershipCountry(String dealershipCountry) {
		this.dealershipCountry = dealershipCountry;
	}

	@Basic
	@Column(name = "USE_SHIPPING_ADDRESS_FOR_MAILI", nullable = true, length = 100)
	public String getUseShippingAddressForMaili() {
		return useShippingAddressForMaili;
	}

	public void setUseShippingAddressForMaili(String useShippingAddressForMaili) {
		this.useShippingAddressForMaili = useShippingAddressForMaili;
	}

	@Basic
	@Column(name = "MAILING_ADDRESS_1", nullable = true, length = 50)
	public String getMailingAddress1() {
		return mailingAddress1;
	}

	public void setMailingAddress1(String mailingAddress1) {
		this.mailingAddress1 = mailingAddress1;
	}

	@Basic
	@Column(name = "MAILING_ADDRESS_2", nullable = true, length = 50)
	public String getMailingAddress2() {
		return mailingAddress2;
	}

	public void setMailingAddress2(String mailingAddress2) {
		this.mailingAddress2 = mailingAddress2;
	}

	@Basic
	@Column(name = "MAILING_CITY", nullable = true, length = 50)
	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	@Basic
	@Column(name = "MAILING_STATE", nullable = true, length = 20)
	public String getMailingState() {
		return mailingState;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

	@Basic
	@Column(name = "MAILING_ZIP", nullable = true, length = 100)
	public String getMailingZip() {
		return mailingZip;
	}

	public void setMailingZip(String mailingZip) {
		this.mailingZip = mailingZip;
	}

	@Basic
	@Column(name = "MAILING_ZIPPLUS4", nullable = true, length = 4)
	public String getMailingZipplus4() {
		return mailingZipplus4;
	}

	public void setMailingZipplus4(String mailingZipplus4) {
		this.mailingZipplus4 = mailingZipplus4;
	}

	@Basic
	@Column(name = "MAILING_COUNTRY", nullable = true, length = 20)
	public String getMailingCountry() {
		return mailingCountry;
	}

	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}

	@Basic
	@Column(name = "HEAVY_TRUCK_FLAG", nullable = true, length = 1)
	public String getHeavyTruckFlag() {
		return heavyTruckFlag;
	}

	public void setHeavyTruckFlag(String heavyTruckFlag) {
		this.heavyTruckFlag = heavyTruckFlag;
	}

	@Basic
	@Column(name = "FORD_REGION_NUMBER", nullable = true, precision = 0)
	public Integer getFordRegionNumber() {
		return fordRegionNumber;
	}

	public void setFordRegionNumber(Integer fordRegionNumber) {
		this.fordRegionNumber = fordRegionNumber;
	}

	@Basic
	@Column(name = "PRIMARY_EMAIL_ADDRESS", nullable = true, length = 50)
	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	@Basic
	@Column(name = "PRIMARY_MANUFACTURER_ID", nullable = true, precision = 0)
	public Integer getPrimaryManufacturerId() {
		return primaryManufacturerId;
	}

	public void setPrimaryManufacturerId(Integer primaryManufacturerId) {
		this.primaryManufacturerId = primaryManufacturerId;
	}

	@Basic
	@Column(name = "FORD_SEGMENT", nullable = true, precision = 0)
	public Integer getFordSegment() {
		return fordSegment;
	}

	public void setFordSegment(Integer fordSegment) {
		this.fordSegment = fordSegment;
	}

	@Basic
	@Column(name = "ADMI_DEALER_TYPE", nullable = true, precision = 0)
	public Integer getAdmiDealerType() {
		return admiDealerType;
	}

	public void setAdmiDealerType(Integer admiDealerType) {
		this.admiDealerType = admiDealerType;
	}

	@Basic
	@Column(name = "RESPONSIBLE_CONSULTANT_ID", nullable = true, precision = 0)
	public Integer getResponsibleConsultantId() {
		return responsibleConsultantId;
	}

	public void setResponsibleConsultantId(Integer responsibleConsultantId) {
		this.responsibleConsultantId = responsibleConsultantId;
	}

	@Basic
	@Column(name = "GRAPH_MONTHS", nullable = true, precision = 0)
	public Integer getGraphMonths() {
		return graphMonths;
	}

	public void setGraphMonths(Integer graphMonths) {
		this.graphMonths = graphMonths;
	}

	@Basic
	@Column(name = "GRAPH_START_DATE", nullable = true)
	public LocalDate getGraphStartDate() {
		return graphStartDate;
	}

	public void setGraphStartDate(LocalDate graphStartDate) {
		this.graphStartDate = graphStartDate;
	}

	@Basic
	@Column(name = "REFERENCE_DEALER", nullable = true, length = 1)
	public String getReferenceDealer() {
		return referenceDealer;
	}

	public void setReferenceDealer(String referenceDealer) {
		this.referenceDealer = referenceDealer;
	}

	@Basic
	@Column(name = "FORD_HVC_REGION_CODE", nullable = true, length = 10)
	public String getFordHvcRegionCode() {
		return fordHvcRegionCode;
	}

	public void setFordHvcRegionCode(String fordHvcRegionCode) {
		this.fordHvcRegionCode = fordHvcRegionCode;
	}

	@Basic
	@Column(name = "DO_NOT_SOLICIT_FLAG", nullable = true, length = 1)
	public String getDoNotSolicitFlag() {
		return doNotSolicitFlag;
	}

	public void setDoNotSolicitFlag(String doNotSolicitFlag) {
		this.doNotSolicitFlag = doNotSolicitFlag;
	}

	@Basic
	@Column(name = "SOLICITATION_COMMENTS", nullable = true, length = 240)
	public String getSolicitationComments() {
		return solicitationComments;
	}

	public void setSolicitationComments(String solicitationComments) {
		this.solicitationComments = solicitationComments;
	}

	@Basic
	@Column(name = "FORD_DOMINANT_MARKET_AREA", nullable = true, length = 3)
	public String getFordDominantMarketArea() {
		return fordDominantMarketArea;
	}

	public void setFordDominantMarketArea(String fordDominantMarketArea) {
		this.fordDominantMarketArea = fordDominantMarketArea;
	}

	@Basic
	@Column(name = "FORD_MAJOR_MARKET_AREA", nullable = true, length = 3)
	public String getFordMajorMarketArea() {
		return fordMajorMarketArea;
	}

	public void setFordMajorMarketArea(String fordMajorMarketArea) {
		this.fordMajorMarketArea = fordMajorMarketArea;
	}

	@Basic
	@Column(name = "FORD_FCSD_MARKET_AREA", nullable = true, length = 3)
	public String getFordFcsdMarketArea() {
		return fordFcsdMarketArea;
	}

	public void setFordFcsdMarketArea(String fordFcsdMarketArea) {
		this.fordFcsdMarketArea = fordFcsdMarketArea;
	}

	@Basic
	@Column(name = "FORD_FCSD_ZONE", nullable = true, length = 3)
	public String getFordFcsdZone() {
		return fordFcsdZone;
	}

	public void setFordFcsdZone(String fordFcsdZone) {
		this.fordFcsdZone = fordFcsdZone;
	}

	@Basic
	@Column(name = "LAST_UPDATED_BY", nullable = true, precision = 0)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Basic
	@Column(name = "LAST_UPDATED_DATE", nullable = true)
	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Basic
	@Column(name = "REACT_DATE", nullable = true)
	public LocalDate getReactDate() {
		return reactDate;
	}

	public void setReactDate(LocalDate reactDate) {
		this.reactDate = reactDate;
	}

	@Basic
	@Column(name = "TEST_DEALER_FLAG", nullable = true, length = 1)
	public String getTestDealerFlag() {
		return testDealerFlag;
	}

	public void setTestDealerFlag(String testDealerFlag) {
		this.testDealerFlag = testDealerFlag;
	}

	@Basic
	@Column(name = "IMA_BLOCK", nullable = true)
	public LocalDateTime getImaBlock() {
		return imaBlock;
	}

	public void setImaBlock(LocalDateTime imaBlock) {
		this.imaBlock = imaBlock;
	}

	@Basic
	@Column(name = "ELECTRONIC_REPORTING", nullable = true, length = 1)
	public String getElectronicReporting() {
		return electronicReporting;
	}

	public void setElectronicReporting(String electronicReporting) {
		this.electronicReporting = electronicReporting;
	}

	@Basic
	@Column(name = "HYPERION", nullable = true, length = 20)
	public String getHyperion() {
		return hyperion;
	}

	public void setHyperion(String hyperion) {
		this.hyperion = hyperion;
	}

	@Basic
	@Column(name = "HYPERION_DIST", nullable = true, precision = 0)
	public Integer getHyperionDist() {
		return hyperionDist;
	}

	public void setHyperionDist(Integer hyperionDist) {
		this.hyperionDist = hyperionDist;
	}

	@Basic
	@Column(name = "DSP_SERVICE_ID", nullable = true, length = 20)
	public String getDspServiceId() {
		return dspServiceId;
	}

	public void setDspServiceId(String dspServiceId) {
		this.dspServiceId = dspServiceId;
	}

	@Basic
	@Column(name = "DEALER_SALES_CODE", nullable = true, length = 20)
	public String getDealerSalesCode() {
		return dealerSalesCode;
	}

	public void setDealerSalesCode(String dealerSalesCode) {
		this.dealerSalesCode = dealerSalesCode;
	}

	@Basic
	@Column(name = "EFFECTIVE_DATE", nullable = true)
	public LocalDateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Basic
	@Column(name = "TERMINATION_DATE", nullable = true)
	public LocalDateTime getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(LocalDateTime terminationDate) {
		this.terminationDate = terminationDate;
	}

	@Basic
	@Column(name = "PRIMARY_OR_SECONDARY", nullable = true, length = 1)
	public String getPrimaryOrSecondary() {
		return primaryOrSecondary;
	}

	public void setPrimaryOrSecondary(String primaryOrSecondary) {
		this.primaryOrSecondary = primaryOrSecondary;
	}

	@Basic
	@Column(name = "NISS_REGION_CODE", nullable = true, length = 2)
	public String getNissRegionCode() {
		return nissRegionCode;
	}

	public void setNissRegionCode(String nissRegionCode) {
		this.nissRegionCode = nissRegionCode;
	}

	@Basic
	@Column(name = "SALES_CODE", nullable = true, length = 50)
	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	@Basic
	@Column(name = "GOLDD_ID", nullable = true, length = 50)
	public String getGolddId() {
		return golddId;
	}

	public void setGolddId(String golddId) {
		this.golddId = golddId;
	}

	@Basic
	@Column(name = "BILL_TO_PERSON", nullable = true, length = 100)
	public String getBillToPerson() {
		return billToPerson;
	}

	public void setBillToPerson(String billToPerson) {
		this.billToPerson = billToPerson;
	}

	@Basic
	@Column(name = "LM", nullable = true, length = 1)
	public String getLm() {
		return lm;
	}

	public void setLm(String lm) {
		this.lm = lm;
	}

	@Basic
	@Column(name = "MINORITY_CODE", nullable = true, length = 2)
	public String getMinorityCode() {
		return minorityCode;
	}

	public void setMinorityCode(String minorityCode) {
		this.minorityCode = minorityCode;
	}

	@Basic
	@Column(name = "REGION_MARKET", nullable = true, length = 2)
	public String getRegionMarket() {
		return regionMarket;
	}

	public void setRegionMarket(String regionMarket) {
		this.regionMarket = regionMarket;
	}

	@Basic
	@Column(name = "SELECT_DEALER", nullable = true, length = 1)
	public String getSelectDealer() {
		return selectDealer;
	}

	public void setSelectDealer(String selectDealer) {
		this.selectDealer = selectDealer;
	}

	@Basic
	@Column(name = "MON", nullable = true, length = 1)
	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	@Basic
	@Column(name = "TUE", nullable = true, length = 1)
	public String getTue() {
		return tue;
	}

	public void setTue(String tue) {
		this.tue = tue;
	}

	@Basic
	@Column(name = "WED", nullable = true, length = 1)
	public String getWed() {
		return wed;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	@Basic
	@Column(name = "THU", nullable = true, length = 1)
	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	@Basic
	@Column(name = "FRI", nullable = true, length = 1)
	public String getFri() {
		return fri;
	}

	public void setFri(String fri) {
		this.fri = fri;
	}

	@Basic
	@Column(name = "PREVIOUS_DEALERSHIP", nullable = true, length = 5)
	public String getPreviousDealership() {
		return previousDealership;
	}

	public void setPreviousDealership(String previousDealership) {
		this.previousDealership = previousDealership;
	}

	@Basic
	@Column(name = "RETURN_WEEK", nullable = true, precision = 0)
	public Long getReturnWeek() {
		return returnWeek;
	}

	public void setReturnWeek(Long returnWeek) {
		this.returnWeek = returnWeek;
	}

	@Basic
	@Column(name = "UTC", nullable = true, precision = 0)
	public Long getUtc() {
		return utc;
	}

	public void setUtc(Long utc) {
		this.utc = utc;
	}

	@Basic
	@Column(name = "LOCAL_DLR_NAME", nullable = true, length = 50)
	public String getLocalDlrName() {
		return localDlrName;
	}

	public void setLocalDlrName(String localDlrName) {
		this.localDlrName = localDlrName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DealerMasterEntity that = (DealerMasterEntity) o;
		return dealerId == that.dealerId &&
				Objects.equals(locationCode, that.locationCode) &&
				Objects.equals(paCode, that.paCode) &&
				Objects.equals(dealershipName, that.dealershipName) &&
				Objects.equals(dealershipAddress1, that.dealershipAddress1) &&
				Objects.equals(dealershipAddress2, that.dealershipAddress2) &&
				Objects.equals(dealershipCity, that.dealershipCity) &&
				Objects.equals(dealershipState, that.dealershipState) &&
				Objects.equals(dealershipZip, that.dealershipZip) &&
				Objects.equals(dealershipZipplus4, that.dealershipZipplus4) &&
				Objects.equals(dealershipCountry, that.dealershipCountry) &&
				Objects.equals(useShippingAddressForMaili, that.useShippingAddressForMaili) &&
				Objects.equals(mailingAddress1, that.mailingAddress1) &&
				Objects.equals(mailingAddress2, that.mailingAddress2) &&
				Objects.equals(mailingCity, that.mailingCity) &&
				Objects.equals(mailingState, that.mailingState) &&
				Objects.equals(mailingZip, that.mailingZip) &&
				Objects.equals(mailingZipplus4, that.mailingZipplus4) &&
				Objects.equals(mailingCountry, that.mailingCountry) &&
				Objects.equals(heavyTruckFlag, that.heavyTruckFlag) &&
				Objects.equals(fordRegionNumber, that.fordRegionNumber) &&
				Objects.equals(primaryEmailAddress, that.primaryEmailAddress) &&
				Objects.equals(primaryManufacturerId, that.primaryManufacturerId) &&
				Objects.equals(fordSegment, that.fordSegment) &&
				Objects.equals(admiDealerType, that.admiDealerType) &&
				Objects.equals(responsibleConsultantId, that.responsibleConsultantId) &&
				Objects.equals(graphMonths, that.graphMonths) &&
				Objects.equals(graphStartDate, that.graphStartDate) &&
				Objects.equals(referenceDealer, that.referenceDealer) &&
				Objects.equals(fordHvcRegionCode, that.fordHvcRegionCode) &&
				Objects.equals(doNotSolicitFlag, that.doNotSolicitFlag) &&
				Objects.equals(solicitationComments, that.solicitationComments) &&
				Objects.equals(fordDominantMarketArea, that.fordDominantMarketArea) &&
				Objects.equals(fordMajorMarketArea, that.fordMajorMarketArea) &&
				Objects.equals(fordFcsdMarketArea, that.fordFcsdMarketArea) &&
				Objects.equals(fordFcsdZone, that.fordFcsdZone) &&
				Objects.equals(lastUpdatedBy, that.lastUpdatedBy) &&
				Objects.equals(lastUpdatedDate, that.lastUpdatedDate) &&
				Objects.equals(reactDate, that.reactDate) &&
				Objects.equals(testDealerFlag, that.testDealerFlag) &&
				Objects.equals(imaBlock, that.imaBlock) &&
				Objects.equals(electronicReporting, that.electronicReporting) &&
				Objects.equals(hyperion, that.hyperion) &&
				Objects.equals(hyperionDist, that.hyperionDist) &&
				Objects.equals(dspServiceId, that.dspServiceId) &&
				Objects.equals(dealerSalesCode, that.dealerSalesCode) &&
				Objects.equals(effectiveDate, that.effectiveDate) &&
				Objects.equals(terminationDate, that.terminationDate) &&
				Objects.equals(primaryOrSecondary, that.primaryOrSecondary) &&
				Objects.equals(nissRegionCode, that.nissRegionCode) &&
				Objects.equals(salesCode, that.salesCode) &&
				Objects.equals(golddId, that.golddId) &&
				Objects.equals(billToPerson, that.billToPerson) &&
				Objects.equals(lm, that.lm) &&
				Objects.equals(minorityCode, that.minorityCode) &&
				Objects.equals(regionMarket, that.regionMarket) &&
				Objects.equals(selectDealer, that.selectDealer) &&
				Objects.equals(mon, that.mon) &&
				Objects.equals(tue, that.tue) &&
				Objects.equals(wed, that.wed) &&
				Objects.equals(thu, that.thu) &&
				Objects.equals(fri, that.fri) &&
				Objects.equals(previousDealership, that.previousDealership) &&
				Objects.equals(returnWeek, that.returnWeek) &&
				Objects.equals(utc, that.utc) &&
				Objects.equals(localDlrName, that.localDlrName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dealerId, locationCode, paCode, dealershipName, dealershipAddress1, dealershipAddress2, dealershipCity, dealershipState, dealershipZip, dealershipZipplus4, dealershipCountry, useShippingAddressForMaili, mailingAddress1, mailingAddress2, mailingCity, mailingState, mailingZip, mailingZipplus4, mailingCountry, heavyTruckFlag, fordRegionNumber, primaryEmailAddress, primaryManufacturerId, fordSegment, admiDealerType, responsibleConsultantId, graphMonths, graphStartDate, referenceDealer, fordHvcRegionCode, doNotSolicitFlag, solicitationComments, fordDominantMarketArea, fordMajorMarketArea, fordFcsdMarketArea, fordFcsdZone, lastUpdatedBy, lastUpdatedDate, reactDate, testDealerFlag, imaBlock, electronicReporting, hyperion, hyperionDist, dspServiceId, dealerSalesCode, effectiveDate, terminationDate, primaryOrSecondary, nissRegionCode, salesCode, golddId, billToPerson, lm, minorityCode, regionMarket, selectDealer, mon, tue, wed, thu, fri, previousDealership, returnWeek, utc, localDlrName);
	}
}
