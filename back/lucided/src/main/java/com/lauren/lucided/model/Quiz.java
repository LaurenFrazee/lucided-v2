package com.lauren.lucided.model;



import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;

    @ManyToOne
    private Module module;
}