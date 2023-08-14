package com.system.quiz.contorller;

import com.system.quiz.entity.*;
import com.system.quiz.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userServiceImpl;



    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody Map<String, String> requestBody) {
        String googleId = requestBody.get("sub");
        String email = requestBody.get("email");
        String name = requestBody.get("name");

        User user = userServiceImpl.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(createLoginResponse(user));
        } else {
            User newUser = new User();
            newUser.setGoogleId(googleId);
            newUser.setUsername(name);
            newUser.setEmail(email);
            newUser.setRole(Role.STUDENT);//set default role
            userServiceImpl.saveUser(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(createLoginResponse(newUser)); // 201 Created
        }
    }

    private LoginResponseDTO createLoginResponse(User user) {
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(String.valueOf(user.getRole()));

        return responseDTO;
    }



    @PostMapping("/role/{userId}")
    public ResponseEntity<LoginResponseDTO> setUserRoleById(@RequestBody Map<String, String> requestBody, @PathVariable("userId") Integer userId) {
        String role = requestBody.get("role");
        String number = requestBody.get("number");
        String department = requestBody.get("department");
        String major = requestBody.get("major");

        User user = userServiceImpl.getUserById(userId);

        user.setRole(Role.valueOf(role));

        userServiceImpl.saveUser(user);

        if(role.equals("STUDENT")){
            Student student = new Student();
            student.setNumber(number);
            student.setMajor(major);
            student.setDepartment(department);
            student.setUser(user);
            userServiceImpl.saveStudent(student);

        } else if (role.equals("TEACHER")) {
            Teacher teacher = new Teacher();
            teacher.setNumber(number);
            teacher.setDepartment(department);
            teacher.setUser(user);
            userServiceImpl.saveTeacher(teacher);

        }
        return ResponseEntity.ok(createLoginResponse(user));

    }




    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userServiceImpl.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/info/{userId}")
    public ResponseEntity<UserDTO> getCurrentUserInfoById(@PathVariable("userId") Integer userId) {
        UserDTO userDTO = userServiceImpl.getCurrentUserInfoById(userId);

        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }










    @PostMapping("/avatar/{userId}")
    public ResponseEntity<String> uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile, @PathVariable("userId") Integer userId) {
        // Validate the avatar file
        if (avatarFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Avatar file is required");
        }

        User currentUser = userServiceImpl.getUserById(userId);

        try {
            // Set the avatar data in the user entity
            currentUser.setAvatar(avatarFile.getBytes());

            // Save the updated user with the new avatar
            userServiceImpl.saveUser(currentUser);

            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar");
        }
    }









}
