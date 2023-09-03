package com.system.quiz.service.impl;

import com.system.quiz.dao.impl.QuestionDAOImpl;
import com.system.quiz.entity.Answer;
import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;
import com.system.quiz.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public List<QuestionDTO> getQuestionListByTeacherId(int teacherId) {
        return questionDAOImpl.getQuestionListByTeacherId(teacherId);
    }

    @Override
    public void addQuestionToTest(int testId, Question question) {
        questionDAOImpl.addQuestionToTest(testId, question);
    }

    @Override
    @Transactional
    public void editQuestion(int questionId, Question question) {
        questionDAOImpl.editQuestion(questionId, question);
    }

    @Override
    @Transactional
    public QuestionDTO createQuestion(Question question) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        question.setCreateTime(currentTimestamp);
        return questionDAOImpl.createQuestion(question);
    }

    @Override
    @Transactional
    public void deleteQuestion(int questionId) {
        questionDAOImpl.deleteQuestion(questionId);
    }



    @Override
    public void markAnswer(Question question, Answer answer) {
        String questionType = question.getType();
        String questionAnswer = question.getAnswer().trim(); // Trim spaces
        String answerContext = answer.getContext().trim(); // Trim spaces

        // Initialize score to 0
        double score = 0.0;

        if ("Blank".equalsIgnoreCase(questionType)) {
            // For Blank type questions, compare the context and answer ignoring case and spaces
            if (answerContext.equalsIgnoreCase(questionAnswer)) {
                score = question.getScore();
            }
        } else if ("Single".equalsIgnoreCase(questionType)) {
            // For Single choice questions, check if the context in the answer is equal to the answer in the question
            if (answerContext.equalsIgnoreCase(questionAnswer)) {
                score = question.getScore();
            }
        } else if ("Multiple".equalsIgnoreCase(questionType)) {
            // For Multiple choice questions, split the choices and the answer into arrays
            String[] questionChoices = question.getChoice().split(",");
            String[] questionAnswers = questionAnswer.split(",");
            String[] answerChoices = answerContext.split(",");

            // Calculate the score based on the choices
            for (String choice : answerChoices) {
                // Ignore leading/trailing spaces
                choice = choice.trim();

                // Check if the choice is in the question's answers
                if (containsIgnoreCase(questionAnswers, choice)) {
                    // Add points for a correct choice
                    score += question.getScore();
                } else if (!containsIgnoreCase(questionChoices, choice)) {
                    // Deduct points for an incorrect choice, but not less than 0 points
                    score = Math.max(0, score - question.getScore());
                }
            }
        }

        // Set the calculated score in the answer object
        answer.setScore(score);
        answer.setMarked(true);
        questionDAOImpl.saveAnswer(answer);
    }

    // Helper method to check if an array contains a value ignoring case
    private boolean containsIgnoreCase(String[] array, String value) {
        for (String item : array) {
            if (item.trim().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
