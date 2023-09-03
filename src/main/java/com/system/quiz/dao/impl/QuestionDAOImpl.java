package com.system.quiz.dao.impl;

import com.system.quiz.dao.QuestionDAO;
import com.system.quiz.entity.Answer;
import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;
import com.system.quiz.entity.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private EntityManager entityManager;

    @Autowired
    public QuestionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public QuestionDTO createQuestion(Question question) {
        entityManager.persist(question);

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setCreateTime(question.getCreateTime());
        questionDTO.setUpdateTime(question.getUpdateTime());
        questionDTO.setType(question.getType());
        questionDTO.setContent(question.getContent());
        questionDTO.setAnswer(question.getAnswer());
        questionDTO.setChoice(question.getChoice());
        questionDTO.setScore(question.getScore());
        questionDTO.setTeacherId(question.getTeacher().getId()); // Assuming a Teacher property in Question entity

        return questionDTO;
    }

    @Override
    public void deleteQuestion(int questionId) {
        Question question = entityManager.find(Question.class, questionId);
        entityManager.remove(question);
    }

    @Override
    public Answer getAnswerByQuestionIdAndSheetId(Integer questionId, Integer sheetId) {
        String queryStr = "SELECT a FROM Answer a WHERE a.questionId = :questionId AND a.sheetId = :sheetId";
        TypedQuery<Answer> query = entityManager.createQuery(queryStr, Answer.class);
        query.setParameter("questionId", questionId);
        query.setParameter("sheetId", sheetId);

        List<Answer> answers = query.getResultList();

        if (!answers.isEmpty()) {
            return answers.get(0);
        }

        return null;
    }

    @Override
    public void saveAnswer(Answer answer) {
        this.entityManager.merge(answer);
    }


    @Override
    public List<Question> findAll() {
        String queryStr = "SELECT q FROM Question q";
        Query query = entityManager.createQuery(queryStr);
        return query.getResultList();
    }

    @Override
    public List<QuestionDTO> getQuestionListByTeacherId(int teacherId) {
        String queryStr = "SELECT q FROM Question q WHERE q.teacher.id = :teacherId";
        TypedQuery<Question> query = entityManager.createQuery(queryStr, Question.class);
        query.setParameter("teacherId", teacherId);
        List<Question> questionList = query.getResultList();

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        Boolean withAnswer = true;
        for (Question question : questionList) {
            QuestionDTO questionDTO = buildQuestionDTO(question, withAnswer);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
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
        existingQuestion.setScore(question.getScore());
        entityManager.merge(existingQuestion);
    }

    QuestionDTO buildQuestionDTO(Question question, boolean withAnswer) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setCreateTime(question.getCreateTime());
        questionDTO.setUpdateTime(question.getUpdateTime());
        questionDTO.setType(question.getType());
        questionDTO.setContent(question.getContent());
        if(withAnswer){
            questionDTO.setAnswer(question.getAnswer());
        }
        questionDTO.setChoice(question.getChoice());
        questionDTO.setScore(question.getScore());
        questionDTO.setTeacherId(question.getTeacher().getId());
        return questionDTO;
    }

    List<Integer> getQuestionIds(List<Question> questions) {
        List<Integer> questionIds = new ArrayList<>();
        for (Question question : questions) {
            questionIds.add(question.getId());
        }
        return questionIds;
    }

}
