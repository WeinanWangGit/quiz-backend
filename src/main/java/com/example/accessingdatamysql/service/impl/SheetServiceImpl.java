package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.dao.impl.SheetDAOImpl;
import com.example.accessingdatamysql.entity.Sheet;
import com.example.accessingdatamysql.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetServiceImpl implements SheetService {


    private SheetDAOImpl sheetDAOImpl;

    @Autowired
    public SheetServiceImpl(SheetDAOImpl sheetDAOImpl){
        this.sheetDAOImpl = sheetDAOImpl;
    }
    @Override
    public List<Sheet> getSheetListByStudentId(int studentId) {

        return sheetDAOImpl.getSheetListByStudentId(studentId);
    }

    @Override
    public Sheet getSheetByTestIdAndStudentId(int testId, int studentId) {

        return sheetDAOImpl.getSheetByTestIdAndStudentId(testId, studentId);
    }

    @Override
    public void saveSheetQuestionAnswer(int questionId, int sheetId, String answer) {
        sheetDAOImpl.saveSheetQuestionAnswer(questionId, sheetId, answer);
    }

    @Override
    public void submitTestSheet(Sheet sheet) {
        sheetDAOImpl.submitTestSheet(sheet);
    }

    @Override
    public List<Sheet> getMarkListByStudentId(int studentId) {
        return sheetDAOImpl.getMarkListByStudentId(studentId);
    }


    @Override
    public Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId) {

        return sheetDAOImpl.getMarkSheetByTestIdAndStudentId(testId, studentId);
    }


}
