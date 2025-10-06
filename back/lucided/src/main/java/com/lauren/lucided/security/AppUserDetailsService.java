package com.lauren.lucided.security;

import com.lauren.lucided.model.AppUser;
import com.lauren.lucided.repository.EducatorRepository;
import com.lauren.lucided.repository.ParentRepository;
import com.lauren.lucided.repository.StudentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepo;
    private final EducatorRepository educatorRepo;
    private final ParentRepository parentRepo;

    public AppUserDetailsService(StudentRepository studentRepo, EducatorRepository educatorRepo, ParentRepository parentRepo) {
        this.studentRepo = studentRepo;
        this.educatorRepo = educatorRepo;
        this.parentRepo = parentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return studentRepo.findByEmail(email)
                .map(user -> buildUserDetails(user))
                .or(() -> educatorRepo.findByEmail(email).map(user -> buildUserDetails(user)))
                .or(() -> parentRepo.findByEmail(email).map(user -> buildUserDetails(user)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UserDetails buildUserDetails(AppUser user) {
        return new AppUserDetails(user);
    }





}