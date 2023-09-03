package com.system.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.quiz.config.TestSecurityConfiguration;
import com.system.quiz.contorller.QuestionController;
import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;
import com.system.quiz.service.impl.QuestionServiceImpl;
import com.system.quiz.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

//@WebMvcTest(QuestionController.class)
//@Import(TestSecurityConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionServiceImpl questionService;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testCreateQuestion() throws Exception {
        // Mock data
        Question question = new Question();
        question.setId(1);
        question.setContent("Sample Question");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/question/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Question created successfully"));
    }

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
        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setId(1);
        questionDTO1.setContent("Question 1");

        QuestionDTO questionDTO2 = new QuestionDTO();
        questionDTO2.setId(2);
        questionDTO2.setContent("Question 2");

        List<QuestionDTO> questionList = Arrays.asList(questionDTO1, questionDTO2);

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
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEditQuestion() throws Exception {
        // Mock data
        Question question = new Question();
        question.setId(1);
        question.setContent("Updated Question");

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/question/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(question)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        int questionId = 1;

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/question/delete/{questionId}", questionId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
