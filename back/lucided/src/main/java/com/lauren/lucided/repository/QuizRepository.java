package com.lauren.lucided.repository;

import com.lauren.lucided.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}