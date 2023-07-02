package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Test;

import java.util.List;

public interface TestService {
    void createTest(Test test);

    void editTest(Test test);

    void setTestSettings(Test test);

    List<Test> getTestListByTeacherId(int teacherId);
}
