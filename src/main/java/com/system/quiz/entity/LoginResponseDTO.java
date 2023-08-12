package com.system.quiz.entity;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String id;
    private String username;
    private String email;
    private String role;
}

