package com.system.quiz.dao;

import com.system.quiz.entity.Student;
import com.system.quiz.entity.Teacher;
import com.system.quiz.entity.User;

import java.util.ArrayList;

public interface UserDAO {
     void saveUser(User user);

     User getUserById(Integer userId);

     User getUserByUsername(String username);

    User getUserByGoogleId(String googleId);

    User findByEmail(String email);

    void saveStudent(Student student);

    void saveTeacher(Teacher teacher);

    Student getStudentByUserId(Integer userId);

    Teacher getTeacherByUserId(Integer userId);

    ArrayList<Integer> getStudentIdListByDapartAndMajor(String department, String[] majorArray);
}
