package com.example.accessingdatamysql.dao.impl;

import com.example.accessingdatamysql.dao.SheetDAO;
import com.example.accessingdatamysql.entity.Answer;
import com.example.accessingdatamysql.entity.Sheet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SheetDAOImpl implements SheetDAO {

    private EntityManager entityManager;

    @Autowired
    public SheetDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Sheet> getSheetListByStudentId(int studentId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s.test FROM Sheet s WHERE s.student.id = :studentId",
                Sheet.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    @Override
    public Sheet getSheetByTestIdAndStudentId(int testId, int studentId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s.test FROM Sheet s WHERE s.test.id = :testId AND s.student.id = :studentId",
                Sheet.class);
        query.setParameter("testId", testId);
        query.setParameter("studentId", studentId);
        return query.getSingleResult();
    }



    @Override
    public void saveSheetQuestionAnswer(int questionId, int sheetId, String answer) {
        Answer newAnswer = new Answer();
        newAnswer.setQuestionId(questionId);
        newAnswer.setContext(answer);
        entityManager.persist(newAnswer);
    }


    @Override
    public void submitTestSheet(Sheet sheet) {
        entityManager.merge(sheet);
    }

    @Override
    public Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s.test FROM Sheet s WHERE s.test.id = :testId AND s.student.id = :studentId AND s.isMarked = true",
                Sheet.class);
        query.setParameter("testId", testId);
        query.setParameter("studentId", studentId);
        return query.getSingleResult();
    }

    @Override
    public List<Sheet> getMarkListByStudentId(int studentId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s.test FROM Sheet s WHERE s.student.id = :studentId AND s.isMarked = true",
                Sheet.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }
}
