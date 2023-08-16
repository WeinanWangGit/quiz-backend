package com.system.quiz.entity;

import lombok.Data;

@Data
public class MarkDTO {
    private Integer id;

    private Integer studentId;

    private TestDTO test;

    private Double similarity;

}
