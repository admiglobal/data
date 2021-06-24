package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MC_PARTS", schema = "ADMI", catalog = "")
public class McPartsEntity {
	private String partno;
	private String code;
	private String description;
	private String service;
	private String ocPartno;
	private Float price;
	private Float installerPrice;
	private Long sellQty;
	private Long jobQty;
	private String motorcraft;
	private LocalDateTime released;
	private String fitment;
	private Long phase;
	private String app;
	private Long pair;
	private Float fadPrice;

	@Id
	@Column(name = "PARTNO", nullable = false, length = 20)
	public String getPartno() {
		return partno;
	}

	public void setPartno(String partno) {
		this.partno = partno;
	}

	@Basic
	@Column(name = "CODE", nullable = true, length = 20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Basic
	@Column(name = "DESCRIPTION", nullable = true, length = 20)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "SERVICE", nullable = true, length = 20)
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Basic
	@Column(name = "OC_PARTNO", nullable = true, length = 20)
	public String getOcPartno() {
		return ocPartno;
	}

	public void setOcPartno(String ocPartno) {
		this.ocPartno = ocPartno;
	}

	@Basic
	@Column(name = "PRICE", nullable = true, precision = 0)
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Basic
	@Column(name = "INSTALLER_PRICE", nullable = true, precision = 0)
	public Float getInstallerPrice() {
		return installerPrice;
	}

	public void setInstallerPrice(Float installerPrice) {
		this.installerPrice = installerPrice;
	}

	@Basic
	@Column(name = "SELL_QTY", nullable = true, precision = 0)
	public Long getSellQty() {
		return sellQty;
	}

	public void setSellQty(Long sellQty) {
		this.sellQty = sellQty;
	}

	@Basic
	@Column(name = "JOB_QTY", nullable = true, precision = 0)
	public Long getJobQty() {
		return jobQty;
	}

	public void setJobQty(Long jobQty) {
		this.jobQty = jobQty;
	}

	@Basic
	@Column(name = "MOTORCRAFT", nullable = true, length = 20)
	public String getMotorcraft() {
		return motorcraft;
	}

	public void setMotorcraft(String motorcraft) {
		this.motorcraft = motorcraft;
	}

	@Basic
	@Column(name = "RELEASED", nullable = true)
	public LocalDateTime getReleased() {
		return released;
	}

	public void setReleased(LocalDateTime released) {
		this.released = released;
	}

	@Basic
	@Column(name = "FITMENT", nullable = true, length = 50)
	public String getFitment() {
		return fitment;
	}

	public void setFitment(String fitment) {
		this.fitment = fitment;
	}

	@Basic
	@Column(name = "PHASE", nullable = true, precision = 0)
	public Long getPhase() {
		return phase;
	}

	public void setPhase(Long phase) {
		this.phase = phase;
	}

	@Basic
	@Column(name = "APP", nullable = true, length = 350)
	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Basic
	@Column(name = "PAIR", nullable = true, precision = 0)
	public Long getPair() {
		return pair;
	}

	public void setPair(Long pair) {
		this.pair = pair;
	}

	@Basic
	@Column(name = "FAD_PRICE", nullable = true, precision = 0)
	public Float getFadPrice() {
		return fadPrice;
	}

	public void setFadPrice(Float fadPrice) {
		this.fadPrice = fadPrice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		McPartsEntity that = (McPartsEntity) o;

		if (partno != null ? !partno.equals(that.partno) : that.partno != null) return false;
		if (code != null ? !code.equals(that.code) : that.code != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (service != null ? !service.equals(that.service) : that.service != null) return false;
		if (ocPartno != null ? !ocPartno.equals(that.ocPartno) : that.ocPartno != null) return false;
		if (price != null ? !price.equals(that.price) : that.price != null) return false;
		if (installerPrice != null ? !installerPrice.equals(that.installerPrice) : that.installerPrice != null)
			return false;
		if (sellQty != null ? !sellQty.equals(that.sellQty) : that.sellQty != null) return false;
		if (jobQty != null ? !jobQty.equals(that.jobQty) : that.jobQty != null) return false;
		if (motorcraft != null ? !motorcraft.equals(that.motorcraft) : that.motorcraft != null) return false;
		if (released != null ? !released.equals(that.released) : that.released != null) return false;
		if (fitment != null ? !fitment.equals(that.fitment) : that.fitment != null) return false;
		if (phase != null ? !phase.equals(that.phase) : that.phase != null) return false;
		if (app != null ? !app.equals(that.app) : that.app != null) return false;
		if (pair != null ? !pair.equals(that.pair) : that.pair != null) return false;
		if (fadPrice != null ? !fadPrice.equals(that.fadPrice) : that.fadPrice != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = partno != null ? partno.hashCode() : 0;
		result = 31 * result + (code != null ? code.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (service != null ? service.hashCode() : 0);
		result = 31 * result + (ocPartno != null ? ocPartno.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (installerPrice != null ? installerPrice.hashCode() : 0);
		result = 31 * result + (sellQty != null ? sellQty.hashCode() : 0);
		result = 31 * result + (jobQty != null ? jobQty.hashCode() : 0);
		result = 31 * result + (motorcraft != null ? motorcraft.hashCode() : 0);
		result = 31 * result + (released != null ? released.hashCode() : 0);
		result = 31 * result + (fitment != null ? fitment.hashCode() : 0);
		result = 31 * result + (phase != null ? phase.hashCode() : 0);
		result = 31 * result + (app != null ? app.hashCode() : 0);
		result = 31 * result + (pair != null ? pair.hashCode() : 0);
		result = 31 * result + (fadPrice != null ? fadPrice.hashCode() : 0);
		return result;
	}
}
