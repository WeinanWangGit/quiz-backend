package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dao.impl.QuestionDAOImpl;
import com.example.accessingdatamysql.entity.Question;
import com.example.accessingdatamysql.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Question> getQuestionListByTeacherId(int teacherId) {
        return questionDAOImpl.getQuestionListByTeacherId(teacherId);
    }

    @Override
    public void addQuestionToTest(int testId, Question question) {
        questionDAOImpl.addQuestionToTest(testId, question);
    }

    @Override
    public void editQuestion(int questionId, Question question) {
        questionDAOImpl.editQuestion(questionId, question);
    }
}
