package com.system.quiz.controller;

import com.system.quiz.contorller.QuestionController;
import com.system.quiz.entity.Question;
import com.system.quiz.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionServiceImpl questionService;

    @Test
    public void testGetQuestionList() throws Exception {
        // Mock data
        Question question1 = new Question();
        question1.setId(1);
        question1.setContent("Question 1");

        Question question2 = new Question();
        question2.setId(2);
        question2.setContent("Question 2");

        List<Question> questionList = Arrays.asList(question1, question2);

        // Mock the service method
        Mockito.when(questionService.findAll()).thenReturn(questionList);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/questions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(questionList.size()));
    }

    @Test
    public void testGetQuestionListByTeacherId() throws Exception {
        // Mock data
        int teacherId = 1;
        Question question1 = new Question();
        question1.setId(1);
        question1.setContent("Question 1");

        Question question2 = new Question();
        question2.setId(2);
        question2.setContent("Question 2");

        List<Question> questionList = Arrays.asList(question1, question2);

        // Mock the service method
        Mockito.when(questionService.getQuestionListByTeacherId(teacherId)).thenReturn(questionList);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/question/list/{teacherId}", teacherId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(questionList.size()));
    }

    @Test
    public void testAddQuestionToTest() throws Exception {
        // Mock data
        int testId = 1;
        Question question = new Question();
        question.setId(1);
        question.setContent("Question 1");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/question/add/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"content\": \"Question 1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testEditQuestion() throws Exception {
        // Mock data
        int questionId = 1;
        Question question = new Question();
        question.setId(1);
        question.setContent("Updated Question");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/question/edit/{questionId}", questionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"content\": \"Updated Question\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
