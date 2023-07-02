package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.User;

public interface UserDAO {
     void saveUser(User user);

     User getUserById(Integer userId);

     User getUserByUsername(String username);
}
