package com.lauren.lucided.service;

import com.lauren.lucided.dto.AuthResponse;
import com.lauren.lucided.dto.LoginRequest;
import com.lauren.lucided.dto.RegisterRequest;
import com.lauren.lucided.model.AppUser;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.AppUserRepository;
import com.lauren.lucided.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AppUserRepository appUserRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        AppUser user;

        switch (request.getRole()) {
            case STUDENT -> user = new Student();
            case EDUCATOR -> user = new Educator();
            case PARENT -> user = new Parent();
            default -> throw new RuntimeException("Invalid role");
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        appUserRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtUtil.generateToken(user.getEmail(), user.getRole().name()));
        response.setRole(user.getRole().name());
        return response;
    }



    public AuthResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        AuthResponse response = new AuthResponse();
        response.setToken(jwtUtil.generateToken(user.getEmail(), user.getRole().name()));
        response.setRole(user.getRole().name());
        return response;
    }
}