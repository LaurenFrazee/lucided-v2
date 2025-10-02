package com.lauren.lucided.repository;

import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    Optional<Module> findByTitle(String title); // ✅ correct
}