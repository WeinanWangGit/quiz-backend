package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private String department;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;  // Association mapping with User entity
}
