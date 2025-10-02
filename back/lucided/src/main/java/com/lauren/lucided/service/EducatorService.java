package com.lauren.lucided.service;

import com.lauren.lucided.dto.CourseDTO;
import com.lauren.lucided.dto.EducatorDTO;
import com.lauren.lucided.mapper.CourseMapper;
import com.lauren.lucided.mapper.EducatorMapper;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.repository.CourseRepository;
import com.lauren.lucided.repository.EducatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducatorService {

    private final EducatorRepository educatorRepository;
    private CourseRepository courseRepository;
    private CourseMapper courseMapper;
    private EducatorMapper educatorMapper;



    public EducatorService(EducatorRepository educatorRepository) {
        this.educatorRepository = educatorRepository;
    }

    public List<Educator> getAllEducators() {
        return educatorRepository.findAll();
    }

    public Optional<Educator> getEducatorById(Long id) {
        return educatorRepository.findById(id);
    }

    public Educator createEducator(Educator educator) {
        return educatorRepository.save(educator);
    }

    public Educator updateEducator(Long id, Educator updatedEducator) {
        return educatorRepository.findById(id)
                .map(existing -> {
                    existing.setFullName(updatedEducator.getFullName());
                    existing.setEmail(updatedEducator.getEmail());
                    existing.setPassword(updatedEducator.getPassword());
                    existing.setCourses(updatedEducator.getCourses());
                    existing.setStudents(updatedEducator.getStudents());
                    return educatorRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Educator not found"));
    }

    public void deleteEducator(Long id) {
        educatorRepository.deleteById(id);
    }

    public List<CourseDTO> getCoursesByEducator(String email) {
        Educator educator = educatorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Educator not found"));
        return courseMapper.toDtoList(courseRepository.findByEducator(educator));
    }


    public EducatorDTO getEducatorByEmail(String email) {
        Educator educator = educatorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Educator not found"));
        return educatorMapper.toDTO(educator);
    }
}


