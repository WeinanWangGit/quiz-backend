package com.system.quiz.dao.impl;

import com.system.quiz.dao.SheetDAO;
import com.system.quiz.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SheetDAOImpl implements SheetDAO {

    private EntityManager entityManager;

    @Autowired
    public SheetDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * get test of the sheet
     * @param studentId
     * @return
     */
    public List<SheetDTO> getSheetDTOListByStudentId(int studentId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s FROM Sheet s WHERE s.student.id = :studentId",
                Sheet.class
        );
        query.setParameter("studentId", studentId);

        List<Sheet> sheetList = query.getResultList();
        List<SheetDTO> sheetDTOList = new ArrayList<>();

        for (Sheet sheet : sheetList) {
            SheetDTO sheetDTO = new SheetDTO();
            sheetDTO.setId(sheet.getId());
            sheetDTO.setSimilarity(sheet.getSimilarity());

            TestDTO testDTO = buildTestDTO(sheet);

            sheetDTO.setTest(testDTO);

            sheetDTOList.add(sheetDTO);
        }

        return sheetDTOList;
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

    @Override
    public void generateSheet(Integer testId, ArrayList<Integer> studentIds) {
        Test test = entityManager.find(Test.class, testId);

        for (Integer studentId : studentIds) {
            Student student = entityManager.find(Student.class, studentId);

            Sheet sheet = new Sheet();
            sheet.setTest(test);
            sheet.setStudent(student);
            sheet.setCreateTime(new Timestamp(System.currentTimeMillis()));
            sheet.setUpdateTime(sheet.getCreateTime());
            sheet.setMarked(false);

            entityManager.persist(sheet);
        }
    }
    @Override
    public List<MarkDTO> getMarkListByTestId(int testId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s FROM Sheet s WHERE s.test.id = :testId AND s.isMarked = true",
                Sheet.class);
        query.setParameter("testId", testId);

        List<Sheet> markedSheets = query.getResultList();
        List<MarkDTO> markDTOList = new ArrayList<>();

        for (Sheet markedSheet : markedSheets) {
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(markedSheet.getId());
            markDTO.setStudentId(markedSheet.getStudent().getId());

            TestDTO testDTO = buildTestDTO(markedSheet);

            markDTO.setTest(testDTO);



            markDTO.setSimilarity(markedSheet.getSimilarity());

            markDTOList.add(markDTO);
        }

        return markDTOList;
    }


    private TestDTO buildTestDTO(Sheet sheet){
        TestDTO testDTO = new TestDTO();
        testDTO.setId(sheet.getTest().getId());
        testDTO.setTitle(sheet.getTest().getTitle());
        testDTO.setDescription(sheet.getTest().getDescription());
        testDTO.setType(sheet.getTest().getType());
        testDTO.setTime(sheet.getTest().getTime());
        testDTO.setSafeCheck(sheet.getTest().isSafeCheck());
        return testDTO;
    }

}
