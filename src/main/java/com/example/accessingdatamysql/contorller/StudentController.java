package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.exception.ClassNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }


    @GetMapping("/tests/{testId}")
    public Test getTestById(@PathVariable int testId){

        throw new ClassNotFoundException("test not found "+testId);

    }


}
