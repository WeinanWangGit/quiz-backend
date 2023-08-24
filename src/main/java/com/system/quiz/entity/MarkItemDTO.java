package com.system.quiz.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MarkItemDTO {
    private Integer id;

    private Integer studentId;

    private String studentName;

    private Double similarity;

    private double score;

    private String title;

    private boolean timer;

    private Integer time;

    private Timestamp beginTime;

    private boolean safeCheck;

    private String department;

    private String major;

    private boolean isMarked;

}
