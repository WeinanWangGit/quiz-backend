package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;

    private String email;

    private String role;

    @Lob
    @Column(name = "avatar", columnDefinition = "BLOB")
    private byte[] avatar; // Field for avatar as BLOB
}

