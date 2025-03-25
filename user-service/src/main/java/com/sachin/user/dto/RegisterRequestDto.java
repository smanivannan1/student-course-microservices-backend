package com.sachin.user.dto;

import com.sachin.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String name;
    private String username;

    private String email;

    private String password;

    private Role role; // Optional — default to "STUDENT" in your service if not provided



}
