package com.example.accessingdatamysql.contorller;

import com.example.accessingdatamysql.entity.Question;
import com.example.accessingdatamysql.entity.Test;
import com.example.accessingdatamysql.service.QuestionService;
import com.example.accessingdatamysql.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionController {

    private QuestionServiceImpl questionServiceImpl;

    @Autowired
    public QuestionController(QuestionServiceImpl questionServiceImpl) {
        this.questionServiceImpl = questionServiceImpl;
    }


    @GetMapping("/questions")
    public List<Question> getQuestionList() {
        return questionServiceImpl.findAll();
    }


    @GetMapping("/question/list/{teacherId}")
    public List<Question> getQuestionListByTeacherId(@PathVariable int teacherId) {
        return questionServiceImpl.getQuestionListByTeacherId(teacherId);
    }

    @PostMapping("/question/add/{testId}")
    public void addQuestionToTest(@PathVariable int testId, @RequestBody Question question) {
        questionServiceImpl.addQuestionToTest(testId, question);
    }

    @PostMapping("/question/edit/{questionId}")
    public void editQuestion(@PathVariable int questionId, @RequestBody Question question) {
        questionServiceImpl.editQuestion(questionId, question);
    }



}
