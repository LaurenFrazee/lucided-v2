package com.lauren.lucided.repository;

import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findAllById(Iterable<Long> ids);
}
