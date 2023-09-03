package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SheetDTO {

    private Integer id;

    private Integer studentId;

    private TestDTO test;

    private Double similarity;

    private Timestamp startTime;

    private Timestamp submitTime;

    private String studentName;

    private String studentNumber;

    private boolean isSubmited;

    private double correctnessRate;

}
