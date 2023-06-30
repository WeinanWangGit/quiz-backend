package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
