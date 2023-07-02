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

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;


    private double score;

    @Column(name = "is_marked")
    private boolean isMarked;

    @Column(name = "begin_time")
    private Timestamp beginTime;

    @Column(name = "submit_time")
    private Timestamp submitTime;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] photo;
}
