package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    private String type;

    private String content;

    private String answer;

    private String choice;

    private double score;


    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
