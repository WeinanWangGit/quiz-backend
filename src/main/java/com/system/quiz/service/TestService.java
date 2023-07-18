package com.system.quiz.service;

import com.system.quiz.entity.Test;

import java.util.List;

public interface TestService {
    void createTest(Test test);

    void editTest(Test test);

    void setTestSettings(Test test);

    List<Test> getTestListByTeacherId(int teacherId);

    List<Test> findAll();

    void deleteTest(int testId);
}
