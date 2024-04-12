package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

}
