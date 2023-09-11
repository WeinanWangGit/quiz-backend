package com.system.quiz.dao;

import com.system.quiz.entity.*;

import java.util.ArrayList;
import java.util.List;

public interface SheetDAO {
    List<SheetDTO> getSheetDTOListByStudentId(int studentId);

    Sheet getSheetByTestIdAndStudentId(int testId, int studentId);

    Answer saveSheetQuestionAnswer(int questionId, int sheetId, Answer answer);

    SheetDTO submitTestSheet(Sheet sheet);

    Sheet getMarkSheetByTestIdAndStudentId(int testId, int studentId);

    List<MarkItemDTO> getMarkListByStudentId(int studentId);


    void saveSheet(Sheet sheet);

    Sheet getSheetById(int sheetId);

    List<Sheet> getSheetByTestId(int testId);

    void generateSheet(Integer testId, ArrayList<Integer> studentIds);

    List<MarkItemDTO> getMarkListByTestId(int testId);

    SheetDTO getSheetDTOById(int sheetId);

    List<MarkItemDTO> getMarkListByTeacherId(int teacherId);

    MarkDTO getMarkDTOBySheetId(int sheetId);

    void updateSheet(Sheet sheet);
}
