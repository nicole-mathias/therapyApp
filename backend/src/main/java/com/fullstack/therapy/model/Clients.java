// data model defines the Database structure for each table
// DB tablename - clients

package com.fullstack.therapy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity annotation indicates that the class is a persistent Java class.
@Entity
@Table(name = "clients")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Column(name = "emergency_contact_relationship")
    private String emergencyContactRelationship;

    @Column(name = "therapist_notes", columnDefinition = "TEXT")
    private String therapistNotes;

    @Column(name = "risk_level")
    private Integer riskLevel = 0; // 0-5 scale, 5 being highest risk
    
    @Column(name = "session_count")
    private Integer sessionCount = 0;
    
    @Column(name = "therapy_status")
    private String therapyStatus = "ACTIVE"; // ACTIVE, INACTIVE, COMPLETED
    
    @Column(name = "therapy_end_date")
    private java.time.LocalDate therapyEndDate;
    
    @Column(name = "payment_notification_sent")
    private Boolean paymentNotificationSent = false;
    
    // Default constructor required by JPA
    public Clients(){

    }

    public Clients(String name, Integer age, String diagnosis) {
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.riskLevel = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getTherapistNotes() {
        return therapistNotes;
    }

    public void setTherapistNotes(String therapistNotes) {
        this.therapistNotes = therapistNotes;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public Integer getSessionCount() {
        return sessionCount != null ? sessionCount : 0;
    }
    
    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount != null ? sessionCount : 0;
    }
    
    public String getTherapyStatus() {
        return therapyStatus;
    }
    
    public void setTherapyStatus(String therapyStatus) {
        this.therapyStatus = therapyStatus;
    }
    
    public java.time.LocalDate getTherapyEndDate() {
        return therapyEndDate;
    }
    
    public void setTherapyEndDate(java.time.LocalDate therapyEndDate) {
        this.therapyEndDate = therapyEndDate;
    }
    
    public Boolean isPaymentNotificationSent() {
        return paymentNotificationSent != null ? paymentNotificationSent : false;
    }
    
    public void setPaymentNotificationSent(Boolean paymentNotificationSent) {
        this.paymentNotificationSent = paymentNotificationSent != null ? paymentNotificationSent : false;
    }
}
