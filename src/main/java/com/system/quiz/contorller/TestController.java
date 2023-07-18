package com.system.quiz.contorller;

import com.system.quiz.entity.Test;
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

    @Autowired
    public TestController(TestServiceImpl testServiceImpl) {
        this.testServiceImpl = testServiceImpl;
    }

    @PostMapping("/test/create")
    public void createTest(@RequestBody Test test) {
        testServiceImpl.createTest(test);
    }


    @PutMapping("/test/edit")
    public void editTest(@RequestBody Test test) {
        testServiceImpl.editTest(test);
    }

    @PostMapping("/test/setting")
    public void setTestSettings(@RequestBody Test test) {
        testServiceImpl.setTestSettings(test);
    }

    @GetMapping("/test/list/{teacherId}")
    public List<Test> getTestListByTeacherId(@PathVariable int teacherId) {
        return testServiceImpl.getTestListByTeacherId(teacherId);
    }

    @GetMapping("/tests")
    public List<Test> getTestList() {
        return testServiceImpl.findAll();
    }


    @DeleteMapping("/test/delete/{testId}")
    public ResponseEntity<String> deleteTest(@PathVariable int testId) {
        try {
            testServiceImpl.deleteTest(testId);
            return ResponseEntity.ok("Test deleted successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete test. Associated sheets exist.");
        }
    }


}
