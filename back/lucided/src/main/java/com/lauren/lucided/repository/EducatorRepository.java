package com.lauren.lucided.repository;

import com.lauren.lucided.model.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducatorRepository extends JpaRepository<Educator, Long> {
    List<Educator> findByFullNameContainingIgnoreCase(String name);
    Optional<Educator> findByEmail(String email);
}

