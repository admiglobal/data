package com.admi.data.entities;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DealerTrackInventoryRow {

	private String partNo;
	private String description;
	private String manufacturer;
	private String qoh;
	private String cost;
	private String status;
	private String phaseIn;
	private String lastSaleDate;
	private String lastReceiptDate;
	private String stockingGroup;
	private String qoo;
	private String phaseOut;
	private String bin;
	private String month1;
	private String month2;
	private String month3;
	private String month4;
	private String month5;
	private String month6;
	private String month7;
	private String month8;
	private String month9;
	private String month10;
	private String month11;
	private String month12;
	private String stockingCode;

	public DealerTrackInventoryRow() {}

	public AipInventoryEntity toAipInventoryEntity(Long dealerId, DateTimeFormatter formatter, LocalDate dataDate) {
		AipInventoryEntity part = new AipInventoryEntity();

		part.setDealerId(dealerId);
		part.setPartNo(this.partNo
				.replaceAll("[^a-zA-Z0-9]", "")
				.toUpperCase());
		part.setCents(getStringAsCents(this.cost));
		part.setQoh(getStringAsInteger(this.qoh));
		part.setDescription(this.description.trim());
		part.setStatus(this.status.trim());
		part.setAdmiStatus(getAdmiStatus());
		part.setLastSale(getStringAsLocalDate(this.lastSaleDate, formatter));
		part.setLastReceipt(getStringAsLocalDate(this.lastReceiptDate, formatter));
		part.setBin(this.bin.trim());
		part.setSource(this.stockingGroup.trim());
		part.setMfgControlled(getStringAsBoolean(this.stockingCode));
		part.setDataDate(dataDate);
		part.setManufacturer(this.manufacturer.trim());
		part.setQoo(getStringAsInteger(this.qoo));
		part.setTwelveMonthSales(getTwelveMonthSales());
		part.setEntryDate(getStringAsLocalDate(this.phaseIn, formatter));

		return part;
	}

	public Integer getStringAsInteger(String string) {
		if (string != null) {
			try {
				return Integer.parseInt(string.trim());
			} catch (NumberFormatException e) {
//				System.out.println("NumberFormatException: " + string + " not converted to Integer.");
//				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}

	public LocalDate getStringAsLocalDate(String string, DateTimeFormatter formatter) {
		if (string != null) {
			try {
				return LocalDate.parse(string.trim());
			} catch (DateTimeParseException e) {
//				System.out.println("DateTimeParseException: " + string.trim() + " not converted to LocalDateTime.");
//				e.printStackTrace();
				return LocalDate.of(2000, 1, 1);
			}
		} else {
			return LocalDate.of(2000, 1, 1);
		}
	}

	public Boolean getStringAsBoolean(String string) {
		if (string.trim().equals("Y")) {
			return true;
		} else if (string.trim().equals("N")) {
			return false;
		} else {
			return null;
		}
	}

	public Integer getStringAsCents(String string) {
		if (string != null) {
			NumberFormat format = new DecimalFormat("$#,###.00");
			double dollarValue;

			try {
				dollarValue = format.parse(string.trim()).doubleValue();
			} catch (ParseException e) {
//				System.out.println("NumberFormatException: " + string.trim() + " not converted to Double.");
//				e.printStackTrace();
				dollarValue = 0.0;
			}

			return (int) Math.round(dollarValue * 100);

		} else {
			return 0;
		}
	}

	private String getAdmiStatus() {
		String status = this.status.trim().toUpperCase();
		String newStatus = "N";

		switch(status) {
			case "A":
			case "R":
				newStatus = "S";
				break;
		}
		return newStatus;
	}

	private Integer getTwelveMonthSales() {
		int total = 0;

		total += getStringAsInteger(this.month1);
		total += getStringAsInteger(this.month2);
		total += getStringAsInteger(this.month3);
		total += getStringAsInteger(this.month4);
		total += getStringAsInteger(this.month5);
		total += getStringAsInteger(this.month6);
		total += getStringAsInteger(this.month7);
		total += getStringAsInteger(this.month8);
		total += getStringAsInteger(this.month9);
		total += getStringAsInteger(this.month10);
		total += getStringAsInteger(this.month11);
		total += getStringAsInteger(this.month12);

		return total;
	}

	public Boolean includeInInventory() {



		return true;
	}


	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getQoh() {
		return qoh;
	}

	public void setQoh(String qoh) {
		this.qoh = qoh;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhaseIn() {
		return phaseIn;
	}

	public void setPhaseIn(String phaseIn) {
		this.phaseIn = phaseIn;
	}

	public String getLastSaleDate() {
		return lastSaleDate;
	}

	public void setLastSaleDate(String lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}

	public String getLastReceiptDate() {
		return lastReceiptDate;
	}

	public void setLastReceiptDate(String lastReceiptDate) {
		this.lastReceiptDate = lastReceiptDate;
	}

	public String getStockingGroup() {
		return stockingGroup;
	}

	public void setStockingGroup(String stockingGroup) {
		this.stockingGroup = stockingGroup;
	}

	public String getQoo() {
		return qoo;
	}

	public void setQoo(String qoo) {
		this.qoo = qoo;
	}

	public String getPhaseOut() {
		return phaseOut;
	}

	public void setPhaseOut(String phaseOut) {
		this.phaseOut = phaseOut;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getMonth2() {
		return month2;
	}

	public void setMonth2(String month2) {
		this.month2 = month2;
	}

	public String getMonth3() {
		return month3;
	}

	public void setMonth3(String month3) {
		this.month3 = month3;
	}

	public String getMonth4() {
		return month4;
	}

	public void setMonth4(String month4) {
		this.month4 = month4;
	}

	public String getMonth5() {
		return month5;
	}

	public void setMonth5(String month5) {
		this.month5 = month5;
	}

	public String getMonth6() {
		return month6;
	}

	public void setMonth6(String month6) {
		this.month6 = month6;
	}

	public String getMonth7() {
		return month7;
	}

	public void setMonth7(String month7) {
		this.month7 = month7;
	}

	public String getMonth8() {
		return month8;
	}

	public void setMonth8(String month8) {
		this.month8 = month8;
	}

	public String getMonth9() {
		return month9;
	}

	public void setMonth9(String month9) {
		this.month9 = month9;
	}

	public String getMonth10() {
		return month10;
	}

	public void setMonth10(String month10) {
		this.month10 = month10;
	}

	public String getMonth11() {
		return month11;
	}

	public void setMonth11(String month11) {
		this.month11 = month11;
	}

	public String getMonth12() {
		return month12;
	}

	public void setMonth12(String month12) {
		this.month12 = month12;
	}

	public String getStockingCode() {
		return stockingCode;
	}

	public void setStockingCode(String stockingCode) {
		this.stockingCode = stockingCode;
	}
}
