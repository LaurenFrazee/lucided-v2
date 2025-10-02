package com.lauren.lucided.dto;

import lombok.Data;

@Data
public class ReportDTO {
    private Long id;
    private String summary;
    private Double score;
    private Long studentId;
}