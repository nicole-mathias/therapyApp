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
@Table(name = "summary_reports")
public class SummaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients client;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "period_start")
    private LocalDate periodStart;

    @Column(name = "period_end")
    private LocalDate periodEnd;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "mood_trend")
    private String moodTrend; // "improving", "declining", "stable"

    @Column(name = "average_mood_score")
    private double averageMoodScore;

    @Column(name = "crisis_indicators")
    private String crisisIndicators;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "sentiment_analysis", columnDefinition = "TEXT")
    private String sentimentAnalysis;

    @Column(name = "risk_level")
    private int riskLevel; // 0-5 scale

    @Column(name = "requires_attention")
    private boolean requiresAttention;

    @Column(name = "therapist_notes", columnDefinition = "TEXT")
    private String therapistNotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public SummaryReport() {
        this.createdAt = LocalDateTime.now();
    }

    public SummaryReport(Clients client, LocalDate reportDate, LocalDate periodStart, LocalDate periodEnd) {
        this.client = client;
        this.reportDate = reportDate;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Clients getClient() { return client; }
    public void setClient(Clients client) { this.client = client; }

    public LocalDate getReportDate() { return reportDate; }
    public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }

    public LocalDate getPeriodStart() { return periodStart; }
    public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }

    public LocalDate getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getMoodTrend() { return moodTrend; }
    public void setMoodTrend(String moodTrend) { this.moodTrend = moodTrend; }

    public double getAverageMoodScore() { return averageMoodScore; }
    public void setAverageMoodScore(double averageMoodScore) { this.averageMoodScore = averageMoodScore; }

    public String getCrisisIndicators() { return crisisIndicators; }
    public void setCrisisIndicators(String crisisIndicators) { this.crisisIndicators = crisisIndicators; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public String getSentimentAnalysis() { return sentimentAnalysis; }
    public void setSentimentAnalysis(String sentimentAnalysis) { this.sentimentAnalysis = sentimentAnalysis; }

    public int getRiskLevel() { return riskLevel; }
    public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }

    public boolean isRequiresAttention() { return requiresAttention; }
    public void setRequiresAttention(boolean requiresAttention) { this.requiresAttention = requiresAttention; }

    public String getTherapistNotes() { return therapistNotes; }
    public void setTherapistNotes(String therapistNotes) { this.therapistNotes = therapistNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 