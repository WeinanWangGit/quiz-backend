package com.system.quiz.contorller;

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
    public List<Question> getQuestionList() {
        return questionServiceImpl.findAll();
    }


    @GetMapping("/question/list/{teacherId}")
    public List<QuestionDTO> getQuestionListByTeacherId(@PathVariable int teacherId) {
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
