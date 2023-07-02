package com.example.accessingdatamysql.dao;

import com.example.accessingdatamysql.entity.Sheet;
import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.entity.User;

import java.util.List;

public interface SheetDAO {
    List<Sheet> getSheetListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);

    void saveSheetQuestionAnswer(int questionId, int sheetId, String answer);

    void submitTestSheet(Sheet sheet);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);

    List<Sheet> getMarkListByStudentId(int studentId);


    void saveSheet(Sheet sheet);

    Sheet getSheetById(int sheetId);
}
