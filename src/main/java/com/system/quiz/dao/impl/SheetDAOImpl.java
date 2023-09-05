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

        Answer dbAnswer = getAnswerBySheetIdAndQuestionId(sheetId, questionId);


        if (dbAnswer != null) {
            double oldScore = dbAnswer.getScore();

            dbAnswer.setContext(answer.getContext());
            dbAnswer.setScore(answer.getScore());
            dbAnswer.setFeedback(answer.getFeedback());
            dbAnswer.setMarked(true);
            dbAnswer.setUpdateTime(new Timestamp(currentTimeMillis));
            entityManager.merge(dbAnswer);
            Sheet sheet = entityManager.find(Sheet.class, sheetId);
            double newScore = sheet.getScore() - oldScore + answer.getScore();
            sheet.setScore(newScore);

            return dbAnswer;
        } else {
            answer.setCreateTime(new Timestamp(currentTimeMillis));
            answer.setUpdateTime(new Timestamp(currentTimeMillis));
            entityManager.persist(answer);
            return answer;
        }
    }

    private Answer getAnswerBySheetIdAndQuestionId(int sheetId, int questionId) {
        TypedQuery<Answer> query = entityManager.createQuery(
                "SELECT a FROM Answer a WHERE a.sheetId = :sheetId AND a.questionId = :questionId",
                Answer.class
        );
        query.setParameter("sheetId", sheetId);
        query.setParameter("questionId", questionId);

        List<Answer> answers = query.getResultList();

        // Check if any answers were found
        if (!answers.isEmpty()) {
            // Return the first answer (assuming there should be only one answer per sheet and question)
            return answers.get(0);
        } else {
            return null; // No answer found for the given sheet and question
        }
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
    public List<MarkItemDTO> getMarkListByStudentId(int studentId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s FROM Sheet s WHERE s.student.id = :studentId AND s.isMarked = true",
                Sheet.class);
        query.setParameter("studentId", studentId);
        List<Sheet> markedSheets = query.getResultList();
        List<MarkItemDTO> markItemDTOList = new ArrayList<>();

        return buildMarkItemDTOList(markedSheets, markItemDTOList);
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
            sheet.setSubmited(false);

            entityManager.persist(sheet);
        }
    }
    @Override
    public List<MarkItemDTO> getMarkListByTestId(int testId) {
//        AND s.isSubmited = true  show all test not just submited
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s FROM Sheet s WHERE s.test.id = :testId",
                Sheet.class);
        query.setParameter("testId", testId);

        List<Sheet> markedSheets = query.getResultList();
        List<MarkItemDTO> markItemDTOList = new ArrayList<>();

        return buildMarkItemDTOList(markedSheets, markItemDTOList);
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
    public List<MarkItemDTO> getMarkListByTeacherId(int teacherId) {
        TypedQuery<Sheet> query = entityManager.createQuery(
                "SELECT s FROM Sheet s WHERE s.test.teacherId = :teacherId AND s.isSubmited = true ",
                Sheet.class);
        query.setParameter("teacherId", teacherId);

        List<Sheet> markedSheets = query.getResultList();
        List<MarkItemDTO> markItemDTOList = new ArrayList<>();

        return buildMarkItemDTOList(markedSheets, markItemDTOList);
    }

    @Override
    public MarkDTO getMarkDTOBySheetId(int sheetId) {
        Sheet sheet = entityManager.find(Sheet.class, sheetId);
        if (sheet == null) {
            return null; // Sheet with the given ID doesn't exist
        }

        MarkDTO markDTO = buildMarkDTO(sheet);

        return markDTO;
    }

    @Override
    public void updateSheet(Sheet sheet) {
        entityManager.merge(sheet);
    }

    private MarkDTO buildMarkDTO(Sheet sheet) {
        MarkDTO markDTO = new MarkDTO();
        Test test = sheet.getTest();
        List<Question> questions = test.getQuestions();
        List<Answer> answers = getAnswersBySheetIdAndQuestionIds(sheet.getId(), questionDAOImpl.getQuestionIds(questions));

        // Set sheet info
        markDTO.setId(sheet.getId());
        markDTO.setStudentId(sheet.getStudent().getId());
        markDTO.setStudentName(sheet.getStudent().getUser().getOriginalUsername());
        markDTO.setScore(sheet.getScore());
        markDTO.setMajor(sheet.getStudent().getMajor());
        markDTO.setDepartment(sheet.getStudent().getDepartment());
        markDTO.setSimilarity(sheet.getSimilarity());
        boolean isAnonymous = true;
        if(sheet.getAnonymous() == null ){
            isAnonymous = true;
        }else if (sheet.getAnonymous() == 0 ) {
            isAnonymous = false;
        }else{
            isAnonymous = true;
        }
        markDTO.setAnonymous(isAnonymous);

        // Set testInfo
        markDTO.setTime(test.getTime());
        markDTO.setTitle(test.getTitle());
        markDTO.setType(test.getType());
        markDTO.setTimer(test.getTimer());
        markDTO.setTotalScore(test.getScore());
        markDTO.setBeginTime(test.getBeginTime());
        markDTO.setSafeCheck(test.isSafeCheck());
        markDTO.setAnswerShowModel(test.getAnswerShowModel());

        String teacherName = "";
        // Set questionDTOs
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = questionDAOImpl.buildQuestionDTO(question, true);
            questionDTOs.add(questionDTO);
             teacherName = question.getTeacher().getUser().getOriginalUsername();
        }
        markDTO.setQuestionDTOs(questionDTOs);


        if (!isAnonymous){
            markDTO.setTeacherName(teacherName);
        }

        markDTO.setAnswers(answers);

        return markDTO;
    }



    private List<Answer> getAnswersBySheetIdAndQuestionIds(int sheetId, List<Integer> questionIds) {
        TypedQuery<Answer> query = entityManager.createQuery(
                "SELECT a FROM Answer a WHERE a.sheetId = :sheetId AND a.questionId IN :questionIds",
                Answer.class
        );
        query.setParameter("sheetId", sheetId);
        query.setParameter("questionIds", questionIds);

        return query.getResultList();
    }


    private List<MarkItemDTO> buildMarkItemDTOList(List<Sheet> markedSheets, List<MarkItemDTO> markItemDTOList) {
        for (Sheet markedSheet : markedSheets) {
            MarkItemDTO marItemDTO = new MarkItemDTO();
            marItemDTO.setId(markedSheet.getId());
            marItemDTO.setStudentId(markedSheet.getStudent().getId());
            marItemDTO.setStudentName(markedSheet.getStudent().getUser().getOriginalUsername());
            marItemDTO.setScore(markedSheet.getScore());
            marItemDTO.setSimilarity(markedSheet.getSimilarity());
            marItemDTO.setMajor(markedSheet.getStudent().getMajor());
            marItemDTO.setDepartment(markedSheet.getStudent().getDepartment());
            marItemDTO.setMarked(markedSheet.isMarked());
            marItemDTO.setSubmited(markedSheet.isSubmited());

            Test test = markedSheet.getTest();
            marItemDTO.setTitle(test.getTitle());
            marItemDTO.setBeginTime(test.getBeginTime());
            marItemDTO.setTimer(test.getTimer());
            marItemDTO.setSafeCheck(test.isSafeCheck());
            marItemDTO.setTime(test.getTime());


            markItemDTOList.add(marItemDTO);
        }

        return markItemDTOList;

    }


//    private List<MarkDTO> buildMarkDTOList(List<Sheet> markedSheets, List<MarkDTO> markDTOList) {
//        for (Sheet markedSheet : markedSheets) {
//            MarkDTO markDTO = new MarkDTO();
//            markDTO.setId(markedSheet.getId());
//            markDTO.setStudentId(markedSheet.getStudent().getId());
//            markDTO.setStudentName(markedSheet.getStudent().getUser().getOriginalUsername());
//            markDTO.setScore(markedSheet.getScore());
//            markDTO.setMajor(markedSheet.getStudent().getMajor());
//            markDTO.setDepartment(markedSheet.getStudent().getDepartment());
//
//            boolean withQuestion = false;
//            boolean withAnswer = false;
//            TestDTO testDTO = buildTestDTO(markedSheet, withQuestion, withAnswer);
//
//            markDTO.setTest(testDTO);
//
//            markDTO.setSimilarity(markedSheet.getSimilarity());
//
//            markDTOList.add(markDTO);
//        }
//
//        return markDTOList;
//    }


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
        testDTO.setAnswerShowModel(test.getAnswerShowModel());
        testDTO.setBeginTime(test.getBeginTime());
        testDTO.setEndTime(test.getEndTime());
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
        sheetDTO.setOpenTimes(sheet.getOpenTimes());
        sheetDTO.setTakeTimes(sheet.getTakeTimes());
        return sheetDTO;
    }

}
