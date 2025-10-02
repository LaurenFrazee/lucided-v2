package com.lauren.lucided.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourseDTO {
    private Long id;

    @NotBlank
    private String title;

    private String description;
    private String subject;
    private String educatorName;
    private LocalDateTime createdAt;
    private Long educatorId;

    // 🔑 Add backing fields
    private List<Long> studentIds;
    private List<Long> moduleIds;

    // --- Getters and Setters for studentIds ---
    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    // --- Getters and Setters for moduleIds ---
    public List<Long> getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(List<Long> moduleIds) {
        this.moduleIds = moduleIds;
    }
}