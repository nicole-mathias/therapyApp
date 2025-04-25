// controller that provides APIs for creating, retrieving, updating, deleting and finding Clients

// @CrossOrigin is for configuring allowed origins.

// @RestController annotation is used to define a controller 
// and to indicate that the return value of the methods should 
// be be bound to the web response body.

// @RequestMapping("/api") declares that all Apis’ url in the controller will start with /api

// We use @Autowired to inject ClientsRepository bean to local variable.
// Injects the JPA repository that talks to the database.
// ClientsRepository is probably an interface that extends JpaRepository<Clients, int>.


package com.fullstack.therapy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.repository.ClientsRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController()
@RequestMapping("/therapy")
public class ClientsController {

    @Autowired
    ClientsRepository clientsRepository;

    // get all clients 
    // what do you need? type: List<Clients>, getMapping, findAll() 
    @GetMapping("/clients")
    public ResponseEntity<List<Clients>> getAllClients(@RequestParam(required = false) String diagnosis) {
        
        try{
            List<Clients> clients = new ArrayList<Clients>();

            if (diagnosis == null){
                for(Clients c : clientsRepository.findAll()) {
                    clients.add(c);
                }
            }else{
                for(Clients c : clientsRepository.findByDiagnosis(diagnosis)){
                    clients.add(c);
                }
            }

            if (clients.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }
            return new ResponseEntity<>(clients, HttpStatus.OK);

        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Clients> getClientsById(@PathVariable("id") int id){
        Optional<Clients> clientData =  clientsRepository.findById(id);

        if (clientData.isPresent()){
            return new ResponseEntity<>(clientData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<Clients> createClient(@RequestBody Clients client){
        try{
            Clients _client = clientsRepository.save(new Clients(client.getName(), client.getAge(), client.getDiagnosis()));
            return new ResponseEntity<>(_client, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Clients> updateClient(@PathVariable int id, @RequestBody Clients client){
        Optional<Clients> clientData = clientsRepository.findById(id);

        if (clientData.isPresent()){
            Clients _client = clientData.get();
            _client.setName(_client.getName());
            _client.setAge(_client.getAge());
            _client.setDiagnosis(_client.getDiagnosis());

            return new ResponseEntity<>(clientsRepository.save(_client), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable int id){
        try{
            clientsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<HttpStatus> deleteAllClients(){
        try{
            clientsRepository.deleteAll(null);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
  
}
