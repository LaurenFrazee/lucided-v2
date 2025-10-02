package com.lauren.lucided.dto;

public class CourseSummary {
    private Long id;
    private String title;
    private String subject;

    public CourseSummary(Long id, String title, String subject) {
        this.id = id;
        this.title = title;
        this.subject = subject;
    }

    // Getters only (immutable)
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }
}