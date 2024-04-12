package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.QuestionEntity;
import com.example.demo.services.QuestionService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("IsticharaConsultation/api/question")
public class QuestionController {

	@Autowired
    private QuestionService questionService;

	
	@GetMapping("/all")
    public ResponseEntity<List<QuestionEntity>> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
