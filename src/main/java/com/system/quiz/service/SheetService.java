package com.system.quiz.service;

import com.system.quiz.entity.MarkDTO;
import com.system.quiz.entity.Sheet;
import com.system.quiz.entity.SheetDTO;

import java.util.List;

public interface SheetService {



    List<SheetDTO> getSheetDTOListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);



    void saveSheetQuestionAnswer(int questionId, int sheetId, String answer);

    void submitTestSheet(Sheet sheet);



    List<Sheet> getMarkListByStudentId(int studentId);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);

    Sheet getSheetById(int sheetId);

    void saveSheet(Sheet sheet);

    List<MarkDTO> getMarkListByTestId(int testId);
}
