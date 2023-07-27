package com.system.quiz.service.impl;

import com.system.quiz.dao.impl.UserDAOImpl;
import com.system.quiz.entity.User;
import com.system.quiz.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDAOImpl userDAOImpl;

    @Autowired
    public UserServiceImpl(UserDAOImpl userDAOImpl){
        this.userDAOImpl = userDAOImpl;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
         userDAOImpl.saveUser(user);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDAOImpl.getUserById(userId);
    }

    @Override
    public User findByGoogleId(String googleId) {
        return userDAOImpl.getUserByGoogleId(googleId);
    }
}
