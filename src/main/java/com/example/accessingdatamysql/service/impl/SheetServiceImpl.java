package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.repository.SheetRepository;
import com.example.accessingdatamysql.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetServiceImpl implements SheetService {


    private SheetRepository sheetRepository;

    @Autowired
    public SheetServiceImpl(SheetRepository sheetRepository){
        this.sheetRepository = sheetRepository;
    }
    @Override
    public List<Test> getSheetListForStudent(int studentId) {
        return null;
    }

    @Override
    public Test getSheetByIdForStudent(int testId, int studentId) {
        return null;
    }

    @Override
    public void saveSheetQuestion(int questionId, int sheetId) {

    }

    @Override
    public void submitTestSheet() {

    }

    @Override
    public List<Test> getMarkListForStudent(int studentId) {
        return null;
    }

    @Override
    public Test getMarkSheetByTestIdForStudent(int testId, int studentId) {
        return null;
    }
}
