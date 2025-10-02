package com.lauren.lucided.dto;


import lombok.Data;

@Data
public class QuizDTO {
    private Long id;
    private String question;
    private String answer;
    private Long moduleId;
}