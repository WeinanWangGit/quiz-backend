package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> findAll();

    List<Question> getQuestionListByTeacherId(int teacherId);

    void addQuestionToTest(int testId, Question question);

    void editQuestion(int questionId, Question question);
}
