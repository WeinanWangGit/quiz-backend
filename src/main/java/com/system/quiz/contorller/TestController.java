package com.system.quiz.contorller;

import com.system.quiz.entity.Test;
import com.system.quiz.exception.ApiResponse;
import com.system.quiz.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TestController {

    private TestServiceImpl testServiceImpl;

    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

    @Autowired
    public TestController(TestServiceImpl testServiceImpl) {
        this.testServiceImpl = testServiceImpl;
    }

    @PostMapping("/test/create")
    public ResponseEntity<ApiResponse<Test>> createTest(@RequestBody Test test) {
        Test createdTest = testServiceImpl.createTest(test);
        ApiResponse<Test> response = new ApiResponse<>(HttpStatus.OK.value(), "Test created successfully.", createdTest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/test/edit")
    public ResponseEntity<ApiResponse<Test>> editTest(@RequestBody Test test) {
        Test editedTest = testServiceImpl.editTest(test);
        ApiResponse<Test> response = new ApiResponse<>(HttpStatus.OK.value(), "Test edited successfully.", editedTest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/test/setting")
    public ResponseEntity<ApiResponse<Test>> setTestSettings(@RequestBody Test test) {
        Test updatedTest = testServiceImpl.setTestSettings(test);
        ApiResponse<Test> response = new ApiResponse<>(HttpStatus.OK.value(), "Test settings updated successfully.", updatedTest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/list/{teacherId}")
    public ResponseEntity<ApiResponse<List<Test>>> getTestListByTeacherId(@PathVariable int teacherId) {
        List<Test> testList = testServiceImpl.getTestListByTeacherId(teacherId);
        ApiResponse<List<Test>> response = new ApiResponse<>(HttpStatus.OK.value(), "Test list fetched successfully.", testList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tests")
    public ResponseEntity<ApiResponse<List<Test>>> getTestList() {
        List<Test> testList = testServiceImpl.findAll();
        ApiResponse<List<Test>> response = new ApiResponse<>(HttpStatus.OK.value(), "All tests fetched successfully.", testList);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/test/delete/{testId}")
    public ResponseEntity<ApiResponse<String>> deleteTest(@PathVariable int testId) {
        try {
            testServiceImpl.deleteTest(testId);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Test deleted successfully.", null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Cannot delete test. Associated sheets exist.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
