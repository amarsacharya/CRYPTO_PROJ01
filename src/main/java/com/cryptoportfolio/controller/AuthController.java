package com.cryptoportfolio.controller;

import com.cryptoportfolio.dto.AuthRequest;
import com.cryptoportfolio.dto.AuthResponse;
import com.cryptoportfolio.dto.RegisterRequest;
import com.cryptoportfolio.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    @Operation(summary = "Sign in user", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        try {
            AuthResponse response = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up user", description = "Register a new user account")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            String message = authService.registerUser(signUpRequest);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}