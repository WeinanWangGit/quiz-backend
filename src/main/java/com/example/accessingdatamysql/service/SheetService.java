package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Sheet;
import com.example.accessingdatamysql.entity.Test;

import java.util.List;

public interface SheetService {



    List<Sheet> getSheetListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);



    void saveSheetQuestionAnswer(int questionId, int sheetId, String answer);

    void submitTestSheet(Sheet sheet);



    List<Sheet> getMarkListByStudentId(int studentId);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);
}
