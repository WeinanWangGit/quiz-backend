package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "sheet_id")
    private Integer sheetId;

    private double score;

    private String context;

    private String feedback;

    @Column(name = "is_marked")
    private boolean isMarked;

}
