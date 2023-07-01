package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.Question;
import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.exception.ClassNotFoundException;
import com.example.accessingdatamysql.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private QuestionService questionService;

    @Autowired
    public StudentController(QuestionService questionService){
        this.questionService = questionService;
    }


    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }


    @GetMapping("/tests/{testId}")
    public Test getTestById(@PathVariable int testId){

        throw new ClassNotFoundException("test not found "+testId);

    }

    @GetMapping("/questions")
    public List<Question> getQuestionList(){
        return questionService.findAll();
    }


}
