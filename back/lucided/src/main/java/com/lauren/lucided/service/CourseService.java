package com.lauren.lucided.service;

import com.lauren.lucided.model.Course;
import com.lauren.lucided.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedCourse.getTitle());
                    existing.setEducator(updatedCourse.getEducator());
                    existing.setStudents(updatedCourse.getStudents());
                    existing.setModules(updatedCourse.getModules());
                    return courseRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // ✅ Implemented save method
    public Course save(Course course) {
        return courseRepository.save(course);
    }
}