package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
