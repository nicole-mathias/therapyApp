package com.fullstack.therapy.model;

import java.time.LocalDate;
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
@Table(name = "daily_logs")
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients client;

    @Column(name = "log_date")
    private LocalDate logDate;

    @Column(name = "mood_score")
    private int moodScore; // 1-10 scale

    @Column(name = "mood_description")
    private String moodDescription;

    @Column(name = "activities", columnDefinition = "TEXT")
    private String activities;

    @Column(name = "thoughts", columnDefinition = "TEXT")
    private String thoughts;

    @Column(name = "sleep_hours")
    private Double sleepHours;

    @Column(name = "medication_taken")
    private boolean medicationTaken;

    @Column(name = "therapy_session")
    private boolean therapySession;

    @Column(name = "crisis_level")
    private int crisisLevel; // 0-5 scale

    @Column(name = "sentiment_score")
    private double sentimentScore;

    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public DailyLog() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public DailyLog(Clients client, LocalDate logDate) {
        this.client = client;
        this.logDate = logDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Clients getClient() { return client; }
    public void setClient(Clients client) { this.client = client; }

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public int getMoodScore() { return moodScore; }
    public void setMoodScore(int moodScore) { this.moodScore = moodScore; }

    public String getMoodDescription() { return moodDescription; }
    public void setMoodDescription(String moodDescription) { this.moodDescription = moodDescription; }

    public String getActivities() { return activities; }
    public void setActivities(String activities) { this.activities = activities; }

    public String getThoughts() { return thoughts; }
    public void setThoughts(String thoughts) { this.thoughts = thoughts; }

    public Double getSleepHours() { return sleepHours; }
    public void setSleepHours(Double sleepHours) { this.sleepHours = sleepHours; }

    public boolean isMedicationTaken() { return medicationTaken; }
    public void setMedicationTaken(boolean medicationTaken) { this.medicationTaken = medicationTaken; }

    public boolean isTherapySession() { return therapySession; }
    public void setTherapySession(boolean therapySession) { this.therapySession = therapySession; }

    public int getCrisisLevel() { return crisisLevel; }
    public void setCrisisLevel(int crisisLevel) { this.crisisLevel = crisisLevel; }

    public double getSentimentScore() { return sentimentScore; }
    public void setSentimentScore(double sentimentScore) { this.sentimentScore = sentimentScore; }

    public String getAiSummary() { return aiSummary; }
    public void setAiSummary(String aiSummary) { this.aiSummary = aiSummary; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 