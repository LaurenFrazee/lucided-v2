package com.lauren.lucided.controller;

import com.lauren.lucided.dto.ParentDTO;
import com.lauren.lucided.dto.StudentProgressDTO;
import com.lauren.lucided.mapper.ParentMapper;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.StudentRepository;
import com.lauren.lucided.service.ParentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    private final ParentService parentService;
    private final ParentMapper parentMapper;
    private final StudentRepository studentRepository;

    public ParentController(ParentService parentService,
                            ParentMapper parentMapper,
                            StudentRepository studentRepository) {
        this.parentService = parentService;
        this.parentMapper = parentMapper;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<ParentDTO> getAllParents() {
        return parentService.getAllParents().stream()
                .map(parentMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) {
        return parentService.getParentById(id)
                .map(parentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParentDTO> createParent(@Valid @RequestBody ParentDTO dto) {
        List<Student> children = studentRepository.findAllById(dto.getChildIds());
        Parent parent = parentMapper.toEntity(dto, children);
        Parent saved = parentService.createParent(parent);
        return ResponseEntity.ok(parentMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDTO> updateParent(@PathVariable Long id, @Valid @RequestBody ParentDTO dto) {
        List<Student> children = studentRepository.findAllById(dto.getChildIds());
        Parent updated = parentMapper.toEntity(dto, children);
        Parent saved = parentService.updateParent(id, updated);
        return ResponseEntity.ok(parentMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentProgressDTO>> getLinkedStudents(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(parentService.getLinkedStudentProgress(email));
    }
}