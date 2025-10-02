package com.lauren.lucided.mapper;

import com.lauren.lucided.dto.CourseDTO;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.Module;
import com.lauren.lucided.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toEntity(CourseDTO dto, Educator educator, List<Student> students, List<Module> modules) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setTitle(dto.getTitle());

        course.setCreatedAt(dto.getCreatedAt());
        course.setEducator(educator);
        course.setStudents(students);
        course.setModules(modules);
        return course;
    }

    public CourseDTO toDto(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setEducatorId(course.getEducator().getId());
        dto.setStudentIds(course.getStudents().stream().map(Student::getId).toList());
        dto.setModuleIds(course.getModules().stream().map(Module::getId).toList());
        return dto;
    }

    public List<CourseDTO> toDtoList(List<Course> courses) {
        return courses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}