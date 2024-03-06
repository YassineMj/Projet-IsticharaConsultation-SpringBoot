package com.example.demo.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.reponses.ConsultantResponseDomaine;
import com.example.demo.requests.ConsultantRequest;
import com.example.demo.services.ConsultantService;

@RestController
@RequestMapping("IsticharaConsultation/api/consultant")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping
    public ResponseEntity<Map<String, String>> addConsultant(@RequestBody ConsultantRequest consultantRequest) {
        try {
            String idConsultant = consultantService.saveConsultant(consultantRequest);
            Map<String, String> response = new HashMap<>();
            response.put("consultantId", idConsultant);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Erreur lors de l'ajout du consultant : " + e.getMessage()));
        }
    }
    
    @GetMapping("/{idDomaine}")
    public ResponseEntity<List<ConsultantResponseDomaine>> getConsultantsByDomaine(@PathVariable String idDomaine) {
        try {
            List<ConsultantResponseDomaine> consultants = consultantService.getConsultantsByDomaine(idDomaine);
            return ResponseEntity.ok(consultants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @DeleteMapping("/{consultantId}")
    public ResponseEntity<String> deleteConsultant(@PathVariable Long consultantId) {
        consultantService.deleteConsultant(consultantId);
        return ResponseEntity.ok("Consultant supprimé avec succès.");
    }
}