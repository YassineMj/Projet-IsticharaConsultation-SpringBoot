package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.requests.DemandeRequest;
import com.example.demo.services.PaiementService;


@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/IsticharaConsultation/api/paiement")
public class PaiementController {

	@Autowired
    private PaiementService paiementService;

    @GetMapping("/details-consultation/{idPlan}")
    public ResponseEntity<?> getDetailsConsultation(@PathVariable Long idPlan) {
        ResponseEntity<?> response = paiementService.getDetailsConsultation(idPlan);
        return response;
    }
    

    @PostMapping("/creer-demande")
    public ResponseEntity<Object> creerDemande(@RequestBody DemandeRequest demandeRequest) {
    	Object responseData = paiementService.creerDemande(demandeRequest);
        return ResponseEntity.ok(responseData);

    }
}