package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.dao.impl.UserDAOImpl;
import com.example.accessingdatamysql.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void saveUser(User user);

    User getUserById(Integer userId);
}
