package com.system.quiz.controller;

import com.system.quiz.entity.Question;
import com.system.quiz.entity.QuestionDTO;
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
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody Question question) {
        QuestionDTO questionDTO = questionServiceImpl.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionDTO);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestionList() {
        List<Question> questions = questionServiceImpl.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/question/list/{teacherId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionListByTeacherId(@PathVariable int teacherId) {
        List<QuestionDTO> questionDTOs = questionServiceImpl.getQuestionListByTeacherId(teacherId);
        return ResponseEntity.ok(questionDTOs);
    }

    @PostMapping("/question/add/{testId}")
    public ResponseEntity<Void> addQuestionToTest(@PathVariable int testId, @RequestBody Question question) {
        questionServiceImpl.addQuestionToTest(testId, question);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/question/edit")
    public ResponseEntity<Void> editQuestion(@RequestBody Question question) {
        questionServiceImpl.editQuestion(question.getId(), question);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/question/delete/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int questionId) {
        questionServiceImpl.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}
