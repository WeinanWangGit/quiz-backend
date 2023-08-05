package com.system.quiz.dao;

import com.system.quiz.entity.User;

public interface UserDAO {
     void saveUser(User user);

     User getUserById(Integer userId);

     User getUserByUsername(String username);

    User getUserByGoogleId(String googleId);

    User findByEmail(String email);
}
