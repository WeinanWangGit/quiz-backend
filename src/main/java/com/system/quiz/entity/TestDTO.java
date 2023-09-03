package com.system.quiz.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TestDTO {

    private Integer id;

    private String title;

    private String description;

    private String type;

    private Integer time;

    private boolean timer;

    private double score;

    private Timestamp beginTime;

    private boolean safeCheck;

    private String completeType;

    private boolean randomSort;

    private String submitMode;

    private String retakeRule;

    private String answerShowModel;

    private List<QuestionDTO> questionDTOs;

}
