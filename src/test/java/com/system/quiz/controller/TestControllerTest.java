package com.system.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.quiz.QuizApplication;
import com.system.quiz.contorller.TestController;
import com.system.quiz.service.impl.TestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;



@SpringBootTest
@ContextConfiguration(classes = {QuizApplication.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class TestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TestServiceImpl testService;

    @InjectMocks
    private TestController testController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(testController).build(); // Initialize MockMvc
    }

    @Test
    public void testCreateTest() throws Exception {
        // Prepare a sample Test object and request body
        com.system.quiz.entity.Test test = new com.system.quiz.entity.Test();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("test", test);
        requestBody.put("taker", new HashMap<>());

        // Prepare a sample created Test
        com.system.quiz.entity.Test createdTest = new com.system.quiz.entity.Test();

        // Mock the service method to return the sample created Test
        when(testService.createTest(test, new HashMap<>())).thenReturn(createdTest);

        // Perform a POST request to the /test/create endpoint with JSON content
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/test/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(testService, times(1)).createTest(test, new HashMap<>());
    }

    @Test
    public void testEditTest() throws Exception {
        // Prepare a sample Test object
        com.system.quiz.entity.Test test = new com.system.quiz.entity.Test();

        // Prepare a sample edited Test
        com.system.quiz.entity.Test editedTest = new com.system.quiz.entity.Test();;

        // Mock the service method to return the sample edited Test
        when(testService.editTest(test)).thenReturn(editedTest);

        // Perform a PUT request to the /test/edit endpoint with JSON content
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/test/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(test)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(testService, times(1)).editTest(test);
    }

    @Test
    public void testSetTestSettings() throws Exception {
        // Prepare a sample Test object
        com.system.quiz.entity.Test test = new com.system.quiz.entity.Test();

        // Prepare a sample updated Test
        com.system.quiz.entity.Test updatedTest = new com.system.quiz.entity.Test();

        // Mock the service method to return the sample updated Test
        when(testService.setTestSettings(test)).thenReturn(updatedTest);

        // Perform a POST request to the /test/setting endpoint with JSON content
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/test/setting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(test)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(testService, times(1)).setTestSettings(test);
    }

    @Test
    public void testGetTestListByTeacherId() throws Exception {
        // Prepare a sample teacher ID
        int teacherId = 123;

        // Mock the service method to return the sample list of Test objects
        when(testService.getTestListByTeacherId(teacherId)).thenReturn(new ArrayList<>());

        // Perform a GET request to the /test/list/{teacherId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/test/list/{teacherId}", teacherId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(testService, times(1)).getTestListByTeacherId(teacherId);
    }

    @Test
    public void testGetTestList() throws Exception {

        // Mock the service method to return the sample list of Test objects
        when(testService.findAll()).thenReturn(new ArrayList<>());

        // Perform a GET request to the /tests endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/tests"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(testService, times(1)).findAll();
    }

    @Test
    public void testDeleteTest() throws Exception {
        // Prepare a sample test ID
        int testId = 789;

        // Mock the service method to delete the test
        doNothing().when(testService).deleteTest(testId);

        // Perform a DELETE request to the /test/delete/{testId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.delete("/test/delete/{testId}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(testService, times(1)).deleteTest(testId);
    }
}
