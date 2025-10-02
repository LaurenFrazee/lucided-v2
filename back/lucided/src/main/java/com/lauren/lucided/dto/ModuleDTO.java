package com.lauren.lucided.dto;


import lombok.Data;
import java.util.List;

@Data
public class ModuleDTO {
    private Long id;
    private String title;
    private Long courseId;
    private List<Long> quizIds;
}