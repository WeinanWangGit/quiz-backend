package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Sheet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;


    private double score;

    @Column(name = "is_submited")
    private boolean isSubmited;

    @Column(name = "is_marked")
    private boolean isMarked;


    //open the sheet time
    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "submit_time")
    private Timestamp submitTime;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;

    private Double similarity;

    @Column(name = "open_times")
    private Integer openTimes;

    @Column(name = "take_times")
    private Integer takeTimes;

    @Column(name = "anonymous")
    private Integer anonymous;


}
