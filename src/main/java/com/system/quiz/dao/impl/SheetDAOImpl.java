package com.system.quiz.dao.impl;

import com.system.quiz.dao.SheetDAO;
import com.system.quiz.entity.Answer;
import com.system.quiz.entity.Sheet;
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

    @Override
    public void saveSheet(Sheet sheet) {
        entityManager.persist(sheet);
    }

    @Override
    public Sheet getSheetById(int sheetId) {
        return entityManager.find(Sheet.class, sheetId);
    }

    @Override
    public List<Sheet> getSheetByTestId(int testId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s.test FROM Sheet s WHERE s.test.id = :testId",
                Sheet.class);
        query.setParameter("testId", testId);
        return query.getResultList();
    }


}
