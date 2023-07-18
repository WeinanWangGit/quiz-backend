package com.system.quiz.contorller;

import com.system.quiz.entity.Sheet;
import com.system.quiz.service.impl.SheetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class SheetController {

    private SheetServiceImpl sheetServiceImpl;

    @Autowired
    public SheetController(SheetServiceImpl sheetServiceImpl) {
        this.sheetServiceImpl = sheetServiceImpl;
    }




    @GetMapping("/sheet/list/{studentId}")
    public List<Sheet> getSheetListByStudentId(@PathVariable int studentId) {
        return sheetServiceImpl.getSheetListByStudentId(studentId);
    }

    @GetMapping("/sheet/{testId}&{studentId}")
    public Sheet getSheetByTestIdAndStudentId(@PathVariable int testId, @PathVariable int studentId) {
        return sheetServiceImpl.getSheetByTestIdAndStudentId(testId, studentId);
    }

    @PostMapping("/sheet/question/save/{questionId}&{sheetId}")
    public void saveSheetQuestionAnswer(@PathVariable int questionId, @PathVariable int sheetId, String answer) {
        sheetServiceImpl.saveSheetQuestionAnswer(questionId, sheetId, answer);
    }

    @PostMapping("/sheet/submission")
    public void submitTestSheet(Sheet sheet) {
        sheetServiceImpl.submitTestSheet(sheet);
    }


    @GetMapping("/mark/list/{studentId}")
    public List<Sheet> getMarkListByStudentId(@PathVariable int studentId) {
        return sheetServiceImpl.getMarkListByStudentId(studentId);
    }

    @GetMapping("/mark/{testId}&{studentId}")
    public Sheet getMarkSheetByTestIdAndStudentId(@PathVariable int testId, @PathVariable int studentId) {
        return sheetServiceImpl.getMarkSheetByTestIdAndStudentId(testId, studentId);
    }


    @PostMapping("/sheet/photo/save/{sheetId}")
    public void savePhoto(@PathVariable int sheetId, @RequestParam("photo") MultipartFile photo) {
        try {
            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);
            byte[] photoData = photo.getBytes();
            sheet.setPhoto(photoData);
            sheetServiceImpl.saveSheet(sheet);
        } catch (IOException e) {
            // Handle the exception appropriately
        }
    }




}