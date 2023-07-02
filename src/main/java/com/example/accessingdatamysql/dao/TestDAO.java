package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.Test;

import java.util.List;

public interface TestDAO {
    List<Test> getTestListByTeacherId(int teacherId);

    void setTestSettings(Test test);

    void editTest(Test test);

    void createTest(Test test);
}
