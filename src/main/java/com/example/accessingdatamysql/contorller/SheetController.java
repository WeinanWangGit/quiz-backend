package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.service.QuestionService;
import com.example.accessingdatamysql.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SheetController {

    private SheetService sheetService;

    @Autowired
    public SheetController(SheetService sheetService) {
        this.sheetService = sheetService;
    }




    @GetMapping("/sheet/list/{studentId}")
    public List<Test> getSheetListForStudent(@PathVariable int studentId) {
        return sheetService.getSheetListForStudent(studentId);
    }

    @GetMapping("/sheet/{testId}&{studentId}")
    public Test getSheetByIdForStudent(@PathVariable int testId, @PathVariable int studentId) {
        return sheetService.getSheetByIdForStudent(testId, studentId);
    }

    @PostMapping("/sheet/question/save/{questionId}&{sheetId}")
    public void saveSheetQuestion(@PathVariable int questionId, @PathVariable int sheetId) {
        sheetService.saveSheetQuestion(questionId, sheetId);
    }

    @PostMapping("/sheet/submission")
    public void submitTestSheet() {
        sheetService.submitTestSheet();
    }


    @GetMapping("/mark/list/{studentId}")
    public List<Test> getMarkListForStudent(@PathVariable int studentId) {
        return sheetService.getMarkListForStudent(studentId);
    }

    @GetMapping("/mark/{testId}&{studentId}")
    public Test getMarkSheetByTestIdForStudent(@PathVariable int testId, @PathVariable int studentId) {
        return sheetService.getMarkSheetByTestIdForStudent(testId, studentId);
    }

}
