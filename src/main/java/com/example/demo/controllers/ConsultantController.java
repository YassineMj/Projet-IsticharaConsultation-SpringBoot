package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.reponses.ConsultantResponseDomaine;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.requests.ConsultantAuthRequest;
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
    
    @GetMapping("domaine/{idDomaine}")
    public ResponseEntity<List<ConsultantResponseDomaine>> getConsultantsByDomaine(@PathVariable String idDomaine) {
    	try {
            List<ConsultantResponseDomaine> consultants = consultantService.getConsultantsByDomaine(idDomaine);
            return ResponseEntity.ok(consultants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("profil-consultant/{idConsultant}")
    public ResponseEntity<ConsultantEntity> getConsultantById(@PathVariable String idConsultant) {

        try {
            // Call the service to get a consultant based on the provided consultant ID
        	ConsultantEntity consultant = consultantService.getConsultantById(idConsultant);

            if (consultant != null) {
                // Return a ResponseEntity with the consultant and HTTP status 200 (OK)
                return ResponseEntity.ok(consultant);
            } else {
                // If the consultant with the specified ID is not found, return HTTP status 404 (Not Found)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            // If an exception occurs during the process, return a ResponseEntity with HTTP status 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Autowired
    private ConsultantRepository consultantRepository;
    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody ConsultantAuthRequest loginRequest) {

        // Recherche du consultant par email
        Optional<ConsultantEntity> consultantOptional = consultantRepository.findByEmail(loginRequest.getEmail());

        if (consultantOptional.isPresent()) {
            ConsultantEntity consultant = consultantOptional.get();

            // Vérification du mot de passe
            if (consultant.getMotDePasse().equals(loginRequest.getMotDePasse())) {
            	java.nio.file.Path path = Paths.get(consultant.getPhotoProfile());
            	consultant.setPhotoProfile(path.getFileName().toString());
                // Retourner l'objet ConsultantEntity après une authentification réussie
                return ResponseEntity.ok(consultant);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Consultant non trouvé");
        }
    }
    
    @DeleteMapping("delete/{consultantId}")
    public ResponseEntity<String> deleteConsultant(@PathVariable Long consultantId) {
        consultantService.deleteConsultant(consultantId);
        return ResponseEntity.ok("Consultant supprimé avec succès.");
    }
}