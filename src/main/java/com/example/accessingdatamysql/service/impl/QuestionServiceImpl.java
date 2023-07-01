package com.example.accessingdatamysql.service.impl;

import com.example.accessingdatamysql.entity.Question;
import com.example.accessingdatamysql.repository.QuestionRepository;
import com.example.accessingdatamysql.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }


    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }
}
