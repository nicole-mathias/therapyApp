package com.fullstack.therapy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.CrisisAlert;
import com.fullstack.therapy.repository.ClientsRepository;
import com.fullstack.therapy.repository.CrisisAlertRepository;

@RestController
@RequestMapping("/therapy/crisis")
public class CrisisController {

    @Autowired
    private CrisisAlertRepository crisisAlertRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @GetMapping("/active")
    public ResponseEntity<List<CrisisAlert>> getActiveAlerts() {
        try {
            List<CrisisAlert> activeAlerts = crisisAlertRepository.findByResolvedFalse();
            return ResponseEntity.ok(activeAlerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CrisisAlert>> getClientAlerts(@PathVariable int clientId) {
        try {
            Clients client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

            List<CrisisAlert> alerts = crisisAlertRepository.findByClientOrderByTimestampDesc(client);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/resolve/{alertId}")
    public ResponseEntity<String> resolveAlert(@PathVariable int alertId, @RequestBody ResolutionRequest request) {
        try {
            CrisisAlert alert = crisisAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

            alert.setResolved(true);
            alert.setResolutionNotes(request.getResolutionNotes());
            crisisAlertRepository.save(alert);

            return ResponseEntity.ok("Alert resolved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/emergency-contact/{alertId}")
    public ResponseEntity<String> callEmergencyContact(@PathVariable int alertId) {
        try {
            CrisisAlert alert = crisisAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

            Clients client = alert.getClient();
            
            // In a real application, this would integrate with a phone service
            // For now, we'll just mark it as called
            alert.setEmergencyContactCalled(true);
            crisisAlertRepository.save(alert);

            return ResponseEntity.ok("Emergency contact called for " + client.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/emergency-services/{alertId}")
    public ResponseEntity<String> callEmergencyServices(@PathVariable int alertId) {
        try {
            CrisisAlert alert = crisisAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

            Clients client = alert.getClient();
            
            // In a real application, this would call 911 or emergency services
            // For now, we'll just mark it as called
            alert.setEmergencyServicesCalled(true);
            crisisAlertRepository.save(alert);

            return ResponseEntity.ok("Emergency services contacted for " + client.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/high-risk")
    public ResponseEntity<List<CrisisAlert>> getHighRiskAlerts() {
        try {
            List<CrisisAlert> highRiskAlerts = crisisAlertRepository.findByAlertLevelGreaterThan(3);
            return ResponseEntity.ok(highRiskAlerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Request class
    public static class ResolutionRequest {
        private String resolutionNotes;

        public String getResolutionNotes() { return resolutionNotes; }
        public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }
    }
} 