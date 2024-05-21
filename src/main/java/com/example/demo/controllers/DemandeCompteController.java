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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DemandeCompteEntity;
import com.example.demo.repositories.DemandeCompteRepository;
import com.example.demo.requests.DemandeComptetRequest;
import com.example.demo.services.DemandeCompteService;

@RestController
@RequestMapping("IsticharaConsultation/api/demande-Compte")
@CrossOrigin(origins = "http://localhost:4200")
public class DemandeCompteController {

	@Autowired
	DemandeCompteRepository demandeCompteRepository;
	
	@Autowired
	DemandeCompteService demandeCompteService;
	
    @PostMapping("add-demande-compte")
    public ResponseEntity<Map<String, String>> addConsultant(@RequestBody DemandeComptetRequest demandeComptetRequest) {
        try {
        	 if (demandeCompteRepository.existsByEmail(demandeComptetRequest.getEmail())==true) {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "L'e-mail existe déjà."));
             }
        	 
            String idConsultant = demandeCompteService.saveDemendeCompte(demandeComptetRequest);
            Map<String, String> response = new HashMap<>();
            response.put("consultantId", idConsultant);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Erreur lors de l'ajout du consultant : " + e.getMessage()));
        }
    }
    
    @GetMapping("get-all-demande-compte")
    public List<DemandeCompteEntity> getAllDemandeCompte(){
    	return demandeCompteService.getAllDemande();
    }
    
    @GetMapping("get-demande-compte/{idDemande}")
    public DemandeCompteEntity getDemandeCompteById(@PathVariable String idDemande ){
    	return demandeCompteService.getDemandeCompteById(idDemande);
    }
    
    @PostMapping("active-compte/{idDemande}/{idFireBase}")
    public ResponseEntity<Map<String, String>> activeCompte(@PathVariable String idDemande , @PathVariable String idFireBase){
    	return demandeCompteService.acitiveCompte(idDemande,idFireBase);
    }
}
