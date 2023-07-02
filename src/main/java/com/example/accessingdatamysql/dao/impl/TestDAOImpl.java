package com.example.accessingdatamysql.dao.impl;

import com.example.accessingdatamysql.dao.TestDAO;
import com.example.accessingdatamysql.entity.Test;
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
    public void setTestSettings(Test test) {
        entityManager.merge(test);
    }

    @Override
    public void editTest(Test test) {
        entityManager.merge(test);
    }

    @Override
    public void createTest(Test test) {
        entityManager.persist(test);
    }
}
