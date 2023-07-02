package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
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
