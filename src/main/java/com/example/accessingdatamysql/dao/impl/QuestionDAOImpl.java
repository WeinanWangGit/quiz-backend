package com.example.accessingdatamysql.dao.impl;

import com.example.accessingdatamysql.dao.QuestionDAO;
import com.example.accessingdatamysql.entity.Question;
import com.example.accessingdatamysql.entity.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private EntityManager entityManager;

    @Autowired
    public QuestionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Question> findAll() {
        String queryStr = "SELECT q FROM Question q";
        Query query = entityManager.createQuery(queryStr);
        return query.getResultList();
    }

    @Override
    public List<Question> getQuestionListByTeacherId(int teacherId) {
        String queryStr = "SELECT q FROM Question q WHERE q.teacher.id = :teacherId";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    @Override
    public void addQuestionToTest(int testId, Question question) {
        Test test = entityManager.find(Test.class, testId);
        test.getQuestions().add(question);
        entityManager.merge(test);
    }


    @Override
    public void editQuestion(int questionId, Question question) {
        Question existingQuestion = entityManager.find(Question.class, questionId);
        existingQuestion.setContent(question.getContent());
        existingQuestion.setAnswer(question.getAnswer());
        existingQuestion.setType(question.getType());
        entityManager.merge(existingQuestion);
    }
}
