package com.system.quiz.dao;

import com.system.quiz.entity.Sheet;
import com.system.quiz.entity.SheetDTO;

import java.util.List;

public interface SheetDAO {
    List<SheetDTO> getSheetDTOListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);

    void saveSheetQuestionAnswer(int questionId, int sheetId, String answer);

    void submitTestSheet(Sheet sheet);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);

    List<Sheet> getMarkListByStudentId(int studentId);


    void saveSheet(Sheet sheet);

    Sheet getSheetById(int sheetId);

    List<Sheet> getSheetByTestId(int testId);
}
