package com.lauren.lucided.service;

import com.lauren.lucided.model.Quiz;
import com.lauren.lucided.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Long id, Quiz updatedQuiz) {
        return quizRepository.findById(id)
                .map(existing -> {
                    existing.setQuestion(updatedQuiz.getQuestion());
                    existing.setAnswer(updatedQuiz.getAnswer());
                    existing.setModule(updatedQuiz.getModule());
                    return quizRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}