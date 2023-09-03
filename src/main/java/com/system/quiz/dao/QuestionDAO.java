package com.system.quiz.dao;

import com.system.quiz.entity.Answer;
import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;

import java.util.List;

public interface QuestionDAO {
    List<Question> findAll();

    List<QuestionDTO> getQuestionListByTeacherId(int teacherId);

    void addQuestionToTest(int testId, Question question);

    void editQuestion(int questionId, Question question);

    QuestionDTO createQuestion(Question question);

    void deleteQuestion(int questionId);

    Answer getAnswerByQuestionIdAndSheetId(Integer questionId, Integer id);

    void saveAnswer(Answer answer);
}
