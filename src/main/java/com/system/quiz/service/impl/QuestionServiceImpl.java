package com.system.quiz.service.impl;

import com.system.quiz.dao.impl.QuestionDAOImpl;
import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;
import com.system.quiz.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDAOImpl questionDAOImpl;

    @Autowired
    public QuestionServiceImpl(QuestionDAOImpl questionDAOImpl){
        this.questionDAOImpl = questionDAOImpl;
    }


    @Override
    public List<Question> findAll() {
        return questionDAOImpl.findAll();
    }

    @Override
    public List<QuestionDTO> getQuestionListByTeacherId(int teacherId) {
        return questionDAOImpl.getQuestionListByTeacherId(teacherId);
    }

    @Override
    public void addQuestionToTest(int testId, Question question) {
        questionDAOImpl.addQuestionToTest(testId, question);
    }

    @Override
    @Transactional
    public void editQuestion(int questionId, Question question) {
        questionDAOImpl.editQuestion(questionId, question);
    }

    @Override
    @Transactional
    public void createQuestion(Question question) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        question.setCreateTime(currentTimestamp);
        questionDAOImpl.createQuestion(question);
    }

    @Override
    @Transactional
    public void deleteQuestion(int questionId) {
        questionDAOImpl.deleteQuestion(questionId);
    }
}
