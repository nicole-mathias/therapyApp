package com.fullstack.therapy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.Goal;
import com.fullstack.therapy.repository.ClientsRepository;
import com.fullstack.therapy.repository.GoalRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/therapy")
public class GoalController {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    ClientsRepository clientsRepository;


    @GetMapping("/goals")
    public ResponseEntity<List<Goal>> getAllGoals(@RequestParam(required = false) boolean completed) {
        try{
            List<Goal> goal = new ArrayList<Goal>();

            if (completed){
                for (Goal g: goalRepository.findByCompleted(true)){
                    goal.add(g);
                }
            }else{
                for (Goal g: goalRepository.findAll()){
                    goal.add(g);
                }
            }
            if (!goal.isEmpty()){
                return new ResponseEntity<>(goal, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/goals/{start_date}")
    public ResponseEntity<List<Goal>> getMethodName(@PathVariable("start_date") LocalDate start_date) {
        try{

            List<Goal> goal = new ArrayList<>();

            for (Goal g : goalRepository.findByStartDate(start_date)){
                goal.add(g);
            }

            if (goal.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(goal, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable int id) {
        Optional<Goal> goalData = goalRepository.findById(id);

        if (goalData.isPresent()){
            return new ResponseEntity<>(goalData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } 
    }

    // since adding a new goal requires that there is a client associated with it
    // enforce that adding a new client through goal is not allowed
    // there should be a pre-existing client_id first
    @PostMapping("/goals")
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {
        try{
            Clients client = goal.getClient();

            if (client != null && client.getId() != 0 && clientsRepository.existsById(client.getId())){
                // Reuse the managed client entity (optional, but cleaner)
                client = clientsRepository.findById(client.getId()).get();
                goal.setClient(client); // ensure it's attached
                Goal savedGoal = goalRepository.save(goal);
                return new ResponseEntity<>(savedGoal,HttpStatus.CREATED);
            }
            // Client is missing or doesn't exist
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a goal also invloves a client
    @PutMapping("/goals/{id}")
    public ResponseEntity<Goal> putMethodName(@PathVariable int id, @RequestBody Goal goal) {   

        Optional<Goal> sessionGoal = goalRepository.findById(id);

        if (sessionGoal.isPresent()){

            Goal existingGoal = sessionGoal.get();

            // only update fileds that are not null
            if (goal.getGoalText() != null){
                existingGoal.setGoalText(goal.getGoalText());
            }

            if (goal.getStartDate() != null){
                existingGoal.setStartDate(goal.getStartDate());
            }

            existingGoal.setCompleted(goal.isCompleted());


            Clients incomingClient = goal.getClient();
            // Handle client (if provided)
            if (incomingClient != null && incomingClient.getId() != 0){
                Optional<Clients> existingClient = clientsRepository.findById(incomingClient.getId());

                if (existingClient.isPresent()){
                    existingGoal.setClient(existingClient.get());
                }
            }

            return new ResponseEntity<>(goalRepository.save(existingGoal), HttpStatus.OK);
    
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }  

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<HttpStatus> deleteGoal(@PathVariable("id") int id){
        try{
            goalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    

    
    
    
}
