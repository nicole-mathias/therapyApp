//  JpaRepository’s methods: save(), findOne(), findById(), findAll(), count(), delete(), deleteById()
// custom method are specified below - findByDiagnosis

package com.fullstack.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.therapy.model.Session;
import java.util.List;
import java.time.LocalDate;


public interface SessionRepository extends JpaRepository<Session,Integer>{
    List<Session> findByDate(LocalDate date);
    List<Session> findBySentiment(String sentiment); 
}
