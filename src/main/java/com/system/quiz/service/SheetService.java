package com.system.quiz.service;

import com.system.quiz.entity.Answer;
import com.system.quiz.entity.MarkDTO;
import com.system.quiz.entity.Sheet;
import com.system.quiz.entity.SheetDTO;

import java.sql.Timestamp;
import java.util.List;

public interface SheetService {



    List<SheetDTO> getSheetDTOListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);



    Answer saveSheetQuestionAnswer(int questionId, int sheetId, Answer answer);

    SheetDTO submitTestSheet(Sheet sheet, Timestamp startTime);



    List<Sheet> getMarkListByStudentId(int studentId);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);

    Sheet getSheetById(int sheetId);

    void saveSheet(Sheet sheet);

    List<MarkDTO> getMarkListByTestId(int testId);

    SheetDTO getSheetDTOById(int sheetId);

    List<MarkDTO> getMarkListByTeacherIdOrTestId(int teacherId, int testId);
}
