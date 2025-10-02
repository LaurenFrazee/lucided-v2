package com.lauren.lucided.controller;

import com.lauren.lucided.dto.CourseDTO;
import com.lauren.lucided.dto.EducatorDTO;
import com.lauren.lucided.mapper.EducatorMapper;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.CourseRepository;
import com.lauren.lucided.repository.StudentRepository;
import com.lauren.lucided.service.EducatorService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educators")
public class EducatorController {

    private final EducatorService educatorService;
    private final EducatorMapper educatorMapper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private EducatorService courseService;

    public EducatorController(EducatorService educatorService,
                              EducatorMapper educatorMapper,
                              CourseRepository courseRepository,
                              StudentRepository studentRepository) {
        this.educatorService = educatorService;
        this.educatorMapper = educatorMapper;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<EducatorDTO> getAllEducators() {
        return educatorService.getAllEducators().stream()
                .map(educatorMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducatorDTO> getEducatorById(@PathVariable Long id) {
        return educatorService.getEducatorById(id)
                .map(educatorMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EducatorDTO> createEducator(@Valid @RequestBody EducatorDTO dto) {
        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
        List<Student> students = studentRepository.findAllById(dto.getStudentIds());

        Educator educator = educatorMapper.toEntity(dto, courses, students);
        Educator saved = educatorService.createEducator(educator);
        return ResponseEntity.ok(educatorMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducatorDTO> updateEducator(@PathVariable Long id, @Valid @RequestBody EducatorDTO dto) {
        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
        List<Student> students = studentRepository.findAllById(dto.getStudentIds());

        Educator updated = educatorMapper.toEntity(dto, courses, students);
        Educator saved = educatorService.updateEducator(id, updated);
        return ResponseEntity.ok(educatorMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducator(@PathVariable Long id) {
        educatorService.deleteEducator(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getCourses(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(courseService.getCoursesByEducator(email));
    }

    @GetMapping("/me")
    public EducatorDTO getProfile(@AuthenticationPrincipal UserDetails user) {
        return educatorService.getEducatorByEmail(user.getUsername());
    }




}