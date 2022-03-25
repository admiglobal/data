package com.admi.data.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "AIP_ENROLLMENTS_V", schema = "ADMI")
public class AipEnrollmentsEntity implements EnrollmentEntity {
    private Long dealerId;
    private String paCode;
    private String dealershipName;
    private Long sme;
    private String firstname;
    private String lastname;
    private Time enrollmentDate;
    private long enrollmentId;
    private Time effectiveEndDate;
    private String emailAddress;
    private String phoneNumber;
    private int dms;

    private DealerMasterEntity dealer;

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
    public Long getSme() {
        return sme;
    }

    public void setSme(Long sme) {
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
    public Time getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Time enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Basic
    @Column(name = "ENROLLMENT_ID", nullable = false, precision = 0)
    public long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    @Basic
    @Column(name = "EFFECTIVE_END_DATE", nullable = true)
    public Time getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Time effectiveEndDate) {
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
    @Column(name = "DMS", nullable = true, precision = 0)
    public Integer getDms() {
        return dms;
    }

    public void setDms(int dms) {
        this.dms = dms;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DEALER_ID", referencedColumnName = "dealerId")
    public DealerMasterEntity getDealer() {
        return dealer;
    }

    public void setDealer(DealerMasterEntity dealer) {
        this.dealer = dealer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AipEnrollmentsEntity that = (AipEnrollmentsEntity) o;
        return enrollmentId == that.enrollmentId &&
                Objects.equals(dealerId, that.dealerId) &&
                Objects.equals(paCode, that.paCode) &&
                Objects.equals(dealershipName, that.dealershipName) &&
                Objects.equals(sme, that.sme) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(enrollmentDate, that.enrollmentDate) &&
                Objects.equals(effectiveEndDate, that.effectiveEndDate) &&
                Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(phoneNumber, that.phoneNumber) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealerId, paCode, dealershipName, sme, firstname, lastname, enrollmentDate, enrollmentId, effectiveEndDate, emailAddress, phoneNumber);
    }
}
