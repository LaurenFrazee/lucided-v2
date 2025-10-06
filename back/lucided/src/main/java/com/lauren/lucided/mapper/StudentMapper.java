package com.lauren.lucided.mapper;

import com.lauren.lucided.dto.StudentDTO;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Educator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;



@Component
public class StudentMapper {

    public StudentDTO toDto(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setEmail(student.getEmail());
        dto.setPassword(student.getPassword());

        if (student.getCourses() != null) {
            dto.setCourseIds(student.getCourses().stream()
                    .map(Course::getId)
                    .collect(Collectors.toList()));
        }

        if (student.getParent() != null) {
            dto.setParentId(student.getParent().getId());
        }

        if (student.getEducator() != null) {
            dto.setEducatorId(student.getEducator().getId());
        }

        return dto;
    }

    public Student toEntity(StudentDTO dto, List<Course> courses, Parent parent, Educator educator) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFullName(dto.getFullName());
        student.setEmail(dto.getEmail());
        student.setPassword(dto.getPassword());
        student.setCourses(courses);
        student.setParent(parent);
        student.setEducator(educator);
        return student;
    }

}