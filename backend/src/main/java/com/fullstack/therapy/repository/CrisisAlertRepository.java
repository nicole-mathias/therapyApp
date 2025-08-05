package com.fullstack.therapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.CrisisAlert;

@Repository
public interface CrisisAlertRepository extends JpaRepository<CrisisAlert, Integer> {
    
    List<CrisisAlert> findByClientOrderByTimestampDesc(Clients client);
    
    List<CrisisAlert> findByResolvedFalse();
    
    List<CrisisAlert> findByAlertLevelGreaterThan(int level);
    
    List<CrisisAlert> findByClientAndResolvedFalse(Clients client);
} 