package com.fullstack.therapy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.Session;
import com.fullstack.therapy.repository.SessionRepository;

import com.fullstack.therapy.repository.ClientsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@CrossOrigin(origins = "http://localhost:3000")
@RestControllerfrontend/package-lock.jsonfrontend/package-lock.json
@RequestMapping("/therapy")
public class SessionController {
    
    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    ClientsRepository clientsRepository;


    // API's

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getAllSessions(@RequestParam(required = false) String sentiment){
        try{
            List<Session> session = new ArrayList<Session>();

            if (sentiment == null){
                for(Session s: sessionRepository.findAll()){
                    session.add(s);
                }
            }else{
                for(Session s: sessionRepository.findBySentiment(sentiment)){
                    session.add(s);
                }
            }
            if (session.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(session, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/sessions/{id}")
    public ResponseEntity<Session> getSessionById(@PathVariable("id") int id) {
        Optional<Session> sessionData = sessionRepository.findById(id);
        
        if(sessionData.isPresent()){
            return new ResponseEntity<>(sessionData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    // TODO
    @GetMapping("/sessions/{client_id}")
    public String getByClientId(@RequestParam String param) {
        return new String();
    }

    // TODO
    @GetMapping("/session/{date}")
    public String GetByDate(@RequestParam String param) {
        return new String();
    }


    // since adding a new session requires that there is a client associated with it
    // Either link an existing client (using thier client_id) or add a new client
    // Adding a new client requires that you import the clientsRepository bean and add it using it
    @PostMapping("/sessions")
    public ResponseEntity<Session> updateSession(@RequestBody Session session) {
        try{
            Clients client = session.getClient();

            // case: Client has an ID and it already exists
            if (client != null && client.getId() != 0 && clientsRepository.existsById(client.getId())){
                client = clientsRepository.findById(client.getId()).get(); // reuse existing
            }else{
                // client is new or has no ID
                client = clientsRepository.save(client);
            }

            // Associate the (existing or new) client with the session
            session.setClient(client);

            Session savedSession = sessionRepository.save(session);
            
            return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }


    // Update a session also invloves a client
    @PutMapping("/sessions/{id}")
    public ResponseEntity<Session> putMethodName(@PathVariable int id, @RequestBody Session session) {   

        Optional<Session> sessionData = sessionRepository.findById(id);

        if (sessionData.isPresent()){

            Session existingSession = sessionData.get();

            // only update fileds that are not null
            if (session.getDate() != null){
                existingSession.setDate(session.getDate());
            }

            if (session.getNotes() != null){
                existingSession.setNotes(session.getNotes());
            }

            if (session.getSummary() != null){
                existingSession.setSummary(session.getSummary());
            }

            if (session.getSentiment() != null){
                existingSession.setSentiment(session.getSentiment());
            }

            if (session.getGoalProgress() > 0){
                existingSession.setGoalProgress(session.getGoalProgress());
            } 


            Clients incomingClient = session.getClient();
            // Handle client (if provided)
            if (incomingClient != null && incomingClient.getId() != 0){
                Optional<Clients> existingClient = clientsRepository.findById(incomingClient.getId());

                if (existingClient.isPresent()){
                    existingSession.setClient(existingClient.get());
                }
            }

            return new ResponseEntity<>(sessionRepository.save(existingSession), HttpStatus.OK);
    
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }   

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<HttpStatus> deleteSession(@PathVariable("id") int id){
        try{
            sessionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }
}
