package com.lauren.lucided.model;



import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class Student extends AppUser {

    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @Column
    private Double progress;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    private Educator educator;

    @OneToMany(mappedBy = "student")
    private List<Report> reports;
}