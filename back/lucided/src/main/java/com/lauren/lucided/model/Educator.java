package com.lauren.lucided.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "educators")
public class Educator extends AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "educator")
    private List<Course> courses;

    @OneToMany(mappedBy = "educator")
    private List<Student> students;
}