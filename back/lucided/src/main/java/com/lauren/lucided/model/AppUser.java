package com.lauren.lucided.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class AppUser extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        EDUCATOR, PARENT, STUDENT
    }

}
