package com.fullstack.therapy.model;


import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // Foreign key to Clients Table
    // client_id is a foreign key to another table (clients.id), 
    // it’s better to model it as an object reference using @ManyToOne
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Clients client;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "sentiment", columnDefinition = "TEXT")
    private String sentiment;

    // Maybe this should be a score?
    @Column(name = "goal_progress")
    private int goalProgress;

    // default constructor
    public Session(){
    }

    // Constructor without ID
    // but included client - because this enforces every session to have a client
    // if client wasnt included in the constructor, then we would have to construct a Client obj sepearelt
    // and then use session.setClient(client)
    public Session(Clients client,LocalDate date, String notes, String summary, String sentiment, int goalProgress){
        this.client = client;
        this.date = date;
        this.notes = notes;
        this.summary = summary;
        this.sentiment = sentiment;
        this.goalProgress = goalProgress;
    }


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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public int getGoalProgress() {
        return goalProgress;
    }

    public void setGoalProgress(int goalProgress) {
        this.goalProgress = goalProgress;
    }
  
}
