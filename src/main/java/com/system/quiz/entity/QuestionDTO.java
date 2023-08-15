package com.system.quiz.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuestionDTO {

    private Integer id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String type;

    private String content;

    private String answer;

    private String choice;

    private double score;

    private Integer teacherId;
}
