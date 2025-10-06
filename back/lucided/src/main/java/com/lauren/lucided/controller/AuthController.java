package com.lauren.lucided.controller;

import com.lauren.lucided.dto.RegisterRequest;
import com.lauren.lucided.dto.StudentDTO;
import com.lauren.lucided.model.*;
import com.lauren.lucided.security.AuthRequest;
import com.lauren.lucided.security.AuthResponse;
import com.lauren.lucided.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.lauren.lucided.repository.StudentRepository;
import com.lauren.lucided.repository.ParentRepository;
import com.lauren.lucided.repository.EducatorRepository;
import com.lauren.lucided.repository.CourseRepository;
import com.lauren.lucided.mapper.StudentMapper;


import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ParentRepository parentRepository;
    private final EducatorRepository educatorRepository;
    private final StudentMapper studentMapper;




    public AuthController( AuthenticationManager authManager,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder,
                           StudentRepository studentRepository,
                           CourseRepository courseRepository,
                           ParentRepository parentRepository,
                           EducatorRepository educatorRepository,
                           StudentMapper studentMapper
    ) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.parentRepository = parentRepository;
        this.educatorRepository = educatorRepository;
        this.studentMapper = studentMapper;



    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtUtil.generateToken(auth);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @PostMapping("/register/basic")
    public ResponseEntity<AuthResponse> basicRegister(@RequestBody RegisterRequest request) {
        if (request.getEmail() == null || request.getPassword() == null || request.getFullName() == null) {
            return ResponseEntity.badRequest().body(new AuthResponse("Missing required fields"));
        }

        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Email already in use"));
        }

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setRole(AppUser.Role.STUDENT);

        Student saved = studentRepository.save(student);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole().name());

        return ResponseEntity.ok(new AuthResponse(token));
    }



    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody StudentDTO dto) {
        System.out.println("🔔 Controller hit: /api/auth/register");


        if (dto.getCourseIds() == null || dto.getCourseIds().isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Course IDs must not be null or empty"));
        }

        if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Email already in use"));
        }

        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
        Parent parent = dto.getParentId() != null ? parentRepository.findById(dto.getParentId()).orElse(null) : null;
        Educator educator = dto.getEducatorId() != null ? educatorRepository.findById(dto.getEducatorId()).orElse(null) : null;

        Student student = studentMapper.toEntity(dto, courses, parent, educator);
        student.setPassword(passwordEncoder.encode(dto.getPassword()));
        student.setRole(Student.Role.STUDENT);



        Student saved = studentRepository.save(student);
        String token = jwtUtil.generateToken(saved.getEmail(), saved.getRole().name());



        return ResponseEntity.ok(new AuthResponse(token));
    }


}
