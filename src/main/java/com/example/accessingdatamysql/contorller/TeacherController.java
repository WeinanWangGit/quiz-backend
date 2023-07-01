package com.example.accessingdatamysql.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
