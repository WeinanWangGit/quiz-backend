package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
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


    @PostMapping("/test/edit")
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

}
