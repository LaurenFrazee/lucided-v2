package com.lauren.lucided.model;



import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id") // optional: makes the foreign key explicit
    private Course course;

    @OneToMany(mappedBy = "module")
    private List<Quiz> quizzes;

    public Module(){}
}
