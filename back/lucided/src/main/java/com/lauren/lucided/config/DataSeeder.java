package com.lauren.lucided.config;
import com.lauren.lucided.model.Parent;
import com.lauren.lucided.model.Module;
import com.lauren.lucided.model.Course;
import com.lauren.lucided.model.Educator;
import com.lauren.lucided.model.AppUser.Role;
import com.lauren.lucided.model.Student;
import com.lauren.lucided.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedEducatorStudentsCoursesModulesParent(
            EducatorRepository educatorRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            ModuleRepository moduleRepository,
            ParentRepository parentRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // ✅ Ensure Educator exists
            Educator educator = educatorRepository.findByEmail("educator@example.com").orElseGet(() -> {
                Educator e = new Educator();
                e.setFullName("Dr. Lauren Bright");
                e.setEmail("educator@example.com");
                e.setPassword(passwordEncoder.encode("securePassword123"));
                e.setRole(Role.EDUCATOR);
                return educatorRepository.save(e);
            });

            // ✅ Ensure Students exist
            Student ella = studentRepository.findByEmail("ella@student.com").orElseGet(() -> {
                Student s = new Student();
                s.setFullName("Ella Johnson");
                s.setEmail("ella@student.com");
                s.setPassword(passwordEncoder.encode("studentPass123"));
                s.setRole(Role.STUDENT);
                s.setEducator(educator);
                return studentRepository.save(s);
            });

            Student kai = studentRepository.findByEmail("kai@student.com").orElseGet(() -> {
                Student s = new Student();
                s.setFullName("Kai Rivera");
                s.setEmail("kai@student.com");
                s.setPassword(passwordEncoder.encode("studentPass123"));
                s.setRole(Role.STUDENT);
                s.setEducator(educator);
                return studentRepository.save(s);
            });

            List<Student> students = List.of(ella, kai);

            // ✅ Ensure Course exists
            Course course = courseRepository.findByTitle("Introduction to Biology").orElseGet(() -> {
                Course c = new Course();
                c.setTitle("Introduction to Biology");
                c.setDescription("A foundational course in life sciences");
                c.setSubject("Biology");
                c.setEducator(educator);
                c.setStudents(students);
                return courseRepository.save(c);
            });

            // ✅ Ensure Modules exist
            String[] moduleTitles = {
                    "Cell Structure & Function",
                    "Genetics & Heredity",
                    "Evolution & Natural Selection"
            };

            for (String title : moduleTitles) {
                if (moduleRepository.findByTitle(title).isEmpty()) {
                    Module module = new Module();
                    module.setTitle(title);
                    module.setContent("Content for " + title);
                    module.setCourse(course);
                    moduleRepository.save(module);
                    System.out.println("✅ Module seeded: " + title);
                }
            }

            // ✅ Seed Parent
            String parentEmail = "parent@example.com";
            if (parentRepository.findByEmail(parentEmail).isEmpty()) {
                Parent parent = new Parent();
                parent.setFullName("Jordan Bright");
                parent.setEmail(parentEmail);
                parent.setPassword(passwordEncoder.encode("parentPass123"));
                parent.setRole(Role.PARENT);

                // ✅ Save parent first to get managed entity
                Parent savedParent = parentRepository.save(parent);

                // ✅ Set parent on each student and save them individually
                for (Student student : students) {
                    student.setParent(savedParent);
                    studentRepository.save(student); // ✅ now student is managed and linked
                }

                System.out.println("✅ Parent seeded and linked to students: " + parentEmail);


            }
        };
    }}