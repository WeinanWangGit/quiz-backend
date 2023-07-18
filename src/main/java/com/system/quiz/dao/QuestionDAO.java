package com.system.quiz.dao;

import com.system.quiz.entity.Question;

import java.util.List;

public interface QuestionDAO {
    List<Question> findAll();

    List<Question> getQuestionListByTeacherId(int teacherId);

    void addQuestionToTest(int testId, Question question);

    void editQuestion(int questionId, Question question);

    void createQuestion(Question question);

    void deleteQuestion(int questionId);
}