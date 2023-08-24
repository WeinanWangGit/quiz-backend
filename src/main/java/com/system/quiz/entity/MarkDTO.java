package com.system.quiz.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MarkDTO {
    private Integer id;

    private Integer studentId;

    private String studentName;

    private String title;

    private Integer time;

    private boolean timer;

    private double totalScore;

    private Timestamp beginTime;

    private boolean safeCheck;

    private String answerShowModel;

    private Double similarity;

    private double score;

    private String department;

    private String major;

    private List<QuestionDTO> questionDTOs;

    private List<Answer> answers;

}
