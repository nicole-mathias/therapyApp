package com.fullstack.therapy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.DailyLog;

@Repository
public interface DailyLogRepository extends JpaRepository<DailyLog, Integer> {
    
    List<DailyLog> findByClientOrderByLogDateDesc(Clients client);
    
    List<DailyLog> findByClientAndLogDateBetweenOrderByLogDateDesc(
        Clients client, LocalDate startDate, LocalDate endDate);
    
    DailyLog findByClientAndLogDate(Clients client, LocalDate logDate);
    
    List<DailyLog> findByCrisisLevelGreaterThan(int level);
    
    List<DailyLog> findByClientAndCrisisLevelGreaterThan(Clients client, int level);
} 