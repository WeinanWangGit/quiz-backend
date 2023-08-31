package com.system.quiz.service;

import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();

    List<QuestionDTO> getQuestionListByTeacherId(int teacherId);

    void addQuestionToTest(int testId, Question question);

    void editQuestion(int questionId, Question question);

    QuestionDTO createQuestion(Question question);

    void deleteQuestion(int questionId);
}
