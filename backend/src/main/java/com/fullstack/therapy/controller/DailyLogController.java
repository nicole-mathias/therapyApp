package com.fullstack.therapy.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.DailyLog;
import com.fullstack.therapy.repository.ClientsRepository;
import com.fullstack.therapy.repository.DailyLogRepository;
import com.fullstack.therapy.service.AITherapyService;

@RestController
@RequestMapping("/therapy/daily-logs")
public class DailyLogController {

    @Autowired
    private DailyLogRepository dailyLogRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private AITherapyService aiService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createDailyLog(@RequestBody DailyLogRequest request) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(request.getClientId());
            if (!clientOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Client not found");
                return ResponseEntity.ok(response);
            }

            Clients client = clientOpt.get();
            LocalDate logDate = request.getLogDate() != null ? 
                LocalDate.parse(request.getLogDate()) : LocalDate.now();

            // Check if log already exists for this date
            DailyLog existingLog = dailyLogRepository.findByClientAndLogDate(client, logDate);
            if (existingLog != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Daily log already exists for this date");
                return ResponseEntity.ok(response);
            }

            DailyLog dailyLog = new DailyLog(client, logDate);
            dailyLog.setMoodScore(request.getMoodScore());
            dailyLog.setMoodDescription(request.getMoodDescription());
            dailyLog.setActivities(request.getActivities());
            dailyLog.setThoughts(request.getThoughts());
            dailyLog.setSleepHours(request.getSleepHours());
            dailyLog.setMedicationTaken(request.isMedicationTaken());
            dailyLog.setTherapySession(request.isTherapySession());

            // Analyze content for crisis detection and sentiment
            String contentToAnalyze = String.format(
                "Mood: %d/10. Description: %s. Activities: %s. Thoughts: %s",
                request.getMoodScore(),
                request.getMoodDescription(),
                request.getActivities(),
                request.getThoughts()
            );

            // Get AI analysis
            String aiResponse = aiService.generateTherapyResponse(contentToAnalyze, client, null);
            dailyLog.setAiSummary(aiResponse);

            // Simple crisis detection based on mood score and keywords
            int crisisLevel = calculateCrisisLevel(request);
            dailyLog.setCrisisLevel(crisisLevel);

            // Simple sentiment score (0-1 scale)
            double sentimentScore = calculateSentimentScore(request);
            dailyLog.setSentimentScore(sentimentScore);

            // Extract keywords
            String keywords = extractKeywords(request);
            dailyLog.setKeywords(keywords);

            DailyLog savedLog = dailyLogRepository.save(dailyLog);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("dailyLog", savedLog);
            response.put("message", "Daily log saved successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error saving daily log: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<DailyLog>> getClientLogs(@PathVariable int clientId) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            if (!clientOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            List<DailyLog> logs = dailyLogRepository.findByClientOrderByLogDateDesc(clientOpt.get());
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/client/{clientId}/date/{date}")
    public ResponseEntity<DailyLog> getClientLogByDate(
            @PathVariable int clientId, 
            @PathVariable String date) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            if (!clientOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            LocalDate logDate = LocalDate.parse(date);
            DailyLog log = dailyLogRepository.findByClientAndLogDate(clientOpt.get(), logDate);
            
            if (log == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(log);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/crisis")
    public ResponseEntity<List<DailyLog>> getCrisisLogs() {
        try {
            List<DailyLog> crisisLogs = dailyLogRepository.findByCrisisLevelGreaterThan(3);
            return ResponseEntity.ok(crisisLogs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private int calculateCrisisLevel(DailyLogRequest request) {
        int crisisLevel = 0;
        
        // Check mood score
        if (request.getMoodScore() <= 2) crisisLevel += 2;
        else if (request.getMoodScore() <= 4) crisisLevel += 1;

        // Check for crisis keywords in thoughts and description
        String content = (request.getThoughts() + " " + request.getMoodDescription()).toLowerCase();
        String[] crisisKeywords = {
            "suicide", "kill myself", "want to die", "end it all", "no reason to live",
            "hopeless", "worthless", "can't go on", "give up", "self harm",
            "cut myself", "hurt myself", "pain", "suffering", "despair"
        };

        for (String keyword : crisisKeywords) {
            if (content.contains(keyword)) {
                crisisLevel += 2;
                break;
            }
        }

        return Math.min(crisisLevel, 5); // Max crisis level is 5
    }

    private double calculateSentimentScore(DailyLogRequest request) {
        // Simple sentiment calculation based on mood score
        // Convert 1-10 mood score to 0-1 sentiment score
        return (request.getMoodScore() - 1) / 9.0;
    }

    private String extractKeywords(DailyLogRequest request) {
        // Simple keyword extraction
        String content = (request.getThoughts() + " " + request.getMoodDescription() + " " + request.getActivities()).toLowerCase();
        String[] commonKeywords = {
            "anxiety", "depression", "stress", "happy", "sad", "angry", "tired", "energetic",
            "sleep", "work", "family", "friends", "exercise", "meditation", "therapy",
            "medication", "appointment", "doctor", "hospital", "emergency"
        };

        StringBuilder keywords = new StringBuilder();
        for (String keyword : commonKeywords) {
            if (content.contains(keyword)) {
                if (keywords.length() > 0) keywords.append(", ");
                keywords.append(keyword);
            }
        }

        return keywords.toString();
    }

    // Request class
    public static class DailyLogRequest {
        private int clientId;
        private String logDate;
        private int moodScore;
        private String moodDescription;
        private String activities;
        private String thoughts;
        private Double sleepHours;
        private boolean medicationTaken;
        private boolean therapySession;

        // Getters and Setters
        public int getClientId() { return clientId; }
        public void setClientId(int clientId) { this.clientId = clientId; }

        public String getLogDate() { return logDate; }
        public void setLogDate(String logDate) { this.logDate = logDate; }

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
    }
} 