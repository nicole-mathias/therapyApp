package com.fullstack.therapy.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fullstack.therapy.model.Clients;
import com.fullstack.therapy.model.User;
import com.fullstack.therapy.model.User.UserRole;
import com.fullstack.therapy.repository.ClientsRepository;
import com.fullstack.therapy.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
            
            if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
                User user = userOpt.get();
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("user", user);
                response.put("role", user.getRole());
                
                if (user.getRole() == UserRole.CLIENT && user.getClient() != null) {
                    response.put("client", user.getClient());
                }
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Invalid username or password");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        try {
            // Check if username or email already exists
            if (userRepository.existsByUsername(request.getUsername())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Username already exists");
                return ResponseEntity.ok(response);
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email already exists");
                return ResponseEntity.ok(response);
            }

            // Create new user
            User user = new User(
                request.getUsername(),
                request.getPassword(), // In production, this should be hashed
                request.getEmail(),
                request.getRole(),
                request.getFirstName(),
                request.getLastName()
            );

            // If registering as a client, link to existing client record
            if (request.getRole() == UserRole.CLIENT && request.getClientId() != null) {
                Optional<Clients> clientOpt = clientsRepository.findById(request.getClientId());
                if (clientOpt.isPresent()) {
                    user.setClient(clientOpt.get());
                }
            }

            User savedUser = userRepository.save(user);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", savedUser);
            response.put("message", "Registration successful");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", userRepository.findByIsActiveTrue());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to fetch users: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable int id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            
            Map<String, Object> response = new HashMap<>();
            if (userOpt.isPresent()) {
                response.put("success", true);
                response.put("user", userOpt.get());
            } else {
                response.put("success", false);
                response.put("message", "User not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to fetch user: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/users/{id}/link-client")
    public ResponseEntity<Map<String, Object>> linkUserToClient(@PathVariable int id, @RequestBody UpdateUserRequest request) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            
            Map<String, Object> response = new HashMap<>();
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Update client link if provided
                if (request.getClientId() != null) {
                    Optional<Clients> clientOpt = clientsRepository.findById(request.getClientId());
                    if (clientOpt.isPresent()) {
                        user.setClient(clientOpt.get());
                        userRepository.save(user);
                        response.put("success", true);
                        response.put("message", "User updated successfully");
                    } else {
                        response.put("success", false);
                        response.put("message", "Client not found");
                    }
                } else {
                    response.put("success", false);
                    response.put("message", "No client ID provided");
                }
            } else {
                response.put("success", false);
                response.put("message", "User not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to update user: " + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    // Request/Response classes
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private UserRole role;
        private String firstName;
        private String lastName;
        private Integer clientId; // For linking to existing client

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public UserRole getRole() { return role; }
        public void setRole(UserRole role) { this.role = role; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public Integer getClientId() { return clientId; }
        public void setClientId(Integer clientId) { this.clientId = clientId; }
    }

    public static class UpdateUserRequest {
        private Integer clientId;

        public Integer getClientId() { return clientId; }
        public void setClientId(Integer clientId) { this.clientId = clientId; }
    }
} 