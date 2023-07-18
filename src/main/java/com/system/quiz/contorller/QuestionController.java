package com.system.quiz.contorller;

import com.system.quiz.entity.Question;
import com.system.quiz.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    @PostMapping("/question/create")
    public ResponseEntity<String> createQuestion(@RequestBody Question question) {
        questionServiceImpl.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body("Question created successfully");
    }

    @GetMapping("/questions")
    public List<Question> getQuestionList() {
        return questionServiceImpl.findAll();
    }


    @GetMapping("/question/list/{teacherId}")
    public List<Question> getQuestionListByTeacherId(@PathVariable int teacherId) {
        System.out.println(teacherId);
        return questionServiceImpl.getQuestionListByTeacherId(teacherId);
    }

    @PostMapping("/question/add/{testId}")
    public void addQuestionToTest(@PathVariable int testId, @RequestBody Question question) {
        questionServiceImpl.addQuestionToTest(testId, question);
    }

    @PutMapping("/question/edit")
    public void editQuestion(@RequestBody Question question) {
        questionServiceImpl.editQuestion(question.getId(), question);
    }

    @DeleteMapping("/question/delete/{questionId}")
    public void deleteQuestion(@PathVariable int questionId) {
        questionServiceImpl.deleteQuestion(questionId);
    }


}