package com.fullstack.therapy.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fullstack.therapy.model.ChatMessage;
import com.fullstack.therapy.model.Clients;

@Service
public class AITherapyService {

    @Value("${huggingface.api.url:https://api-inference.huggingface.co/models/gpt2}")
    private String HF_API_URL;
    
    @Value("${huggingface.api.token:}")
    private String HF_API_TOKEN;
    
    private static final String[] THERAPY_RESPONSES = {
        "I hear you, and I want you to know that your feelings are valid. Can you tell me more about what's been on your mind?",
        "It sounds like you're going through a really difficult time. How have you been coping with these feelings?",
        "I appreciate you sharing this with me. What do you think triggered these feelings?",
        "That must be really challenging. Have you tried any techniques that have helped you in the past?",
        "I'm here to listen. What would be most helpful for you right now?",
        "Your feelings matter, and it's okay to not be okay. What kind of support do you feel you need?",
        "I can sense this is really affecting you. How long have you been feeling this way?",
        "It takes courage to talk about these things. What would you like to focus on today?",
        "I want to understand better. Can you describe what this feels like for you?",
        "Thank you for trusting me with this. What's one small thing that might help you feel a bit better?",
        "I understand this is really hard for you. What would make you feel supported right now?",
        "Your experience is important. Can you help me understand what you're going through?",
        "It's okay to feel this way. What do you think would help you feel a little better?",
        "I'm here to support you. What's been the most challenging part of this for you?",
        "Thank you for being open with me. How can I best help you today?"
    };

    public String generateTherapyResponse(String userMessage, Clients client, List<ChatMessage> conversationHistory) {
        System.out.println("Generating therapy response for: " + userMessage);
        
        try {
            // Try to use real AI model first
            String aiResponse = callHuggingFaceAPI(userMessage, client, conversationHistory);
            System.out.println("Generated AI response: " + aiResponse);
            return aiResponse;
        } catch (Exception e) {
            System.out.println("Hugging Face API failed: " + e.getMessage());
            // Fallback to intelligent response generation
            String intelligentResponse = generateIntelligentResponse(userMessage, client, conversationHistory);
            System.out.println("Using intelligent fallback: " + intelligentResponse);
            return intelligentResponse;
        }
    }

    private String generateIntelligentResponse(String userMessage, Clients client, List<ChatMessage> conversationHistory) {
        String lowerMessage = userMessage.toLowerCase();
        
        // Analyze the user's emotional state and context
        String emotionalState = analyzeEmotionalState(lowerMessage);
        String context = analyzeContext(lowerMessage, conversationHistory);
        
        // Generate personalized response based on analysis
        return generatePersonalizedResponse(userMessage, emotionalState, context, client);
    }
    
    private String analyzeEmotionalState(String message) {
        if (message.contains("sad") || message.contains("depressed") || message.contains("hopeless") || message.contains("down")) {
            return "DEPRESSED";
        } else if (message.contains("anxious") || message.contains("worried") || message.contains("nervous") || message.contains("panic")) {
            return "ANXIOUS";
        } else if (message.contains("angry") || message.contains("frustrated") || message.contains("mad") || message.contains("rage")) {
            return "ANGRY";
        } else if (message.contains("lonely") || message.contains("alone") || message.contains("isolated")) {
            return "LONELY";
        } else if (message.contains("stress") || message.contains("overwhelmed") || message.contains("pressure")) {
            return "STRESSED";
        } else if (message.contains("happy") || message.contains("good") || message.contains("better")) {
            return "POSITIVE";
        } else {
            return "NEUTRAL";
        }
    }
    
    private String analyzeContext(String message, List<ChatMessage> conversationHistory) {
        // Analyze conversation history for context
        if (conversationHistory.size() > 0) {
            String lastMessage = conversationHistory.get(conversationHistory.size() - 1).getMessageText().toLowerCase();
            if (lastMessage.contains("relationship") || message.contains("relationship")) {
                return "RELATIONSHIP_ISSUES";
            } else if (lastMessage.contains("work") || message.contains("work")) {
                return "WORK_STRESS";
            } else if (lastMessage.contains("family") || message.contains("family")) {
                return "FAMILY_ISSUES";
            }
        }
        return "GENERAL";
    }
    
    private String generatePersonalizedResponse(String originalMessage, String emotionalState, String context, Clients client) {
        StringBuilder response = new StringBuilder();
        
        // Handle simple greetings and neutral messages
        if (originalMessage.toLowerCase().contains("hello") || originalMessage.toLowerCase().contains("hi") || originalMessage.length() < 10) {
            response.append("Hello! I'm here to support you today. ");
            response.append("How are you feeling right now? ");
            response.append("What would you like to talk about? ");
            return response.toString();
        }
        
        // Start with empathy based on emotional state
        switch (emotionalState) {
            case "DEPRESSED":
                response.append("I can sense that you're feeling really down right now. Depression can be incredibly isolating and overwhelming. ");
                response.append("It takes courage to reach out and talk about these feelings. ");
                break;
            case "ANXIOUS":
                response.append("I hear that you're feeling anxious, and I want you to know that anxiety can feel really overwhelming. ");
                response.append("Your feelings are valid, and it's okay to feel this way. ");
                break;
            case "ANGRY":
                response.append("I understand that you're feeling angry, and that's a natural response to difficult situations. ");
                response.append("Anger often tells us when something isn't right or fair. ");
                break;
            case "LONELY":
                response.append("Loneliness can be one of the most painful feelings to experience. ");
                response.append("You're reaching out, and that's a brave step toward connection. ");
                break;
            case "STRESSED":
                response.append("Stress can feel like it's taking over everything, making even small tasks feel impossible. ");
                response.append("It sounds like you're dealing with a lot right now. ");
                break;
            case "POSITIVE":
                response.append("It's wonderful to hear that you're feeling better! ");
                response.append("Progress, no matter how small, is still progress. ");
                break;
            default:
                response.append("I hear you, and I want you to know that your feelings matter. ");
                break;
        }
        
        // Add context-specific guidance
        switch (context) {
            case "RELATIONSHIP_ISSUES":
                response.append("Relationships can be both wonderful and challenging. ");
                response.append("What aspect of this relationship is most affecting you right now? ");
                break;
            case "WORK_STRESS":
                response.append("Work stress can significantly impact our overall well-being. ");
                response.append("What's been the most challenging part of work for you? ");
                break;
            case "FAMILY_ISSUES":
                response.append("Family dynamics can be complex and emotionally charged. ");
                response.append("How has this been affecting your daily life? ");
                break;
            default:
                response.append("Can you tell me more about what's been on your mind? ");
                break;
        }
        
        // Add personalized question based on the original message
        if (originalMessage.contains("?")) {
            response.append("I want to understand better - can you help me see this from your perspective? ");
        } else if (originalMessage.length() < 20) {
            response.append("What would be most helpful for you right now? ");
        } else {
            response.append("What do you think would help you feel a little better? ");
        }
        
        return response.toString();
    }

    private String callHuggingFaceAPI(String userMessage, Clients client, List<ChatMessage> conversationHistory) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        
        // Build a comprehensive context for the AI model
        StringBuilder context = new StringBuilder();
        context.append("You are a compassionate therapist. Respond naturally and empathetically to the client's message. ");
        context.append("Keep responses conversational, supportive, and under 100 words. ");
        context.append("Focus on understanding, validation, and gentle guidance. ");
        context.append("Client message: ").append(userMessage).append("\n");
        context.append("Therapist response: ");

        // Prepare request for the AI model
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(Map.of(
            "inputs", context.toString(),
            "parameters", Map.of(
                "max_new_tokens", 80,
                "temperature", 0.8,
                "top_p", 0.9,
                "do_sample", true,
                "pad_token_id", 50256,
                "repetition_penalty", 1.1
            )
        ));

        System.out.println("Sending request to Hugging Face API...");
        System.out.println("Request body: " + requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HF_API_URL))
            .header("Authorization", "Bearer " + HF_API_TOKEN)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println("Response status: " + response.statusCode());
        System.out.println("Response body: " + response.body());
        
        if (response.statusCode() == 200) {
            JsonNode responseNode = mapper.readTree(response.body());
            if (responseNode.isArray() && responseNode.size() > 0) {
                JsonNode firstResponse = responseNode.get(0);
                String generatedText = firstResponse.get("generated_text").asText();
                System.out.println("Generated text: " + generatedText);
                
                // Extract just the therapist's response part
                String[] parts = generatedText.split("Therapist response:");
                if (parts.length > 1) {
                    String therapistResponse = parts[1].trim();
                    // Clean up the response
                    therapistResponse = therapistResponse.replaceAll("\\n", " ").trim();
                    return therapistResponse;
                }
                return generatedText;
            } else {
                System.out.println("Unexpected response format: " + response.body());
                throw new Exception("Unexpected response format");
            }
        } else {
            System.out.println("API call failed with status: " + response.statusCode());
            throw new Exception("API call failed with status: " + response.statusCode());
        }
    }

    private String generateFallbackResponse(String userMessage) {
        // Simple response generation based on keywords
        String lowerMessage = userMessage.toLowerCase();
        
        if (lowerMessage.contains("sad") || lowerMessage.contains("depressed") || lowerMessage.contains("down")) {
            return "I can sense that you're feeling really down right now. Depression can be incredibly isolating. " +
                   "Remember that you don't have to go through this alone. What's one thing that usually brings you even a little bit of comfort?";
        }
        
        if (lowerMessage.contains("anxious") || lowerMessage.contains("worried") || lowerMessage.contains("stress")) {
            return "Anxiety can feel overwhelming, like your thoughts are racing. " +
                   "Let's take a moment together. Can you try taking three deep breaths with me? " +
                   "What's one thing you're most worried about right now?";
        }
        
        if (lowerMessage.contains("angry") || lowerMessage.contains("frustrated") || lowerMessage.contains("mad")) {
            return "Anger is a natural emotion, and it sounds like you have good reasons to feel this way. " +
                   "What triggered these feelings? Sometimes understanding the root cause can help us process anger more healthily.";
        }
        
        if (lowerMessage.contains("lonely") || lowerMessage.contains("alone") || lowerMessage.contains("isolated")) {
            return "Loneliness can be one of the most painful feelings. You're reaching out, and that's a brave step. " +
                   "What kind of connection are you looking for right now?";
        }
        
        // Default empathetic response
        return THERAPY_RESPONSES[(int)(Math.random() * THERAPY_RESPONSES.length)];
    }

    public CrisisDetectionResult detectCrisis(String message) {
        // Crisis keywords and patterns
        String[] suicideKeywords = {
            "kill myself", "suicide", "end my life", "want to die", "don't want to live",
            "better off dead", "no reason to live", "plan to die"
        };
        
        String[] selfHarmKeywords = {
            "cut myself", "self harm", "hurt myself", "bleeding", "scars",
            "burn myself", "hit myself"
        };
        
        String[] violenceKeywords = {
            "hurt someone", "kill someone", "attack", "violent", "weapon",
            "shoot", "stab", "punch"
        };

        String lowerMessage = message.toLowerCase();
        int crisisLevel = 0;
        String crisisType = "";
        String description = "";

        // Check for suicide risk
        for (String keyword : suicideKeywords) {
            if (lowerMessage.contains(keyword)) {
                crisisLevel = Math.max(crisisLevel, 5);
                crisisType = "SUICIDE";
                description = "Suicidal ideation detected in message";
                break;
            }
        }

        // Check for self-harm
        for (String keyword : selfHarmKeywords) {
            if (lowerMessage.contains(keyword)) {
                crisisLevel = Math.max(crisisLevel, 4);
                crisisType = "SELF_HARM";
                description = "Self-harm ideation detected in message";
                break;
            }
        }

        // Check for violence
        for (String keyword : violenceKeywords) {
            if (lowerMessage.contains(keyword)) {
                crisisLevel = Math.max(crisisLevel, 4);
                crisisType = "VIOLENCE";
                description = "Violent ideation detected in message";
                break;
            }
        }

        // Check for distress indicators
        String[] distressKeywords = {
            "hopeless", "helpless", "worthless", "alone", "nobody cares",
            "can't take it anymore", "overwhelmed", "desperate"
        };

        for (String keyword : distressKeywords) {
            if (lowerMessage.contains(keyword)) {
                crisisLevel = Math.max(crisisLevel, 3);
                if (crisisType.isEmpty()) {
                    crisisType = "DISTRESS";
                    description = "High distress indicators detected";
                }
                break;
            }
        }

        return new CrisisDetectionResult(crisisLevel > 0, crisisLevel, crisisType, description);
    }

    public static class CrisisDetectionResult {
        private boolean crisisDetected;
        private int crisisLevel;
        private String crisisType;
        private String description;

        public CrisisDetectionResult(boolean crisisDetected, int crisisLevel, String crisisType, String description) {
            this.crisisDetected = crisisDetected;
            this.crisisLevel = crisisLevel;
            this.crisisType = crisisType;
            this.description = description;
        }

        // Getters
        public boolean isCrisisDetected() { return crisisDetected; }
        public int getCrisisLevel() { return crisisLevel; }
        public String getCrisisType() { return crisisType; }
        public String getDescription() { return description; }
    }
} 