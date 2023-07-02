package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dao.impl.UserDAOImpl;
import com.example.accessingdatamysql.entity.User;
import com.example.accessingdatamysql.service.UserService;
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
}
