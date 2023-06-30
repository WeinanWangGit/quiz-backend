package com.example.accessingdatamysql.repository;

import com.example.accessingdatamysql.entity.Sheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Integer> {
}
