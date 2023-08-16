package com.system.quiz.contorller;

import com.system.quiz.entity.MarkDTO;
import com.system.quiz.entity.Sheet;
import com.system.quiz.entity.SheetDTO;
import com.system.quiz.exception.ApiResponse;
import com.system.quiz.service.impl.SheetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
public class SheetController {

    private SheetServiceImpl sheetServiceImpl;

    @Autowired
    public SheetController(SheetServiceImpl sheetServiceImpl) {
        this.sheetServiceImpl = sheetServiceImpl;
    }


    /**
     * get test list of sheet list
     * @param studentId
     * @return
     */
    @GetMapping("/sheet/list/{studentId}")
    public List<SheetDTO> getSheetListByStudentId(@PathVariable int studentId) {
        return sheetServiceImpl.getSheetDTOListByStudentId(studentId);
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


    @GetMapping("/mark/list/student/{studentId}")
    public List<Sheet> getMarkListByStudentId(@PathVariable int studentId) {
        return sheetServiceImpl.getMarkListByStudentId(studentId);
    }


    @GetMapping("/mark/list/test/{testId}")
    public List<MarkDTO> getMarkListByTestId(@PathVariable int testId) {
        return sheetServiceImpl.getMarkListByTestId(testId);
    }

    @GetMapping("/mark/{testId}&{studentId}")
    public Sheet getMarkSheetByTestIdAndStudentId(@PathVariable int testId, @PathVariable int studentId) {
        return sheetServiceImpl.getMarkSheetByTestIdAndStudentId(testId, studentId);
    }


    @PostMapping("/sheet/photo/save/{sheetId}")
    public ResponseEntity<ApiResponse<String>> savePhoto(@PathVariable int sheetId, @RequestParam("photo") MultipartFile photo) {
        try {
            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);
            byte[] photoData = photo.getBytes();
            sheet.setPhoto(photoData);
            sheetServiceImpl.saveSheet(sheet);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Sheet photo save success", null);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "photo save fail", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



}
