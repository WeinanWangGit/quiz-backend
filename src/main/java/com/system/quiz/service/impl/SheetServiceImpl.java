package com.system.quiz.service.impl;

import com.system.quiz.dao.impl.SheetDAOImpl;
import com.system.quiz.entity.Sheet;
import com.system.quiz.entity.SheetDTO;
import com.system.quiz.service.SheetService;
import jakarta.transaction.Transactional;
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
    public List<SheetDTO> getSheetDTOListByStudentId(int studentId) {

        return sheetDAOImpl.getSheetDTOListByStudentId(studentId);
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
        //todo add face check logic here
        byte[] avatar = sheet.getStudent().getUser().getAvatar();
        byte[] photo = sheet.getPhoto();



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

    @Override
    public Sheet getSheetById(int sheetId) {
        return sheetDAOImpl.getSheetById(sheetId);
    }

    @Override
    @Transactional
    public void saveSheet(Sheet sheet) {
        sheetDAOImpl.saveSheet(sheet);
    }


}
