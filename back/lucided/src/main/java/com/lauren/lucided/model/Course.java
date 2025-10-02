package com.lauren.lucided.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;


@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subject;
    private LocalDateTime createdAt;
    private String Description;

    @ManyToOne
    @JoinColumn(name = "educator_id")
    private Educator educator;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @OneToMany(mappedBy = "course")
    private List<Module> modules;


}
