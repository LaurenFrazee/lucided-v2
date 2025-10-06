package com.lauren.lucided.controller;

import com.lauren.lucided.dto.StudentDTO;
import com.lauren.lucided.mapper.StudentMapper;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.CourseRepository;
import com.lauren.lucided.repository.ParentRepository;
import com.lauren.lucided.repository.EducatorRepository;
import com.lauren.lucided.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    @Autowired
    private final StudentMapper studentMapper;

    private final CourseRepository courseRepository;
    private final ParentRepository parentRepository;
    private final EducatorRepository educatorRepository;

    public StudentController(StudentService studentService,
                             StudentMapper studentMapper,
                             CourseRepository courseRepository,
                             ParentRepository parentRepository,
                             EducatorRepository educatorRepository) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.courseRepository = courseRepository;
        this.parentRepository = parentRepository;
        this.educatorRepository = educatorRepository;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents().stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(studentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO dto) {
        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
        Parent parent = dto.getParentId() != null ? parentRepository.findById(dto.getParentId()).orElse(null) : null;
        Educator educator = dto.getEducatorId() != null ? educatorRepository.findById(dto.getEducatorId()).orElse(null) : null;

        Student student = studentMapper.toEntity(dto, courses, parent, educator);
        Student saved = studentService.createStudent(student);
        return ResponseEntity.ok(studentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO dto) {
        List<Course> courses = courseRepository.findAllById(dto.getCourseIds());
        Parent parent = dto.getParentId() != null ? parentRepository.findById(dto.getParentId()).orElse(null) : null;
        Educator educator = dto.getEducatorId() != null ? educatorRepository.findById(dto.getEducatorId()).orElse(null) : null;

        Student updated = studentMapper.toEntity(dto, courses, parent, educator);
        Student saved = studentService.updateStudent(id, updated);
        return ResponseEntity.ok(studentMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<StudentDTO> getStudentProfile(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(studentService.getProfileByEmail(email));
    }


}