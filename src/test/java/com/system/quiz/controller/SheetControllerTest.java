package com.system.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.quiz.QuizApplication;
import com.system.quiz.contorller.SheetController;
import com.system.quiz.entity.*;
import com.system.quiz.service.impl.SheetServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {QuizApplication.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class SheetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SheetServiceImpl sheetService;

    @InjectMocks
    private SheetController sheetController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sheetController).build(); // Initialize MockMvc
    }

    @Test
    public void testGetSheetListByStudentId() throws Exception {
        // Prepare a sample student ID
        int studentId = 123;

        // Mock the service method to return the sample SheetDTO list
        when(sheetService.getSheetDTOListByStudentId(studentId)).thenReturn(new ArrayList<>());

        // Perform a GET request to the /sheet/list/{studentId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/sheet/list/{studentId}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getSheetDTOListByStudentId(studentId);
    }

    @Test
    public void testGetSheetByTestIdAndStudentId() throws Exception {
        // Prepare sample test and student IDs
        int testId = 456;
        int studentId = 789;

        // Mock the service method to return the sample Sheet
        when(sheetService.getSheetByTestIdAndStudentId(testId, studentId)).thenReturn(new Sheet());

        // Perform a GET request to the /sheet/{testId}/{studentId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/sheet/{testId}/{studentId}", testId, studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getSheetByTestIdAndStudentId(testId, studentId);
    }

    @Test
    public void testGetSheetDTOById() throws Exception {
        // Prepare a sample sheet ID
        int sheetId = 123;

        // Mock the service method to return the sample SheetDTO
        when(sheetService.getSheetDTOById(sheetId)).thenReturn(new SheetDTO());

        // Perform a GET request to the /sheet/{sheetId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/sheet/{sheetId}", sheetId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getSheetDTOById(sheetId);
    }

    @Test
    public void testSaveSheetQuestionAnswer() throws Exception {
        // Prepare sample question ID, sheet ID, and answer
        int questionId = 456;
        int sheetId = 789;
        Answer answer = new Answer();

        // Mock the service method to return the sample Answer
        when(sheetService.saveSheetQuestionAnswer(questionId, sheetId, answer)).thenReturn(new Answer());

        // Perform a POST request to the /sheet/answer/save/{questionId}/{sheetId} endpoint with JSON content
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sheet/answer/save/{questionId}/{sheetId}", questionId, sheetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(answer)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).saveSheetQuestionAnswer(questionId, sheetId, answer);
    }

    @Test
    public void testSavePhoto() throws Exception {
        // Prepare a sample sheet ID and a mock MultipartFile
        int sheetId = 123;
        MultipartFile photoFile = mock(MultipartFile.class);

        // Perform a POST request to the /sheet/photo/save/{sheetId} endpoint with a mock MultipartFile
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sheet/photo/save/{sheetId}", sheetId)
                        .param("photo", "fakePhotoData")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the photo saving process was called as expected
        // verify(sheetService, times(1)).savePhoto(sheetId, photoData);
    }

    @Test
    public void testSubmitTestSheet() throws Exception {
        // Prepare sample sheet ID and start time
        int sheetId = 123;
        long startTimeMillis = System.currentTimeMillis();

        // Mock the service method to return the sample SheetDTO
        Sheet sheet = new Sheet();
        when(sheetService.getSheetById(sheetId)).thenReturn(sheet);

        // Perform a POST request to the /sheet/submit/{sheetId} endpoint with a start time parameter
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sheet/submit/{sheetId}", sheetId)
                        .param("startTime", String.valueOf(startTimeMillis)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).submitTestSheet(sheet, new Timestamp(startTimeMillis));
    }

    @Test
    public void testSaveStartTime() throws Exception {
        // Prepare sample sheet ID and start time
        int sheetId = 123;
        long startTimeMillis = System.currentTimeMillis();

        // Mock the service method to return the sample Sheet
        Sheet sheet = new Sheet();
        when(sheetService.getSheetById(sheetId)).thenReturn(sheet);

        // Perform a POST request to the /sheet/start/time/{sheetId} endpoint with a start time parameter
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/sheet/start/time/{sheetId}", sheetId)
                        .param("startTime", String.valueOf(startTimeMillis)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).saveStartTime(sheet, new Timestamp(startTimeMillis));
    }

    @Test
    public void testGetPhotoCompare() throws Exception {
        // Prepare a sample sheet ID
        int sheetId = 123;

        // Mock the service method to return the sample FaceCompareDTO
        when(sheetService.getPhotoCompare(any(Sheet.class))).thenReturn(new FaceCompareDTO());

        // Perform a GET request to the /mark/compare/{sheetId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/mark/compare/{sheetId}", sheetId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getPhotoCompare(any(Sheet.class));
    }

    @Test
    public void testPostMark() throws Exception {
        // Prepare sample sheet ID and anonymous flag
        int sheetId = 123;
        int isAnonymous = 1;

        // Mock the service method to post the mark
        doNothing().when(sheetService).postMark(any(Sheet.class), eq(isAnonymous));

        // Perform a POST request to the /mark/post/{sheetId}/{isAnonymous} endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/mark/post/{sheetId}/{isAnonymous}", sheetId, isAnonymous))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).postMark(any(Sheet.class), eq(isAnonymous));
    }

    @Test
    public void testGetMarkListByStudentId() throws Exception {
        // Prepare a sample student ID
        int studentId = 123;

        // Mock the service method to return the sample list of MarkItemDTO objects
        when(sheetService.getMarkListByStudentId(studentId)).thenReturn(new ArrayList<>());

        // Perform a GET request to the /mark/list/student/{studentId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/mark/list/student/{studentId}", studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getMarkListByStudentId(studentId);
    }

    @Test
    public void testGetMarkListByTestId() throws Exception {
        // Prepare a sample test ID
        int testId = 123;

        // Mock the service method to return the sample list of MarkItemDTO objects
        when(sheetService.getMarkListByTestId(testId)).thenReturn(new ArrayList<>());

        // Perform a GET request to the /mark/list/test/{testId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/mark/list/test/{testId}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getMarkListByTestId(testId);
    }

    @Test
    public void testGetMarkListByTeacherIdOrTestId() throws Exception {
        // Prepare sample teacher ID and test ID
        int teacherId = 123;
        int testId = 456;

        // Mock the service method to return the sample list of MarkItemDTO objects
        when(sheetService.getMarkListByTeacherIdOrTestId(teacherId, testId)).thenReturn(new ArrayList<>());

        // Perform a GET request to the /mark/list/{teacherId}/{testId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/mark/list/{teacherId}/{testId}", teacherId, testId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getMarkListByTeacherIdOrTestId(teacherId, testId);
    }

    @Test
    public void testGetMarkSheetByTestIdAndStudentId() throws Exception {
        // Prepare sample test ID and student ID
        int testId = 123;
        int studentId = 456;

        // Mock the service method to return the sample Sheet
        when(sheetService.getMarkSheetByTestIdAndStudentId(testId, studentId)).thenReturn(new Sheet());

        // Perform a GET request to the /mark/{testId}/{studentId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/mark/{testId}/{studentId}", testId, studentId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getMarkSheetByTestIdAndStudentId(testId, studentId);
    }

    @Test
    public void testGetMarkDTOBySheetId() throws Exception {
        // Prepare a sample sheet ID
        int sheetId = 123;

        // Mock the service method to return the sample MarkDTO
        when(sheetService.getMarkDTOBySheetId(sheetId)).thenReturn(new MarkDTO());

        // Perform a GET request to the /mark/{sheetId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/mark/{sheetId}", sheetId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(sheetService, times(1)).getMarkDTOBySheetId(sheetId);
    }

}
