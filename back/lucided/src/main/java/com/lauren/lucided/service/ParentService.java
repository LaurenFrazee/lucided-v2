package com.lauren.lucided.service;

import com.lauren.lucided.dto.StudentProgressDTO;
import com.lauren.lucided.mapper.CourseMapper;
import com.lauren.lucided.mapper.StudentProgressMapper;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.repository.ParentRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;
    private StudentProgressMapper studentProgressMapper;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Optional<Parent> getParentById(Long id) {
        return parentRepository.findById(id);
    }

    public Parent createParent(Parent parent) {
        return parentRepository.save(parent);
    }

    public Parent updateParent(Long id, Parent updatedParent) {
        return parentRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(updatedParent.getFullName());
                    existing.setEmail(updatedParent.getEmail());
                    existing.setPassword(updatedParent.getPassword());
                    existing.setStudents(updatedParent.getStudents());
                    return parentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Parent not found"));
    }

    public void deleteParent(Long id) {
        parentRepository.deleteById(id);
    }

    public List<StudentProgressDTO> getLinkedStudentProgress(String email) {
        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        return studentProgressMapper.toDtoList(parent.getStudents());
    }
}