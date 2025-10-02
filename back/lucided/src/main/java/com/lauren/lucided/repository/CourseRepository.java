package com.lauren.lucided.repository;

import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByEducator(Educator educator); // ✅ this must match the field name in Course.java
    Optional<Course> findByTitle(String title);
}

