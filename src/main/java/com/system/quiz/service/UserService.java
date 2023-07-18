package com.system.quiz.service;

import com.system.quiz.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void saveUser(User user);

    User getUserById(Integer userId);
}
