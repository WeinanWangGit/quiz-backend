package com.system.quiz.entity;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String username;

    private String email;

    private String role;
    private byte[] avatar;

    private String number;

    private String department;

    private String major;

    private Integer roleId;
}
