package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.PlanConsultationEntity;
import com.example.demo.entities.RendezVousEntity;
import com.example.demo.repositories.PlanConsultationRepository;
import com.example.demo.repositories.RendezVousRepository;
import com.example.demo.requests.PlanConsultationRequest;
import com.example.demo.services.PlanConsultationService;

import jakarta.persistence.EntityNotFoundException;


@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("IsticharaConsultation/api/plan")
public class PlanConsultationController {

    @Autowired
    private PlanConsultationService planConsultationService;
    
    @Autowired
    private RendezVousRepository  rendezVousRepository;

    @PostMapping("ajouter-plan")
    public ResponseEntity<?> ajouterPlanConsultation(@RequestBody PlanConsultationRequest planRequest) {
        try {
            PlanConsultationEntity planConsultation = planConsultationService.ajouterPlanConsultation(planRequest);
            return new ResponseEntity<>(planConsultation, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("delete-plan/{id}")
    public ResponseEntity<String> supprimerPlanConsultation(@PathVariable Long id) {
        planConsultationService.supprimerPlanConsultation(id);
        return ResponseEntity.ok("PlanConsultation supprimée avec succès.");
    }
    
    @GetMapping("/check-plan/{idPlan}")
    public Map<String, Boolean> verifierPlanExistence(@PathVariable Long idPlan) {
        Map<String, Boolean> result = new HashMap();
        // Vérifier si au moins une ligne contient accepte à vrai pour cet ID de plan
        boolean accepteTrueFound = rendezVousRepository.existsByPlanIdAndAccepte(idPlan, true);
        result.put("accepteTrueFound", !accepteTrueFound);
        return result;
    }
}
