package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.entities.PlanConsultationEntity;
import com.example.demo.reponses.StatistiqueResponse;
import com.example.demo.repositories.CategorieRepository;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.ConsultationRepository;
import com.example.demo.repositories.DemandeCompteRepository;
import com.example.demo.repositories.DomaineRepository;
import com.example.demo.repositories.PlanConsultationRepository;
import com.example.demo.repositories.RendezVousRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://yassinemj.github.io"})

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
	
	@Autowired
	PlanConsultationRepository planConsultationEntity;
	
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
	
	@GetMapping("/plan-consultant/{idConsultant}")
	public ResponseEntity<Map<String, Object>> countConsultationsByConsultantId(@PathVariable long idConsultant) {
	    long count = planConsultationEntity.countPlanByConsultantId(idConsultant);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("count", count);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/count-rv-domaine/{idDomaine}")
    public ResponseEntity<Map<String, Long>> countRvByDomaine(@PathVariable long idDomaine) {
        long total = domaineRepository.countTotalByDomainId(idDomaine);
        long accepte = domaineRepository.countAccepteByDomainId(idDomaine);
        long refuse = domaineRepository.countRefuseByDomainId(idDomaine);
        long enAttente = domaineRepository.countEnAttenteByDomainId(idDomaine);

        Map<String, Long> counts = new HashMap<>();
        //counts.put("TOTAL", total);
        counts.put("ACCEPTE", accepte);
        counts.put("REFUSE", refuse);
        counts.put("ENATTENTE", enAttente);

        return ResponseEntity.ok(counts);
    }
	
	@GetMapping("/rv-consultant/{consultantId}")
    public ResponseEntity<Map<String, Long>> countRvByConsultant(@PathVariable long consultantId) {
        long total = consultantRepository.countTotalRvByConsultantId(consultantId);
        long accepte = consultantRepository.countAccepteRvByConsultantId(consultantId);
        long refuse = consultantRepository.countRefuseRvByConsultantId(consultantId);
        long enAttente = consultantRepository.countEnAttenteRvByConsultantId(consultantId);

        Map<String, Long> counts = new HashMap<>();
        counts.put("TOTAL", total);
        counts.put("ACCEPTE", accepte);
        counts.put("REFUSE", refuse);
        counts.put("ENATTENTE", enAttente);

        return ResponseEntity.ok(counts);
    }
	
	@GetMapping("/consultation-plan-domaine")
	public ResponseEntity<List<Map<String, Object>>> countConsultationsPlansByDomaine() {
	    List<Map<String, Object>> result = new ArrayList<>();

	    List<Object[]> consultationsByDomaine = domaineRepository.countConsultationsByDomaine();
	    List<Object[]> plansByDomaine = domaineRepository.countPlansByDomaine();

	    Map<String, Long> consultationsMap = new HashMap<>();
	    Map<String, Long> plansMap = new HashMap<>();

	    for (Object[] consultationData : consultationsByDomaine) {
	        String nomDomaine = (String) consultationData[0];
	        Long countConsultations = (Long) consultationData[1];
	        consultationsMap.put(nomDomaine, countConsultations);
	    }

	    for (Object[] planData : plansByDomaine) {
	        String nomDomaine = (String) planData[0];
	        Long countPlans = (Long) planData[1];
	        plansMap.put(nomDomaine, countPlans);
	    }

	    for (String nomDomaine : consultationsMap.keySet()) {
	        Map<String, Object> domainStats = new HashMap<>();
	        domainStats.put("nomDomaine", nomDomaine);

	        Long countConsultations = consultationsMap.getOrDefault(nomDomaine, 0L);
	        Long countPlans = plansMap.getOrDefault(nomDomaine, 0L);

	        domainStats.put("consultations", countConsultations);
	        domainStats.put("plans", countPlans);

	        result.add(domainStats);
	    }

	    return ResponseEntity.ok(result);
	}

}


