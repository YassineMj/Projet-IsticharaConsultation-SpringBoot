package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ActivityConsultantEntity;
import com.example.demo.entities.ActivityLogEntity;
import com.example.demo.entities.AdminEntity;
import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.DemandeCompteEntity;
import com.example.demo.entities.DomaineEntity;
import com.example.demo.entities.PlanConsultationEntity;
import com.example.demo.entities.RendezVousEntity;
import com.example.demo.reponses.ConsultantResponseDomaine;
import com.example.demo.repositories.ActivityConsultantRepository;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.PlanConsultationRepository;
import com.example.demo.repositories.ReclamationRepository;
import com.example.demo.repositories.RendezVousRepository;
import com.example.demo.requests.ActivityConsultantRequest;
import com.example.demo.requests.ActivityRequest;
import com.example.demo.requests.ConsultantAuthRequest;
import com.example.demo.requests.DemandeComptetRequest;
import com.example.demo.services.ConsultantService;

@RestController
@RequestMapping("IsticharaConsultation/api/consultant")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;
    
    @Autowired
    private ConsultantRepository consultantRepository;
/*
    @PostMapping
    public ResponseEntity<Map<String, String>> addConsultant(@RequestBody ConsultantRequest consultantRequest) {
        try {
        	 if (consultantRepository.existsByEmail(consultantRequest.getEmail())==true) {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "L'e-mail existe déjà."));
             }
        	 
            String idConsultant = consultantService.saveConsultant(consultantRequest);
            Map<String, String> response = new HashMap<>();
            response.put("consultantId", idConsultant);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Erreur lors de l'ajout du consultant : " + e.getMessage()));
        }
    }
*/
    
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
    
    //*
    @GetMapping("get-all-consultant")
    public List<ConsultantEntity> getAllConsultant(){
    	return consultantService.getAllConsultant();
    }


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
    
    @PutMapping("update-consultant")
    public ResponseEntity<ConsultantEntity> updateConsultantById(@RequestBody ConsultantEntity consultant) {
        ConsultantEntity updatedConsultant = consultantService.updateAllAttributesById(consultant);
        java.nio.file.Path path = Paths.get(updatedConsultant.getPhotoProfile());
        updatedConsultant.setPhotoProfile(path.getFileName().toString());

        if (updatedConsultant != null) {
            return ResponseEntity.ok(updatedConsultant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("delete/{consultantId}")
    public ResponseEntity<String> deleteConsultant(@PathVariable Long consultantId) {
        consultantService.deleteConsultant(consultantId);
        return ResponseEntity.ok("Consultant supprimé avec succès.");
    }
    
    @Autowired
    private RendezVousRepository rendezVousRepository;

    @GetMapping("/stats/{idConsultant}")
    public Map<String, Long> getRendezVousStatsByIdConsultant(@PathVariable String idConsultant) {
        Long total = rendezVousRepository.countTotalRendezVousByIdConsultant(idConsultant);
        Long accepted = rendezVousRepository.countAcceptedRendezVousByIdConsultant(idConsultant);
        Long refused = rendezVousRepository.countRefusedRendezVousByIdConsultant(idConsultant);
        
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("accepted", accepted);
        stats.put("refused", refused);
        
        return stats;
    }
    
    @Autowired
    private PlanConsultationRepository planConsultationRepository;

    @GetMapping("/countPlanByMonth/{idConsultant}")
    public Map<Integer, Long> getCountPlansByMonthForCurrentYear(@PathVariable String idConsultant) {
        int currentYear = Year.now().getValue();
        List<Map<String, Object>> result = planConsultationRepository.countPlansByMonthForYear(idConsultant, currentYear);
        Map<Integer, Long> countsByMonth = new HashMap<>();
        for (Map<String, Object> entry : result) {
            countsByMonth.put((Integer) entry.get("month"), (Long) entry.get("count"));
        }
        return countsByMonth;
    }
    
    @GetMapping("/get-plan-status/{idConsultant}")
    public List<Map<String, Object>> getPlansWithStatus(@PathVariable String idConsultant) {
        LocalDate currentDate = LocalDate.now();
        List<Map<String, Object>> plans = planConsultationRepository.findPlansWithRendezVousByConsultant(idConsultant);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> planData : plans) {
            Long planId = ((Number) planData.get("planId")).longValue();
            LocalDate dateJourDebut = LocalDate.parse(planData.get("dateJourDebut").toString());
            LocalDate dateJourFin = LocalDate.parse(planData.get("dateJourFin").toString());
            LocalTime heureDebut = LocalTime.parse(planData.get("heureDebut").toString());
            LocalTime heureFin = LocalTime.parse(planData.get("heureFin").toString());
            String jourDebut = planData.get("jourDebut").toString();
            String jourFin = planData.get("jourFin").toString();
            Boolean accepte = planData.get("accepte") != null ? (Boolean) planData.get("accepte") : null;
            Boolean refuse = planData.get("refuse") != null ? (Boolean) planData.get("refuse") : null;

            String status;
            if (dateJourDebut.isBefore(currentDate)) {
                if (accepte == null && refuse == null) {
                    status = "terminerSansReservation";
                } else if (Boolean.TRUE.equals(accepte) && refuse == null) {
                    status = "terminerAvecReservation";
                } else {
                    status = "paiement annulé";
                }
            } else {
                if (accepte == null && refuse == null) {
                    status = "enCours";
                } else if (Boolean.TRUE.equals(accepte) && refuse == null) {
                    status = "reserver";
                } else {
                    status = "indetermine";
                }
            }

            Map<String, Object> planMap = new HashMap<>();
            planMap.put("idPlan", planId);
            planMap.put("dateJourDebut", dateJourDebut);
            planMap.put("dateJourFin", dateJourFin);
            planMap.put("heureDebut", heureDebut);
            planMap.put("heureFin", heureFin);
            planMap.put("jourDebut", jourDebut);
            planMap.put("jourFin", jourFin);
            planMap.put("status", status);
            result.add(planMap);
        }

        return result;
    }
    
    @Autowired
    private ReclamationRepository reclamationRepository;
    
    @GetMapping("/avis/{idConsultant}")
    public Map<String, Long> getFavAndDefavCount(@PathVariable String idConsultant) {
        return reclamationRepository.countFavAndDefavByConsultantId(idConsultant);
    }
    
    @Autowired
    ActivityConsultantRepository activityConsultantRepository;
    
    @PostMapping("/add-activity")
    public ActivityConsultantEntity addActivity(@RequestBody ActivityConsultantRequest request) {
		ActivityConsultantEntity activityLog = new ActivityConsultantEntity();
		
		activityLog.setAction(request.getAction());
		activityLog.setDescription(request.getDescription());
		activityLog.setDate(request.getDate());
		
		Optional<ConsultantEntity> consultantEntity= consultantRepository.findById(request.getIdConsultant());
		activityLog.setConsultant(consultantEntity.get());
        return activityConsultantRepository.save(activityLog);
    }
	
    @GetMapping("/get-activity/{id}")
    public List<ActivityConsultantEntity> getActivitiesByConsultantId(@PathVariable("id") Long id) {
        return activityConsultantRepository.findByConsultantId(id);
    }
}