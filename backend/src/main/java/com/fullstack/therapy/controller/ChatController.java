package com.fullstack.therapy.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.therapy.model.ChatMessage;
import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.CrisisAlert;
import com.fullstack.therapy.repository.ChatMessageRepository;
import com.fullstack.therapy.repository.ClientsRepository;
import com.fullstack.therapy.repository.CrisisAlertRepository;
import com.fullstack.therapy.service.AITherapyService;
import com.fullstack.therapy.service.AITherapyService.CrisisDetectionResult;

@RestController
@RequestMapping("/therapy/chat")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private CrisisAlertRepository crisisAlertRepository;

    @Autowired
    private AITherapyService aiTherapyService;

    @PostMapping("/send")
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        try {
            // Get client
            Clients client = clientsRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

            // Create user message
            ChatMessage userMessage = new ChatMessage(client, request.getMessage(), false, request.getSessionId());
            chatMessageRepository.save(userMessage);

            // Get conversation history
            List<ChatMessage> conversationHistory = chatMessageRepository
                .findByClientAndSessionIdOrderByTimestampAsc(client, request.getSessionId());

            // Detect crisis
            CrisisDetectionResult crisisResult = aiTherapyService.detectCrisis(request.getMessage());
            userMessage.setCrisisDetected(crisisResult.isCrisisDetected());
            userMessage.setCrisisLevel(crisisResult.getCrisisLevel());
            chatMessageRepository.save(userMessage);

            // Create crisis alert if needed
            if (crisisResult.isCrisisDetected() && crisisResult.getCrisisLevel() >= 3) {
                CrisisAlert alert = new CrisisAlert(client, crisisResult.getCrisisLevel(), 
                    crisisResult.getCrisisType(), crisisResult.getDescription());
                crisisAlertRepository.save(alert);
            }

            // Generate AI response
            String aiResponse = aiTherapyService.generateTherapyResponse(request.getMessage(), client, conversationHistory);

            // Create AI message
            ChatMessage aiMessage = new ChatMessage(client, aiResponse, true, request.getSessionId());
            chatMessageRepository.save(aiMessage);

            return ResponseEntity.ok(new ChatResponse(aiResponse, crisisResult.isCrisisDetected(), 
                crisisResult.getCrisisLevel(), crisisResult.getCrisisType()));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/history/{clientId}/{sessionId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable int clientId, @PathVariable String sessionId) {
        try {
            Clients client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

            List<ChatMessage> messages = chatMessageRepository
                .findByClientAndSessionIdOrderByTimestampAsc(client, sessionId);

            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sessions/{clientId}")
    public ResponseEntity<List<String>> getClientSessions(@PathVariable int clientId) {
        try {
            Clients client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

            List<ChatMessage> allMessages = chatMessageRepository.findByClientOrderByTimestampAsc(client);
            
            // Extract unique session IDs
            List<String> sessions = allMessages.stream()
                .map(ChatMessage::getSessionId)
                .distinct()
                .toList();

            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/start-session/{clientId}")
    public ResponseEntity<SessionResponse> startSession(@PathVariable int clientId) {
        try {
            Clients client = clientsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

            // Increment session count
            client.setSessionCount(client.getSessionCount() + 1);
            clientsRepository.save(client);

            String sessionId = UUID.randomUUID().toString();
            
            return ResponseEntity.ok(new SessionResponse(sessionId, 
                "Welcome to your therapy session. How are you feeling today?"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Request/Response classes
    public static class ChatRequest {
        private int clientId;
        private String message;
        private String sessionId;

        // Getters and setters
        public int getClientId() { return clientId; }
        public void setClientId(int clientId) { this.clientId = clientId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    }

    public static class ChatResponse {
        private String message;
        private boolean crisisDetected;
        private int crisisLevel;
        private String crisisType;

        public ChatResponse(String message, boolean crisisDetected, int crisisLevel, String crisisType) {
            this.message = message;
            this.crisisDetected = crisisDetected;
            this.crisisLevel = crisisLevel;
            this.crisisType = crisisType;
        }

        // Getters
        public String getMessage() { return message; }
        public boolean isCrisisDetected() { return crisisDetected; }
        public int getCrisisLevel() { return crisisLevel; }
        public String getCrisisType() { return crisisType; }
    }

    public static class SessionResponse {
        private String sessionId;
        private String welcomeMessage;

        public SessionResponse(String sessionId, String welcomeMessage) {
            this.sessionId = sessionId;
            this.welcomeMessage = welcomeMessage;
        }

        // Getters
        public String getSessionId() { return sessionId; }
        public String getWelcomeMessage() { return welcomeMessage; }
    }
} 