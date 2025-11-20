package com.cryptoportfolio.service;

import com.cryptoportfolio.dto.AuthRequest;
import com.cryptoportfolio.dto.AuthResponse;
import com.cryptoportfolio.dto.RegisterRequest;
import com.cryptoportfolio.entity.User;
import com.cryptoportfolio.repository.UserRepository;
import com.cryptoportfolio.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse authenticateUser(AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken((User) authentication.getPrincipal());

        User user = (User) authentication.getPrincipal();
        return new AuthResponse(jwt, user.getUsername(), user.getEmail());
    }

    public String registerUser(RegisterRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                           signUpRequest.getEmail(),
                           encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        return "User registered successfully!";
    }
}