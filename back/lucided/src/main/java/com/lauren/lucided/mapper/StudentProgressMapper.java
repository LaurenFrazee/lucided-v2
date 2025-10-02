package com.lauren.lucided.mapper;

import com.lauren.lucided.dto.StudentProgressDTO;
import com.lauren.lucided.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class StudentProgressMapper {

    public StudentProgressDTO toDto(Student student) {
        StudentProgressDTO dto = new StudentProgressDTO();
        dto.setStudentId(student.getId());
        dto.setFullName(student.getFullName());
        dto.setEmail(student.getEmail());

        // Assuming Student has a numeric progress field
        dto.setProgressPercentage(student.getProgress());

        return dto;
    }

    public List<StudentProgressDTO> toDtoList(List<Student> students) {
        return students.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

