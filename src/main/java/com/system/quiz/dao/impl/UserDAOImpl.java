package com.system.quiz.dao.impl;

import com.system.quiz.dao.UserDAO;
import com.system.quiz.entity.Student;
import com.system.quiz.entity.Teacher;
import com.system.quiz.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public User getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public User getUserByGoogleId(String googleId) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.googleId = :googleId", User.class);
        query.setParameter("googleId", googleId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void saveStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        entityManager.persist(teacher);
    }

    @Override
    public Student getStudentByUserId(Integer userId) {
        TypedQuery<Student> query = entityManager.createQuery(
                "SELECT s FROM Student s WHERE s.user.id = :userId", Student.class);
        query.setParameter("userId", userId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Teacher getTeacherByUserId(Integer userId) {
        TypedQuery<Teacher> query = entityManager.createQuery(
                "SELECT t FROM Teacher t WHERE t.user.id = :userId", Teacher.class);
        query.setParameter("userId", userId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public ArrayList<Integer> getStudentIdListByDapartAndMajor(String department, String[] majorArray) {
        if (majorArray == null || majorArray.length == 0) {
            TypedQuery<Integer> query = entityManager.createQuery(
                    "SELECT s.id FROM Student s WHERE s.department = :department", Integer.class);
            query.setParameter("department", department);
            return new ArrayList<>(query.getResultList());
        } else {
            TypedQuery<Integer> query = entityManager.createQuery(
                    "SELECT s.id FROM Student s WHERE s.department = :department AND s.major IN :majorList", Integer.class);
            query.setParameter("department", department);
            query.setParameter("majorList", Arrays.asList(majorArray));
            return new ArrayList<>(query.getResultList());
        }
    }



}
