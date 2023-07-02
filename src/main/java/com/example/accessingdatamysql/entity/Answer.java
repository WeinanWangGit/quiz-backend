package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "question_id")
    private int questionId;

    @ManyToOne
    @JoinColumn(name = "sheet_id")
    private Sheet sheet;

    private double score;

    private String context;

    @Column(name = "is_marked")
    private boolean isMarked;

}