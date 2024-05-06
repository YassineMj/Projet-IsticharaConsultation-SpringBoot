package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.reponses.StatistiqueResponse;
import com.example.demo.repositories.CategorieRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.ConsultationRepository;
import com.example.demo.repositories.DemandeCompteRepository;
import com.example.demo.repositories.DomaineRepository;
import com.example.demo.repositories.RendezVousRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("IsticharaConsultation/api/statistique")
public class StatistiqueController {

	@Autowired
	DomaineRepository domaineRepository;
	
	@Autowired
	CategorieRepository categorieRepository;
	
	@Autowired
	ConsultantRepository consultantRepository;
	
	@Autowired
	DemandeCompteRepository demandeCompteRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	RendezVousRepository rendezVousRepository;
	
	@Autowired
	ConsultationRepository consultationRepository;
	
	@GetMapping("/count-all")
    public StatistiqueResponse countAll() {
        StatistiqueResponse response = new StatistiqueResponse();
        response.setCountDomaine(domaineRepository.count());
        response.setCountCategorie(categorieRepository.count());
        response.setCountConsultant(consultantRepository.count());
        response.setCountDemandeCompte(demandeCompteRepository.count());
        response.setCountClient(clientRepository.count());
        response.setCountConsultations(consultationRepository.count());
        response.setCountRendezVous(rendezVousRepository.count());
        
        return response;
    }
	
	@GetMapping("/count-categorie-consultant-par-domaine")
	public ResponseEntity<List<Map<String, Object>>> countCategorieConsultantParDomaine() {
	    List<Map<String, Object>> resultat = new ArrayList();
	    List<DomaineEntity> domaines = domaineRepository.findAll();

	    for (DomaineEntity domaine : domaines) {
	        long countCategories = categorieRepository.countByDomaine(domaine);
	        long countConsultants = consultantRepository.countByDomaine(domaine);

	        Map<String, Object> domaineStat = new HashMap<>();
	        domaineStat.put("nomDomaine", domaine.getNomDomaine());
	        domaineStat.put("countCategories", countCategories);
	        domaineStat.put("countConsultants", countConsultants);

	        resultat.add(domaineStat);
	    }

	    return ResponseEntity.ok(resultat);
	}
	
}
