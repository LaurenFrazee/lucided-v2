package com.lauren.lucided.mapper;

import com.lauren.lucided.dto.ParentDTO;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParentMapper {

    public ParentDTO toDTO(Parent parent) {
        ParentDTO dto = new ParentDTO();
        dto.setId(parent.getId());
        dto.setFullName(parent.getFullName());
        dto.setEmail(parent.getEmail());
        dto.setPassword(parent.getPassword());

        if (parent.getStudents() != null) {
            dto.setChildIds(parent.getStudents().stream()
                    .map(Student::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Parent toEntity(ParentDTO dto, List<Student> children) {
        Parent parent = new Parent();
        parent.setId(dto.getId());
        parent.setFullName(dto.getFullName());
        parent.setEmail(dto.getEmail());
        parent.setPassword(dto.getPassword());
        parent.setStudents(children);
        return parent;
    }
}