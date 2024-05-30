package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ClientEntity;
import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.ReclamationEntity;
import com.example.demo.entities.RendezVousAccepteEntity;
import com.example.demo.repositories.ReclamationRepository;
import com.example.demo.repositories.RendezVousAccepteRepository;
import com.example.demo.requests.ReclamationRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("IsticharaConsultation/api/reclamation")
public class ReclamationController {
	
	@Autowired
	ReclamationRepository reclamationRepository;
	
	@Autowired
	RendezVousAccepteRepository rendezVousAccepteRepository;
	
	 @PostMapping("add-reclamation")
	    public ResponseEntity<Map<String, String>> ajouterReclamation(@RequestBody ReclamationRequest reclamationRequest) {
	        Optional<RendezVousAccepteEntity> rendezVousAccepteEntityOpt = 
	                rendezVousAccepteRepository.findByIdReclamation(reclamationRequest.getCodeReclamation());
	        
	        if (rendezVousAccepteEntityOpt.isPresent()) {
	            RendezVousAccepteEntity rendezVousAccepteEntity = rendezVousAccepteEntityOpt.get();
	            ClientEntity client = rendezVousAccepteEntity.getRendezVous().getClient();
	            ConsultantEntity consultant = rendezVousAccepteEntity.getRendezVous().getConsultant();
	            
	            ReclamationEntity reclamation = new ReclamationEntity();
	            reclamation.setCodeReclamation(reclamationRequest.getCodeReclamation());
	            reclamation.setClient(client);
	            reclamation.setConsultant(consultant);
	            reclamation.setAvis(reclamationRequest.getAvis());
	            reclamation.setCommentaire(reclamationRequest.getCommentaire());
	            reclamation.setConsultantPasParle(reclamationRequest.isConsultantPasParle());
	            reclamation.setConsultantPasParticipe(reclamationRequest.isConsultantPasParticipe());
	            reclamation.setProblemeVoix(reclamationRequest.isProblemeVoix());
	            reclamation.setMauvaiseQualiteVideo(reclamationRequest.isMauvaiseQualiteVideo());
	            reclamation.setManqueDeConnaissances(reclamationRequest.isManqueDeConnaissances());
	            reclamation.setMauvaiseQualiteAudio(reclamationRequest.isMauvaiseQualiteAudio());
	            reclamation.setConnexionInterrompue(reclamationRequest.isConnexionInterrompue());
	            
	            reclamationRepository.save(reclamation);
	            
	            Map<String, String> response = new HashMap<>();
	            response.put("message", "Reclamation ajoutée avec succès");
	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
	        } else {
	            Map<String, String> response = new HashMap<>();
	            response.put("message", "Rendez-vous accepté non trouvé pour le code de réclamation donné");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	 
	 //
	 @GetMapping("get-all-reclamation")
	 public ResponseEntity<List<Map<String, String>>> getAllReclamations() {
		 List<Map<String, String>> reclamations = reclamationRepository.findAllReclamationsOrderedByConsultantName();
	        return ResponseEntity.ok(reclamations);
	 }
	 //
}
