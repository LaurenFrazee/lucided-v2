package com.lauren.lucided.service;

import com.lauren.lucided.dto.AuthResponse;
import com.lauren.lucided.dto.LoginRequest;
import com.lauren.lucided.dto.RegisterRequest;
import com.lauren.lucided.model.AppUser;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.StudentRepository;
import com.lauren.lucided.repository.EducatorRepository;
import com.lauren.lucided.repository.ParentRepository;
import com.lauren.lucided.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentRepository studentRepo;
    private final EducatorRepository educatorRepo;
    private final ParentRepository parentRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(StudentRepository studentRepo,
                       EducatorRepository educatorRepo,
                       ParentRepository parentRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.studentRepo = studentRepo;
        this.educatorRepo = educatorRepo;
        this.parentRepo = parentRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        AppUser user;

        switch (request.getRole()) {
            case STUDENT -> {
                Student student = new Student();
                student.setFullName(request.getFullName());
                student.setEmail(request.getEmail());
                student.setPassword(passwordEncoder.encode(request.getPassword()));
                student.setRole(request.getRole());
                studentRepo.save(student);
                user = student;
            }
            case EDUCATOR -> {
                Educator educator = new Educator();
                educator.setFullName(request.getFullName());
                educator.setEmail(request.getEmail());
                educator.setPassword(passwordEncoder.encode(request.getPassword()));
                educator.setRole(request.getRole());
                educatorRepo.save(educator);
                user = educator;
            }
            case PARENT -> {
                Parent parent = new Parent();
                parent.setFullName(request.getFullName());
                parent.setEmail(request.getEmail());
                parent.setPassword(passwordEncoder.encode(request.getPassword()));
                parent.setRole(request.getRole());
                parentRepo.save(parent);
                user = parent;
            }
            default -> throw new RuntimeException("Invalid role");
        }

        AuthResponse response = new AuthResponse();
        response.setToken(jwtUtil.generateToken(user.getEmail(), user.getRole().name()));
        response.setRole(user.getRole().name());
        return response;
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = studentRepo.findByEmail(request.getEmail())
                .map(u -> (AppUser) u)
                .or(() -> educatorRepo.findByEmail(request.getEmail()).map(u -> (AppUser) u))
                .or(() -> parentRepo.findByEmail(request.getEmail()).map(u -> (AppUser) u))
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