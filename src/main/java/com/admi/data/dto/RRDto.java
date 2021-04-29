package com.admi.data.dto;

public class RRDto {

	public String partNo;
	public Long qoh;
	public Long costCents;
	public Long source;
	public Long monthNoSale;
	public String status;
	public String entryDate;
	public Long bsl;
	public Long history6;
	public Long history12;
	public Long history24;
	public Long min;
	public Long max;

	public RRDto(){}

	public RRDto(String partNo, Long qoh, Long costCents, Long source, Long monthNoSale, String status, String entry, Long bsl, Long history6, Long history12, Long history24, Long min, Long max) {
		this.partNo = partNo;
		this.qoh = qoh;
		this.costCents = costCents;
		this.source = source;
		this.monthNoSale = monthNoSale;
		this.status = status;
		this.entryDate = entry;
		this.bsl = bsl;
		this.history6 = history6;
		this.history12 = history12;
		this.history24 = history24;
		this.min = min;
		this.max = max;
	}
}
