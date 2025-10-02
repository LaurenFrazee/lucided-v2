package com.lauren.lucided.model;

import com.lauren.lucided.model.AppUser;
import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
public class Educator extends AppUser
{

    @OneToMany(mappedBy = "educator")
    private List<Course> courses;

    @OneToMany
    private List<Student> students;
}