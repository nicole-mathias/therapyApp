package com.fullstack.therapy.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/therapy/ai")
@CrossOrigin(origins="http://localhost:3000")
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    //  Summarize session notes
    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeNotes(@RequestBody Map<String, String> payload) {
        String notes = payload.get("notes");

        String prompt = """
            You are a therapist's assistant. Summarize these session notes and extract 3 actionable insights:
            %s 
            """.formatted(notes);

        ChatResponse chatResponse = chatClient
            .prompt(prompt)
            .call()
            .chatResponse();

        String summary = chatResponse.getResult().getOutput().getText();

        System.out.println(summary);

        return ResponseEntity.ok(summary);  
    }  
}
