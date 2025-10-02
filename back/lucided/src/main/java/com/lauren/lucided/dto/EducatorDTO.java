package com.lauren.lucided.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class EducatorDTO {

    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private List<Long> courseIds;
    private List<Long> studentIds;
    private List<CourseSummary> courses;

    public List<CourseSummary> getCourses() {
        return courses;
    }
    public void setCourses(List<CourseSummary> courses) {
        this.courses = courses;
    }




}