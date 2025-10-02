package com.lauren.lucided.mapper;

import com.lauren.lucided.dto.CourseSummary;
import com.lauren.lucided.dto.EducatorDTO;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Student;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class EducatorMapper {

    public EducatorDTO toDTO(Educator educator) {
        EducatorDTO dto = new EducatorDTO();
        dto.setId(educator.getId());
        dto.setFullName(educator.getFullName());
        dto.setEmail(educator.getEmail());
        dto.setPassword(educator.getPassword());
        dto.setCourses(educator.getCourses().stream()
                .map(course -> new CourseSummary(course.getId(), course.getTitle(), course.getSubject()))
                .toList());



        if (educator.getCourses() != null) {
            dto.setCourseIds(educator.getCourses().stream()
                    .map(Course::getId)
                    .collect(Collectors.toList()));
        }

        if (educator.getStudents() != null) {
            dto.setStudentIds(educator.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Educator toEntity(EducatorDTO dto, List<Course> courses, List<Student> students) {
        Educator educator = new Educator();
        educator.setId(dto.getId());
        educator.setFullName(dto.getFullName());
        educator.setEmail(dto.getEmail());
        educator.setPassword(dto.getPassword());
        educator.setCourses(courses);
        educator.setStudents(students);
        return educator;
    }
}