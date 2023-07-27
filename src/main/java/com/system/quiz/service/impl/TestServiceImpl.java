package com.system.quiz.service.impl;

import com.system.quiz.dao.impl.SheetDAOImpl;
import com.system.quiz.dao.impl.TestDAOImpl;
import com.system.quiz.entity.Sheet;
import com.system.quiz.entity.Test;
import com.system.quiz.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private TestDAOImpl testDAOImpl;
    private SheetDAOImpl sheetDAOImpl;

    @Autowired
    public TestServiceImpl(TestDAOImpl testDAOImpl, SheetDAOImpl sheetDAOImpl){
        this.testDAOImpl = testDAOImpl;
        this.sheetDAOImpl = sheetDAOImpl;
    }
    @Override
    @Transactional
    public Test createTest(Test test) {
         return testDAOImpl.createTest(test);
    }

    @Override
    @Transactional
    public Test editTest(Test test) {
         return testDAOImpl.editTest(test);
    }

    @Override
    public Test setTestSettings(Test test) {
         return testDAOImpl.setTestSettings(test);
    }

    @Override
    public List<Test> getTestListByTeacherId(int teacherId) {
        return testDAOImpl.getTestListByTeacherId(teacherId);
    }

    @Override
    public List<Test> findAll() {
        return testDAOImpl.findAll();
    }

    @Override
    @Transactional
    public void deleteTest(int testId) {
        Test test = testDAOImpl.findTestById(testId);
        if (test == null) {
            throw new EntityNotFoundException("Test not found with ID: " + testId);
        }
        List<Sheet> sheets = sheetDAOImpl.getSheetByTestId(testId);

        if (!sheets.isEmpty()&& sheets.size()!=0){
            throw new RuntimeException("Cannot delete test. Associated sheets exist.");
        }

        testDAOImpl.deleteTest(testId);
    }



}
