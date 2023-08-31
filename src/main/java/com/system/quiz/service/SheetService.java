package com.system.quiz.service;

import com.system.quiz.entity.*;

import java.sql.Timestamp;
import java.util.List;

public interface SheetService {



    List<SheetDTO> getSheetDTOListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);



    Answer saveSheetQuestionAnswer(int questionId, int sheetId, Answer answer);

    SheetDTO submitTestSheet(Sheet sheet, Timestamp startTime);



    List<MarkItemDTO> getMarkListByStudentId(int studentId);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);

    Sheet getSheetById(int sheetId);

    void saveSheet(Sheet sheet);

    List<MarkItemDTO> getMarkListByTestId(int testId);

    SheetDTO getSheetDTOById(int sheetId);

    List<MarkItemDTO> getMarkListByTeacherIdOrTestId(int teacherId, int testId);

    MarkDTO getMarkDTOBySheetId(int sheetId);

    void postMark(Sheet sheet);

    FaceCompareDTO getPhotoCompare(Sheet sheet);
}
