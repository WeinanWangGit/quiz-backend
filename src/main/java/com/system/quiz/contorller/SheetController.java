package com.system.quiz.contorller;

import com.system.quiz.entity.*;
import com.system.quiz.exception.ApiResponse;
import com.system.quiz.service.impl.SheetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
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
    @PreAuthorize("hasAnyAuthority('STUDENT','ADMIN')")
    public List<SheetDTO> getSheetListByStudentId(@PathVariable int studentId) {
        return sheetServiceImpl.getSheetDTOListByStudentId(studentId);
    }

    @GetMapping("/sheet/{testId}/{studentId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'ADMIN')")
    public Sheet getSheetByTestIdAndStudentId(@PathVariable int testId, @PathVariable int studentId) {
        return sheetServiceImpl.getSheetByTestIdAndStudentId(testId, studentId);
    }

    @GetMapping("/sheet/{sheetId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public SheetDTO getSheetDTOById(@PathVariable int sheetId) {
        return sheetServiceImpl.getSheetDTOById(sheetId);
    }

    @PostMapping("/sheet/answer/save/{questionId}/{sheetId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Answer>> saveSheetQuestionAnswer(@PathVariable int questionId, @PathVariable int sheetId, @RequestBody Answer answer) {
        try {
            Answer saved = sheetServiceImpl.saveSheetQuestionAnswer(questionId, sheetId, answer);
            ApiResponse<Answer> response = new ApiResponse<>(HttpStatus.OK.value(), "Answer saved successfully", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Answer> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error saving answer", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }





    @PostMapping("/sheet/photo/save/{sheetId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> savePhoto(@PathVariable int sheetId, @RequestParam("photo") MultipartFile photoFile) {
        try {
            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);
            byte[] photoData = photoFile.getBytes();
            sheet.setPhoto(photoData);
            sheetServiceImpl.saveSheet(sheet);
//            sheetServiceImpl.updateSheet(sheet);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Sheet photo save success", null);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "photo save fail", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



    @PostMapping("/sheet/submit/{sheetId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<ApiResponse<SheetDTO>> submitTestSheet(@PathVariable int sheetId, @RequestParam("startTime") Long startTimeMillis) {
        try {
            // Convert the received timestamp to java.sql.Timestamp
            Timestamp startTime = new Timestamp(startTimeMillis);

            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);

            SheetDTO sheetDTO = sheetServiceImpl.submitTestSheet(sheet, startTime);

            ApiResponse<SheetDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Test sheet submitted successfully", sheetDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<SheetDTO> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error submitting test sheet", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/sheet/start/time/{sheetId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<String> saveStartTime(@PathVariable int sheetId, @RequestParam("startTime") Long startTimeMillis) {
        try {
            // Convert the received timestamp to java.sql.Timestamp
            Timestamp startTime = new Timestamp(startTimeMillis);

            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);

            sheetServiceImpl.saveStartTime(sheet, startTime);

            return ResponseEntity.ok("Test sheet submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting test sheet");
        }
    }

    @GetMapping("/mark/compare/{sheetId}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public ResponseEntity<?> getPhotoCompare(@PathVariable int sheetId) {
        try {
            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);
            FaceCompareDTO faceCompareDTO = sheetServiceImpl.getPhotoCompare(sheet);
            return ResponseEntity.ok(faceCompareDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error getting photo comparison");
        }
    }






    @PostMapping("/mark/post/{sheetId}/{isAnonymous}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public ResponseEntity<String> postMark(@PathVariable int sheetId, @PathVariable int isAnonymous) {
        try {

            // Convert the received timestamp to java.sql.Timestamp
            Sheet sheet = sheetServiceImpl.getSheetById(sheetId);
            sheetServiceImpl.postMark(sheet, isAnonymous);

            return ResponseEntity.ok("Test sheet submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting test sheet");
        }
    }

    @GetMapping("/mark/list/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public List<MarkItemDTO> getMarkListByStudentId(@PathVariable int studentId) {
        return sheetServiceImpl.getMarkListByStudentId(studentId);
    }


    @GetMapping("/mark/list/test/{testId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public List<MarkItemDTO> getMarkListByTestId(@PathVariable int testId) {
        return sheetServiceImpl.getMarkListByTestId(testId);
    }

    @GetMapping("/mark/list/{teacherId}/{testId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public List<MarkItemDTO> getMarkListByTeacherIdOrTestId(@PathVariable int teacherId, @PathVariable int testId) {
        return sheetServiceImpl.getMarkListByTeacherIdOrTestId(teacherId, testId);
    }

    @GetMapping("/mark/{testId}/{studentId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public Sheet getMarkSheetByTestIdAndStudentId(@PathVariable int testId, @PathVariable int studentId) {
        return sheetServiceImpl.getMarkSheetByTestIdAndStudentId(testId, studentId);
    }


    @GetMapping("/mark/{sheetId}")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public MarkDTO getMarkDTOBySheetId(@PathVariable int sheetId) {
        return sheetServiceImpl.getMarkDTOBySheetId(sheetId);
    }






}
