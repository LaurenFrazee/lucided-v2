package com.lauren.lucided.service;

import com.lauren.lucided.dto.CourseDTO;
import com.lauren.lucided.dto.StudentDTO;
import com.lauren.lucided.mapper.CourseMapper;
import com.lauren.lucided.mapper.StudentMapper;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(updatedStudent.getFullName());
                    existing.setEmail(updatedStudent.getEmail());
                    existing.setPassword(updatedStudent.getPassword());
                    existing.setCourses(updatedStudent.getCourses());
                    existing.setParent(updatedStudent.getParent());
                    existing.setEducator(updatedStudent.getEducator());
                    return studentRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public StudentDTO getProfileByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentMapper.toDto(student);
    }






}