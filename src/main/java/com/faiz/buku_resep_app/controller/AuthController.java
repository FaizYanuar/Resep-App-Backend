package com.faiz.buku_resep_app.controller;

import com.faiz.buku_resep_app.model.User;
import com.faiz.buku_resep_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

// A simple DTO (Data Transfer Object) for login requests
class LoginRequest {
    public String email;
    public String password;
}

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000") // Allow requests from your frontend
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error: Email is already in use!"));
        }

        // Hash the password before saving
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Error: Invalid email or password."));
        }

        User user = userOptional.get();

        // Check if the provided password matches the stored hashed password
        boolean passwordMatches = passwordEncoder.matches(loginRequest.password, user.getPassword());

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Error: Invalid email or password."));
        }

        // Login successful.
        // In a real app, you would generate and return a JWT (JSON Web Token) here.
        // For now, we return a success message and the user's details (without password).
        user.setPassword(null); // Do not send the password hash back to the client
        return ResponseEntity.ok(user);
    }
}
