package com.system.quiz.service;

import com.system.quiz.entity.Test;

import java.util.List;

public interface TestService {
    Test createTest(Test test);

    Test editTest(Test test);

    Test setTestSettings(Test test);

    List<Test> getTestListByTeacherId(int teacherId);

    List<Test> findAll();

    void deleteTest(int testId);
}
