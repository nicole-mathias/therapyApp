//  JpaRepository’s methods: save(), findOne(), findById(), findAll(), count(), delete(), deleteById()
// custom method are specified below - findByDiagnosis

package com.fullstack.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.therapy.model.Clients;

import java.util.List;

// JPARespository<generic - Entity, type of primary key>
public interface ClientsRepository extends JpaRepository<Clients,Integer>{
    List<Clients> findByDiagnosis(String diagnosis);
}
