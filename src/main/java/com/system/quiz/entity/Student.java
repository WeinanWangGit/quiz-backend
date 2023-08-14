package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String number;

    private String department;

    private String major;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;  // Association mapping with User entity


}
