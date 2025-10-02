package com.lauren.lucided.controller;

import com.lauren.lucided.model.Quiz;
import com.lauren.lucided.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id).orElseThrow();
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }

    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable Long id, @RequestBody Quiz quiz) {
        return quizService.updateQuiz(id, quiz);
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
}