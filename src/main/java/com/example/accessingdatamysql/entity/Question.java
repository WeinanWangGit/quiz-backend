package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    private String type;

    private String content;

    private String answer;

    private double mark;

    private String feedback;

    private String choice;

    private boolean save;

    private double score;

    @Column(name = "teacher_id")
    private int teacherId;
}
