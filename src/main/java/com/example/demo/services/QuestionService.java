package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.QuestionEntity;
import com.example.demo.repositories.QuestionRepository;


@Service
public class QuestionService {
	@Autowired
    private QuestionRepository questionRepository;
	
	public ResponseEntity<List<QuestionEntity>> getAllQuestions() {
        List<QuestionEntity> questions = questionRepository.findAll();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
