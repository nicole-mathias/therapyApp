package com.fullstack.therapy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.therapy.model.Goal;
import java.time.LocalDate;


public interface GoalRepository extends JpaRepository<Goal,Integer>{
    List<Goal> findByCompleted(boolean completed);
    List<Goal> findByStartDate(LocalDate startDate); 
}
