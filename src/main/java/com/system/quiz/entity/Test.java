package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    private String title;

    private String description;

    private String type;

    private Integer time;

    @Column(name = "timer")
    private boolean timer;

    private double score;

    //the time allow to open sheet
    @Column(name = "begin_time")
    private Timestamp beginTime;

    @Column(name = "end_time")
    private Timestamp endTime;

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
    private Integer teacherId;

    @ManyToMany
    @JoinTable(
            name = "test_question",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    public boolean getTimer() {
        return timer;
    }

    public void setTimer(boolean timer){
        this.timer = timer;
    }

    public boolean isRandomSort() {
        return randomSort;
    }


}
