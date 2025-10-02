package com.lauren.lucided.dto;

import com.lauren.lucided.model.AppUser.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    private Role role;
}