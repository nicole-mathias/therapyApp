package com.fullstack.therapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.therapy.model.ChatMessage;
import com.fullstack.therapy.model.Clients;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    
    List<ChatMessage> findByClientOrderByTimestampAsc(Clients client);
    
    List<ChatMessage> findByClientAndSessionIdOrderByTimestampAsc(Clients client, String sessionId);
    
    List<ChatMessage> findByCrisisDetectedTrue();
    
    List<ChatMessage> findByCrisisLevelGreaterThan(int level);
} 