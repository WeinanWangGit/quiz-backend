package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();

    List<Question> getQuestionListByTeacherId(int teacherId);

    void addQuestionToTest(int testId, Question question);

    void editQuestion(int questionId, Question question);
}
