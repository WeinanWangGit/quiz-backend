package com.system.quiz.entity;

import lombok.Data;

@Data
public class MarkDTO {
    private Integer id;

    private Integer studentId;

    private String studentName;

    private TestDTO test;

    private Double similarity;

    private double score;

    private String department;

    private String major;

}
