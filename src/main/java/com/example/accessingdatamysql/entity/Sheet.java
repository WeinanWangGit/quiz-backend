package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Sheet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "test_id")
    private int testId;

    private double score;

    @Column(name = "is_marked")
    private boolean isMarked;

    @Column(name = "begin_time")
    private Timestamp beginTime;

    @Column(name = "submit_time")
    private Timestamp submitTime;
}
