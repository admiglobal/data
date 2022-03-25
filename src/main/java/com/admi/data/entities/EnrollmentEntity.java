package com.admi.data.entities;

import java.sql.Time;

public interface EnrollmentEntity {

	Long getDealerId();
	void setDealerId(Long dealerId);

	String getPaCode();
	void setPaCode(String paCode);

	String getDealershipName();
	void setDealershipName(String dealershipName);

	Long getSme();
	void setSme(Long sme);

	String getFirstname();
	void setFirstname(String firstname);

	String getLastname();
	void setLastname(String lastname);

	Time getEnrollmentDate();
	void setEnrollmentDate(Time enrollmentDate);

	long getEnrollmentId();
	void setEnrollmentId(long enrollmentId);

	Time getEffectiveEndDate();
	void setEffectiveEndDate(Time effectiveEndDate);

	String getEmailAddress();
	void setEmailAddress(String emailAddress);

	String getPhoneNumber();
	void setPhoneNumber(String phoneNumber);

	Integer getDms();
	void setDms(int dms);

	DealerMasterEntity getDealer();
	void setDealer(DealerMasterEntity dealer);

}