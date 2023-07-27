package com.system.quiz.dao;

import com.system.quiz.entity.Test;

import java.util.List;

public interface TestDAO {
    List<Test> getTestListByTeacherId(int teacherId);

    Test setTestSettings(Test test);

    Test editTest(Test test);

    Test createTest(Test test);

    List<Test> findAll();

    void deleteTest(int testId);

    Test findTestById(int testId);
}
