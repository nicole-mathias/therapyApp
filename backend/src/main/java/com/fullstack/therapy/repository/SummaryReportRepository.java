package com.fullstack.therapy.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.SummaryReport;

@Repository
public interface SummaryReportRepository extends JpaRepository<SummaryReport, Integer> {
    
    List<SummaryReport> findByClientOrderByReportDateDesc(Clients client);
    
    List<SummaryReport> findByClientAndReportDateBetweenOrderByReportDateDesc(
        Clients client, LocalDate startDate, LocalDate endDate);
    
    List<SummaryReport> findByRequiresAttentionTrue();
    
    List<SummaryReport> findByRiskLevelGreaterThan(int level);
    
    List<SummaryReport> findByClientAndRiskLevelGreaterThan(Clients client, int level);
} 