package com.example.demo.services;

import java.util.List;
import java.util.Optional;

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
	
    public QuestionEntity addQuestion(QuestionEntity question) {
        return questionRepository.save(question);
    }
        
	public ResponseEntity<List<QuestionEntity>> getAllQuestions() {
        List<QuestionEntity> questions = questionRepository.findAll();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

	public Optional<QuestionEntity> getQuestionById(Long idQuestion) {
		return questionRepository.findById(idQuestion);
	}

	public QuestionEntity updateQuestion(Long idQuestion, QuestionEntity updatedQuestion) {
		Optional<QuestionEntity> existingQuestion = questionRepository.findById(idQuestion);
        if (existingQuestion.isPresent()) {
            updatedQuestion.setId(idQuestion);
            return questionRepository.save(updatedQuestion);
        }
        return null; // or throw an exception
	}

	public void deleteQuestion(Long idQuestion) {
		questionRepository.deleteById(idQuestion);		
	}
}
