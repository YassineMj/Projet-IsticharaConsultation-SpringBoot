package com.example.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.ConsultantRequest;
import com.example.demo.services.ConsultantService;

@RestController
@RequestMapping("IsticharaConsultation/api/consultant")
@CrossOrigin("*")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping
    public ResponseEntity<String> addConsultant(@RequestBody ConsultantRequest consultantRequest) {
        try {
            String consultantId = consultantService.saveConsultant(consultantRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(consultantId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du consultant : " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{consultantId}")
    public ResponseEntity<String> deleteConsultant(@PathVariable Long consultantId) {
        consultantService.deleteConsultant(consultantId);
        return ResponseEntity.ok("Consultant supprimé avec succès.");
    }
}