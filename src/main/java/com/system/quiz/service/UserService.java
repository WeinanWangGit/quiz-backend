package com.system.quiz.service;

import com.system.quiz.entity.Student;
import com.system.quiz.entity.Teacher;
import com.system.quiz.entity.User;
import com.system.quiz.entity.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void saveUser(User user);

    User getUserById(Integer userId);

    User findByGoogleId(String googleId);

    User findByEmail(String email);

    void saveTeacher(Teacher teacher);

    void saveStudent(Student student);

    UserDTO getCurrentUserInfoById(Integer userId);

    List<User> findAll();
}
