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
import com.fullstack.therapy.repository.ClientsRepository;

@RestController
@RequestMapping("/therapy/management")
public class TherapyManagementController {

    @Autowired
    private ClientsRepository clientsRepository;

    // Get session count for a client
    @GetMapping("/client/{clientId}/sessions")
    public ResponseEntity<Map<String, Object>> getClientSessions(@PathVariable int clientId) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            
            Map<String, Object> response = new HashMap<>();
            if (clientOpt.isPresent()) {
                Clients client = clientOpt.get();
                response.put("success", true);
                response.put("sessionCount", client.getSessionCount());
                response.put("therapyStatus", client.getTherapyStatus());
                response.put("therapyEndDate", client.getTherapyEndDate());
                response.put("paymentNotificationSent", client.isPaymentNotificationSent());
            } else {
                response.put("success", false);
                response.put("message", "Client not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get client sessions: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // Increment session count (called when a chat session is started)
    @PostMapping("/client/{clientId}/increment-session")
    public ResponseEntity<Map<String, Object>> incrementSession(@PathVariable int clientId) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            
            Map<String, Object> response = new HashMap<>();
            if (clientOpt.isPresent()) {
                Clients client = clientOpt.get();
                client.setSessionCount((client.getSessionCount() != null ? client.getSessionCount() : 0) + 1);
                clientsRepository.save(client);
                
                response.put("success", true);
                response.put("sessionCount", client.getSessionCount());
                response.put("message", "Session count incremented");
            } else {
                response.put("success", false);
                response.put("message", "Client not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to increment session: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // End therapy for a client
    @PostMapping("/client/{clientId}/end-therapy")
    public ResponseEntity<Map<String, Object>> endTherapy(@PathVariable int clientId, @RequestBody EndTherapyRequest request) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            
            Map<String, Object> response = new HashMap<>();
            if (clientOpt.isPresent()) {
                Clients client = clientOpt.get();
                client.setTherapyStatus("COMPLETED");
                client.setTherapyEndDate(LocalDate.now());
                client.setPaymentNotificationSent(true);
                clientsRepository.save(client);
                
                // Calculate payment amount (example: $50 per session)
                int totalSessions = client.getSessionCount() != null ? client.getSessionCount() : 0;
                double totalAmount = totalSessions * 50.0;
                
                response.put("success", true);
                response.put("message", "Therapy ended successfully");
                response.put("totalSessions", totalSessions);
                response.put("totalAmount", totalAmount);
                response.put("paymentRequired", true);
            } else {
                response.put("success", false);
                response.put("message", "Client not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to end therapy: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // Get payment information for a client
    @GetMapping("/client/{clientId}/payment")
    public ResponseEntity<Map<String, Object>> getPaymentInfo(@PathVariable int clientId) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            
            Map<String, Object> response = new HashMap<>();
            if (clientOpt.isPresent()) {
                Clients client = clientOpt.get();
                int totalSessions = client.getSessionCount() != null ? client.getSessionCount() : 0;
                double totalAmount = totalSessions * 50.0; // $50 per session
                
                response.put("success", true);
                response.put("totalSessions", totalSessions);
                response.put("totalAmount", totalAmount);
                response.put("therapyStatus", client.getTherapyStatus());
                response.put("paymentNotificationSent", client.isPaymentNotificationSent());
                
                if ("COMPLETED".equals(client.getTherapyStatus())) {
                    response.put("paymentRequired", true);
                    response.put("message", "Payment required for completed therapy sessions");
                } else {
                    response.put("paymentRequired", false);
                    response.put("message", "Therapy is still active");
                }
            } else {
                response.put("success", false);
                response.put("message", "Client not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to get payment info: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // Mark payment as received
    @PostMapping("/client/{clientId}/mark-paid")
    public ResponseEntity<Map<String, Object>> markPaymentReceived(@PathVariable int clientId) {
        try {
            Optional<Clients> clientOpt = clientsRepository.findById(clientId);
            
            Map<String, Object> response = new HashMap<>();
            if (clientOpt.isPresent()) {
                Clients client = clientOpt.get();
                client.setPaymentNotificationSent(false); // Reset notification flag
                clientsRepository.save(client);
                
                response.put("success", true);
                response.put("message", "Payment marked as received");
            } else {
                response.put("success", false);
                response.put("message", "Client not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to mark payment: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public static class EndTherapyRequest {
        private String reason;
        private String notes;

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
} 