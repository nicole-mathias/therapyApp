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
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients client;

    @Column(name = "message_text", columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "is_from_ai")
    private boolean isFromAI;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "crisis_detected")
    private boolean crisisDetected;

    @Column(name = "crisis_level")
    private int crisisLevel; // 0-5 scale, 5 being highest risk

    @Column(name = "sentiment_score")
    private double sentimentScore;

    @Column(name = "session_id")
    private String sessionId; // To group messages by therapy session

    public ChatMessage() {
    }

    public ChatMessage(Clients client, String messageText, boolean isFromAI, String sessionId) {
        this.client = client;
        this.messageText = messageText;
        this.isFromAI = isFromAI;
        this.sessionId = sessionId;
        this.timestamp = LocalDateTime.now();
        this.crisisDetected = false;
        this.crisisLevel = 0;
        this.sentimentScore = 0.0;
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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isFromAI() {
        return isFromAI;
    }

    public void setFromAI(boolean fromAI) {
        isFromAI = fromAI;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isCrisisDetected() {
        return crisisDetected;
    }

    public void setCrisisDetected(boolean crisisDetected) {
        this.crisisDetected = crisisDetected;
    }

    public int getCrisisLevel() {
        return crisisLevel;
    }

    public void setCrisisLevel(int crisisLevel) {
        this.crisisLevel = crisisLevel;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
} 