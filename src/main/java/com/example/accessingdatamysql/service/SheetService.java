package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Test;

import java.util.List;

public interface SheetService {
    List<Test> getSheetListForStudent(int studentId);

    Test getSheetByIdForStudent(int testId, int studentId);

    void saveSheetQuestion(int questionId, int sheetId);

    void submitTestSheet();

    List<Test> getMarkListForStudent(int studentId);

    Test getMarkSheetByTestIdForStudent(int testId, int studentId);
}
