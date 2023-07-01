package com.example.accessingdatamysql.service;

import com.example.accessingdatamysql.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();
}
