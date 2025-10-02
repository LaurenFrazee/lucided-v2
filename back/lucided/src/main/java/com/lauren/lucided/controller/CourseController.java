package com.lauren.lucided.controller;

import com.lauren.lucided.dto.CourseDTO;
import com.lauren.lucided.mapper.CourseMapper;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Module;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.CourseRepository;
import com.lauren.lucided.repository.EducatorRepository;
import com.lauren.lucided.repository.ModuleRepository;
import com.lauren.lucided.repository.StudentRepository;
import com.lauren.lucided.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final EducatorRepository educatorRepository;
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService,
                            CourseRepository courseRepository,
                            EducatorRepository educatorRepository,
                            StudentRepository studentRepository,
                            ModuleRepository moduleRepository,
                            CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.educatorRepository = educatorRepository;
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
        this.courseMapper = courseMapper;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO dto) {
        // 1. Fetch related entities
        Educator educator = educatorRepository.findById(dto.getEducatorId())
                .orElseThrow(() -> new RuntimeException("Educator not found"));

        List<Student> students = studentRepository.findAllById(dto.getStudentIds());
        List<Module> modules = moduleRepository.findAllById(dto.getModuleIds());

        // 2. Map DTO → Entity
        Course course = courseMapper.toEntity(dto, educator, students, modules);

        // 3. Save
        Course saved = courseService.save(course);

        // 4. Return DTO
        return ResponseEntity.ok(courseMapper.toDto(saved));
    }
}