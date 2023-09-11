package com.system.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.quiz.QuizApplication;
import com.system.quiz.contorller.UserController;
import com.system.quiz.entity.User;
import com.system.quiz.entity.UserDTO;
import com.system.quiz.service.impl.UserServiceImpl;
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
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {QuizApplication.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Initialize MockMvc
    }

    @Test
    public void testLogin() throws Exception {
        // Prepare a sample request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("sub", "googleId");
        requestBody.put("email", "test@example.com");
        requestBody.put("name", "Test User");

        // Prepare a sample User
        User user = new User();

        // Mock the service method to return the sample User
        when(userService.findByEmail("test@example.com")).thenReturn(user);

        // Perform a POST request to the /user/login endpoint with JSON content
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(userService, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testSetUserRoleById() throws Exception {
        // Prepare a sample request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("role", "STUDENT");
        requestBody.put("number", "123456");
        requestBody.put("department", "Computer Science");
        requestBody.put("major", "Computer Engineering");

        // Prepare a sample User
        User user = new User();

        // Mock the service methods to return the sample User
        when(userService.getUserById(1)).thenReturn(user);

        // Perform a POST request to the /user/role/{userId} endpoint with JSON content
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/role/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service methods were called
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(1)).saveUser(user);
    }

//    @Test
//    public void testUploadAvatar() throws Exception {
//        // Prepare a sample MultipartFile
//        StandardMultipartHttpServletRequest multipartHttpServletRequest = new StandardMultipartHttpServletRequest();
//        MultipartFile avatarFile = multipartHttpServletRequest.getFile("avatar");
//        avatarFile = new StandardMultipartFile("avatar", "test.jpg", "image/jpeg", new byte[1024]);
//
//        // Prepare a sample User
//        User user = new User();
//
//        // Mock the service methods to return the sample User
//        when(userService.getUserById(1)).thenReturn(user);
//        when(userService.saveUser(user)).thenReturn(user);
//
//        // Perform a POST request to the /user/avatar/{userId} endpoint with MultipartFile
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/user/avatar/{userId}", 1)
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .param("avatar", "test.jpg")
//                        .content(avatarFile.getBytes()))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Verify that the service methods were called
//        verify(userService, times(1)).getUserById(1);
//        verify(userService, times(1)).saveUser(user);
//    }

    @Test
    public void testGetUserById() throws Exception {
        // Prepare a sample User
        User user = new User();

        // Mock the service method to return the sample User
        when(userService.getUserById(1)).thenReturn(user);

        // Perform a GET request to the /user/{userId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void testGetCurrentUserInfoById() throws Exception {
        // Prepare a sample UserDTO
        UserDTO userDTO = new UserDTO();

        // Mock the service method to return the sample UserDTO
        when(userService.getCurrentUserInfoById(1)).thenReturn(userDTO);

        // Perform a GET request to the /user/info/{userId} endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/user/info/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(userService, times(1)).getCurrentUserInfoById(1);
    }

    @Test
    public void testGetUserList() throws Exception {
        // Prepare a sample list of User objects
        List<User> userList = new ArrayList<>();
        // Add some User objects to the list as needed

        // Mock the service method to return the sample list of User objects
        when(userService.findAll()).thenReturn(userList);

        // Perform a GET request to the /user/all endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verify that the service method was called
        verify(userService, times(1)).findAll();
    }
}
