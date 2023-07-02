package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.Sheet;
import com.example.accessingdatamysql.service.impl.SheetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
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

}
