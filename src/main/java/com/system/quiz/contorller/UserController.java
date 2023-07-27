package com.system.quiz.contorller;

import com.system.quiz.entity.User;
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
    public ResponseEntity<User> login(@RequestBody Map<String, String> requestBody) {
        String googleId = requestBody.get("sub");
        String email = requestBody.get("email");
        String name = requestBody.get("name");


        User user = userServiceImpl.findByGoogleId(googleId);

        if (user!=null) {
            return ResponseEntity.ok(user);
        } else {
            // If user does not exist, create a new user and save it
            User newUser = new User();
            newUser.setGoogleId(googleId);
            newUser.setUsername(name);
            newUser.setEmail(email);
            userServiceImpl.saveUser(newUser);

            return ResponseEntity.ok(newUser);
        }
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

    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile, Integer userId) {
        // Validate the avatar file
        if (avatarFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Avatar file is required");
        }

        // Get the current user (assuming you have a way to determine the current user)
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
