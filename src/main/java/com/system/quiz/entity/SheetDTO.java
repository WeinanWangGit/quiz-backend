package com.system.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SheetDTO {

    private Integer id;

    private Student studentId;

    private TestDTO test;

}
