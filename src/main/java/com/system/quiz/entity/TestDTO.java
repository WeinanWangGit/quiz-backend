package com.system.quiz.entity;

import lombok.Data;

@Data
public class TestDTO {

    private Integer id;

    private String title;

    private String description;

    private String type;

    private String time;

    private boolean safeCheck;

}
