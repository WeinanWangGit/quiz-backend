package com.system.quiz.dao.impl;

import com.system.quiz.dao.TestDAO;
import com.system.quiz.entity.Question;
import com.system.quiz.entity.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDAOImpl implements TestDAO {

    private EntityManager entityManager;

    @Autowired
    public TestDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Test> getTestListByTeacherId(int teacherId) {
        String queryStr = "SELECT t FROM Test t WHERE t.teacherId = :teacherId";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("teacherId", teacherId);
        return query.getResultList();
    }

    @Override
    public Test setTestSettings(Test test) {
        entityManager.merge(test);
        return test;
    }

    @Override
    public Test editTest(Test test) {
        entityManager.merge(test);
        return test;
    }

    @Override
    public Test createTest(Test test) {
         entityManager.persist(test);
         return test;
    }

    @Override
    public List<Test> findAll() {
        String queryStr = "SELECT t FROM Test t";
        Query query = entityManager.createQuery(queryStr);
        return query.getResultList();
    }

    @Override
    public void deleteTest(int testId) {
        Test test = entityManager.find(Test.class, testId);
        entityManager.remove(test);
    }

    @Override
    public Test findTestById(int testId) {
        return entityManager.find(Test.class, testId);
    }



}
