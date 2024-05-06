package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	//*
	@PostMapping("/add-question")
    public ResponseEntity<QuestionEntity> addQuestion(@RequestBody QuestionEntity question) {
        QuestionEntity savedQuestion = questionService.addQuestion(question);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }
	
	//*
    @GetMapping("/get-all-questions")
    public ResponseEntity<List<QuestionEntity>> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    
    //*
    @GetMapping("/get-question/{idQuestion}")
    public ResponseEntity<QuestionEntity> getQuestionById(@PathVariable Long idQuestion) {
        Optional<QuestionEntity> question = questionService.getQuestionById(idQuestion);
        return question.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //*
    @PutMapping("/update-question/{idQuestion}")
    public ResponseEntity<QuestionEntity> updateQuestion(@PathVariable Long idQuestion,
                                                        @RequestBody QuestionEntity updatedQuestion) {
        QuestionEntity question = questionService.updateQuestion(idQuestion, updatedQuestion);
        return question != null ?
                new ResponseEntity<>(question, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //*
    @DeleteMapping("/delete-question/{idQuestion}")
    public ResponseEntity<Map<String,Object>> deleteQuestion(@PathVariable Long idQuestion) {
        Map<String, Object> response = new HashMap();
        try {
            questionService.deleteQuestion(idQuestion);
            response.put("message", "Question supprimé avec succès");
            response.put("ok", true);
        } catch (Exception e) {
            response.put("message", "Erreur lors de la suppression du Question");
            response.put("ok", false);
        }
        return ResponseEntity.ok(response);    
    }
	
	@GetMapping("/all")
    public ResponseEntity<List<QuestionEntity>> getAllQuestion() {
        return questionService.getAllQuestions();
    }
}
