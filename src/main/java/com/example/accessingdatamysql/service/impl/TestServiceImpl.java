package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dao.impl.SheetDAOImpl;
import com.example.accessingdatamysql.dao.impl.TestDAOImpl;
import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private TestDAOImpl testDAOImpl;

    @Autowired
    public TestServiceImpl(TestDAOImpl testDAOImpl){
        this.testDAOImpl = testDAOImpl;
    }
    @Override
    public void createTest(Test test) {
         testDAOImpl.createTest(test);
    }

    @Override
    public void editTest(Test test) {
         testDAOImpl.editTest(test);
    }

    @Override
    public void setTestSettings(Test test) {
         testDAOImpl.setTestSettings(test);
    }

    @Override
    public List<Test> getTestListByTeacherId(int teacherId) {
        return testDAOImpl.getTestListByTeacherId(teacherId);
    }
}
