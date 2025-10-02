package com.lauren.lucided.model;


import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;
    private Double score;

    @ManyToOne
    private Student student;
}