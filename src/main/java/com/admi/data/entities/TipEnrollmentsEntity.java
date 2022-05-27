package com.admi.data.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TIP_ENROLLMENTS_V", schema = "ADMI")
public class TipEnrollmentsEntity {
	private Long dealerId;
	private String paCode;
	private String dealershipName;
	private Integer sme;
	private String firstname;
	private String lastname;
	private LocalDate enrollmentDate;
	private Long enrollmentId;
	private LocalDate effectiveEndDate;
	private String emailAddress;
	private String phoneNumber;
	private Integer dms;

	@Id
	@Basic
	@Column(name = "DEALER_ID", nullable = true, precision = 0)
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
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
	@Column(name = "SME", nullable = true, precision = 0)
	public Integer getSme() {
		return sme;
	}

	public void setSme(Integer sme) {
		this.sme = sme;
	}

	@Basic
	@Column(name = "FIRSTNAME", nullable = true, length = 30)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Basic
	@Column(name = "LASTNAME", nullable = true, length = 40)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Basic
	@Column(name = "ENROLLMENT_DATE", nullable = true)
	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	@Basic
	@Column(name = "ENROLLMENT_ID", nullable = false, precision = 0)
	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	@Basic
	@Column(name = "EFFECTIVE_END_DATE", nullable = true)
	public LocalDate getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(LocalDate effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	@Basic
	@Column(name = "EMAIL_ADDRESS", nullable = true, length = 100)
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Basic
	@Column(name = "PHONE_NUMBER", nullable = true, length = 50)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Basic
	@Column(name = "DMS", nullable = true, length = 20)
	public Integer getDms() {
		return dms;
	}

	public void setDms(Integer dms) {
		this.dms = dms;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TipEnrollmentsEntity that = (TipEnrollmentsEntity) o;

		if (dealerId != null ? !dealerId.equals(that.dealerId) : that.dealerId != null) return false;
		if (paCode != null ? !paCode.equals(that.paCode) : that.paCode != null) return false;
		if (dealershipName != null ? !dealershipName.equals(that.dealershipName) : that.dealershipName != null)
			return false;
		if (sme != null ? !sme.equals(that.sme) : that.sme != null) return false;
		if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
		if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
		if (enrollmentDate != null ? !enrollmentDate.equals(that.enrollmentDate) : that.enrollmentDate != null)
			return false;
		if (enrollmentId != null ? !enrollmentId.equals(that.enrollmentId) : that.enrollmentId != null) return false;
		if (effectiveEndDate != null ? !effectiveEndDate.equals(that.effectiveEndDate) : that.effectiveEndDate != null)
			return false;
		if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
		if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
		if (dms != null ? !dms.equals(that.dms) : that.dms != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dealerId != null ? dealerId.hashCode() : 0;
		result = 31 * result + (paCode != null ? paCode.hashCode() : 0);
		result = 31 * result + (dealershipName != null ? dealershipName.hashCode() : 0);
		result = 31 * result + (sme != null ? sme.hashCode() : 0);
		result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
		result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
		result = 31 * result + (enrollmentDate != null ? enrollmentDate.hashCode() : 0);
		result = 31 * result + (enrollmentId != null ? enrollmentId.hashCode() : 0);
		result = 31 * result + (effectiveEndDate != null ? effectiveEndDate.hashCode() : 0);
		result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
		result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
		result = 31 * result + (dms != null ? dms.hashCode() : 0);
		return result;
	}
}
