package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    private String title;

    private String description;

    private String type;

    private String time;

    @Column(name = "complete_type")
    private String completeType;

    @Column(name = "random_sort")
    private boolean randomSort;

    @Column(name = "submit_mode")
    private String submitMode;

    @Column(name = "retake_rule")
    private String retakeRule;

    @Column(name = "safe_check")
    private boolean safeCheck;

    @Column(name = "answer_show_model")
    private String answerShowModel;

    @Column(name = "teacher_id")
    private int teacherId;
}
