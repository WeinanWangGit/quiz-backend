package com.system.quiz.service.impl;

import com.system.quiz.config.JwtAuthenticationFilter;
import com.system.quiz.dao.impl.UserDAOImpl;
import com.system.quiz.entity.Student;
import com.system.quiz.entity.Teacher;
import com.system.quiz.entity.User;
import com.system.quiz.entity.UserDTO;
import com.system.quiz.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

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

    @Override
    public User findByEmail(String email) {
        User user = userDAOImpl.findByEmail(email);
        if(user == null){
            logger.info("User not fond");
        }
        return user;
    }

    @Override
    @Transactional
    public void saveTeacher(Teacher teacher) {
        userDAOImpl.saveTeacher(teacher);
    }

    @Override
    @Transactional
    public void saveStudent(Student student) {
       userDAOImpl.saveStudent(student);
    }

    @Override
    public UserDTO getCurrentUserInfoById(Integer userId) {
        UserDTO userDTO = new UserDTO();
        String number = "";
        String major = "";
        String department = "";

        User user = userDAOImpl.getUserById(userId);
        if(user!=null){
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            String role = user.getRole().name();
            userDTO.setRole(role);
            userDTO.setAvatar(user.getAvatar());
            if(role.equals("STUDENT")){
                Student student = userDAOImpl.getStudentByUserId(userId);
                 number = student.getNumber();
                 major = student.getMajor();
                 department = student.getDepartment();
            }else if(role.equals("TEACHER")){
                Teacher teacher = userDAOImpl.getTeacherByUserId(userId);
                number = teacher.getNumber();
                department = teacher.getDepartment();
            }
            userDTO.setNumber(number);
            userDTO.setDepartment(department);
            userDTO.setMajor(major);
        }

        return userDTO;

    }
}
