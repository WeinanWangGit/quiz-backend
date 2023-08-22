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

    QuestionDAOImpl questionDAOImpl;

    private EntityManager entityManager;

    @Autowired
    public SheetDAOImpl(EntityManager entityManager, QuestionDAOImpl questionDAOImpl) {
        this.entityManager = entityManager;
        this.questionDAOImpl = questionDAOImpl;
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
            boolean withQuestion = false;
            boolean withAnswer = false;
            SheetDTO sheetDTO = buildSheetDTO(sheet, withQuestion, withAnswer);

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
    public Answer saveSheetQuestionAnswer(int questionId, int sheetId, Answer answer) {
        long currentTimeMillis = System.currentTimeMillis();

        if (answer.getId() == null || answer.getId() <= 0) {
            answer.setCreateTime(new Timestamp(currentTimeMillis));
            answer.setUpdateTime(new Timestamp(currentTimeMillis));
            entityManager.persist(answer);
        } else {
            answer.setUpdateTime(new Timestamp(currentTimeMillis));
            entityManager.merge(answer);
        }
        return answer;
    }


    @Override
    public SheetDTO submitTestSheet(Sheet sheet) {
        boolean withQuestion = false;
        boolean withAnswer = false;
        SheetDTO sheetDTO = buildSheetDTO(sheet, withQuestion, withAnswer);
        entityManager.merge(sheet);
        return sheetDTO;
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

        return buildMarkDTO(markedSheets, markDTOList);
    }

    @Override
    public SheetDTO getSheetDTOById(int sheetId) {
        Sheet sheet = entityManager.find(Sheet.class, sheetId);
        if (sheet == null) {
            return null; // Sheet with the given ID doesn't exist
        }

        boolean withQuestion = true;
        boolean withAnswer = false;
        SheetDTO sheetDTO = buildSheetDTO(sheet, withQuestion, withAnswer);

        return sheetDTO;
    }


    @Override
    public List<MarkDTO> getMarkListByTeacherId(int teacherId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s FROM Sheet s WHERE s.test.teacherId = :teacherId AND s.isMarked = true ",
                Sheet.class);
        query.setParameter("teacherId", teacherId);

        List<Sheet> markedSheets = query.getResultList();
        List<MarkDTO> markDTOList = new ArrayList<>();

        return buildMarkDTO(markedSheets, markDTOList);
    }


    private List<MarkDTO> buildMarkDTO(List<Sheet> markedSheets, List<MarkDTO> markDTOList) {
        for (Sheet markedSheet : markedSheets) {
            MarkDTO markDTO = new MarkDTO();
            markDTO.setId(markedSheet.getId());
            markDTO.setStudentId(markedSheet.getStudent().getId());
            markDTO.setStudentName(markedSheet.getStudent().getUser().getOriginalUsername());
            markDTO.setScore(markedSheet.getScore());
            markDTO.setMajor(markedSheet.getStudent().getMajor());
            markDTO.setDepartment(markedSheet.getStudent().getDepartment());

            boolean withQuestion = false;
            boolean withAnswer = false;
            TestDTO testDTO = buildTestDTO(markedSheet, withQuestion, withAnswer);

            markDTO.setTest(testDTO);



            markDTO.setSimilarity(markedSheet.getSimilarity());

            markDTOList.add(markDTO);
        }

        return markDTOList;
    }


    private TestDTO buildTestDTO(Sheet sheet, boolean withQuestion, boolean withAnswer){
        TestDTO testDTO = new TestDTO();
        Test test = sheet.getTest();
        testDTO.setId(test.getId());
        testDTO.setTitle(test.getTitle());
        testDTO.setDescription(test.getDescription());
        testDTO.setType(test.getType());
        testDTO.setTime(test.getTime());
        testDTO.setSafeCheck(test.isSafeCheck());
        testDTO.setCompleteType(test.getCompleteType());
        testDTO.setRandomSort(test.isRandomSort());
        testDTO.setRetakeRule(test.getRetakeRule());
        testDTO.setSubmitMode(test.getSubmitMode());
        testDTO.setBeginTime(test.getBeginTime());
        testDTO.setTimer(test.getTimer());
        if(withQuestion){
            List<Question> questions = test.getQuestions();
            List<QuestionDTO> questionDTOS = new ArrayList<>();

            for (Question question : questions) {
                QuestionDTO questionDTO = questionDAOImpl.buildQuestionDTO(question, withAnswer);
                questionDTOS.add(questionDTO);
            }
            testDTO.setQuestionDTOs(questionDTOS);
        }
        return testDTO;
    }


    private SheetDTO buildSheetDTO(Sheet sheet, boolean withQuestion, boolean withAnswer) {
        SheetDTO sheetDTO = new SheetDTO();
        sheetDTO.setId(sheet.getId());
        sheetDTO.setStudentId(sheet.getStudent().getId());
        sheetDTO.setTest(buildTestDTO(sheet, withQuestion, withAnswer));
        sheetDTO.setSimilarity(sheet.getSimilarity());
        sheetDTO.setSubmitTime(sheet.getSubmitTime());
        sheetDTO.setStartTime(sheet.getStartTime());
        sheetDTO.setStudentName(sheet.getStudent().getUser().getOriginalUsername());
        sheetDTO.setStudentNumber(sheet.getStudent().getNumber());
        return sheetDTO;
    }

}
