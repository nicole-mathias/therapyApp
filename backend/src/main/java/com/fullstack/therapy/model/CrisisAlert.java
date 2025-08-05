package com.fullstack.therapy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "crisis_alerts")
public class CrisisAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients client;

    @Column(name = "alert_level")
    private int alertLevel; // 1-5 scale, 5 being highest emergency

    @Column(name = "alert_type")
    private String alertType; // SUICIDE, SELF_HARM, VIOLENCE, etc.

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "resolved")
    private boolean resolved;

    @Column(name = "emergency_contact_called")
    private boolean emergencyContactCalled;

    @Column(name = "emergency_services_called")
    private boolean emergencyServicesCalled;

    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    public CrisisAlert() {
    }

    public CrisisAlert(Clients client, int alertLevel, String alertType, String description) {
        this.client = client;
        this.alertLevel = alertLevel;
        this.alertType = alertType;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.resolved = false;
        this.emergencyContactCalled = false;
        this.emergencyServicesCalled = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public int getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(int alertLevel) {
        this.alertLevel = alertLevel;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean isEmergencyContactCalled() {
        return emergencyContactCalled;
    }

    public void setEmergencyContactCalled(boolean emergencyContactCalled) {
        this.emergencyContactCalled = emergencyContactCalled;
    }

    public boolean isEmergencyServicesCalled() {
        return emergencyServicesCalled;
    }

    public void setEmergencyServicesCalled(boolean emergencyServicesCalled) {
        this.emergencyServicesCalled = emergencyServicesCalled;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }
} 