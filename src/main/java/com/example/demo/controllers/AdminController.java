package com.example.demo.controllers;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.ActivityLogEntity;
import com.example.demo.entities.AdminEntity;
import com.example.demo.repositories.ActivityLogRepository;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.requests.ActivityRequest;
import com.example.demo.requests.ConsultantAuthRequest;

@RestController
@RequestMapping("IsticharaConsultation/api/admin")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes depuis ce domaine
public class AdminController {

	
	@Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private ActivityLogRepository activityLogRepository;
	
	@Autowired
	private ConsultantRepository consultantRepository;
	
	@PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody ConsultantAuthRequest loginRequest) {

        // Recherche du consultant par email
        Optional<AdminEntity> adminOptional = adminRepository.findByEmail(loginRequest.getEmail());

        if (adminOptional.isPresent()) {
        	AdminEntity admin = adminOptional.get();

            // Vérification du mot de passe
            if (admin.getMotDepasse().equals(loginRequest.getMotDePasse())) {
                // Retourner l'objet ConsultantEntity après une authentification réussie
                return ResponseEntity.ok(admin);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin non trouvé");
        }
    }
	
	@PutMapping("/{id}")
    public AdminEntity updateAdmin(@PathVariable Long id, @RequestBody AdminEntity adminUpdate) {
		// Vérifier si l'administrateur avec l'ID spécifié existe dans la base de données
        if (!adminRepository.existsById(id)) {
            return null;
        }

        // Mettre à jour les données de l'administrateur
        AdminEntity admin = adminRepository.findById(id).get();
        admin.setNomComplet(adminUpdate.getNomComplet());
        admin.setDescription(adminUpdate.getDescription());
        admin.setEmail(adminUpdate.getEmail());
        admin.setTelephone(adminUpdate.getTelephone());
        admin.setPays(adminUpdate.getPays());
        admin.setAdresse(adminUpdate.getAdresse());
        admin.setTravail(adminUpdate.getTravail());
        admin.setMotDepasse(adminUpdate.getMotDepasse()); // Assurez-vous de gérer correctement les mots de passe

        // Enregistrer les modifications dans la base de données
        return adminRepository.save(admin);
    }
	
	@PostMapping("/add-activity")
    public ActivityLogEntity addActivity(@RequestBody ActivityRequest request) {
		ActivityLogEntity activityLog = new ActivityLogEntity();
		
		activityLog.setAction(request.getAction());
		activityLog.setDescription(request.getDescription());
		activityLog.setDate(request.getDate());
		
		AdminEntity adminEntity= adminRepository.findById(request.getIdAdmin());
		activityLog.setAdmin(adminEntity);
        return activityLogRepository.save(activityLog);
    }
	
    @GetMapping("/get-activity/{id}")
    public List<ActivityLogEntity> getActivitiesByAdminId(@PathVariable("id") Long id) {
        return activityLogRepository.findByAdminId(id);
    }
    
    @GetMapping("/get-nom-admin/{idFireBase}")
    public ResponseEntity<Map<String, String>> getNomCompletByIdFireBase(@PathVariable String idFireBase) {
        String nomComplet = adminRepository.findNomCompletByIdFireBase(idFireBase);
        if (nomComplet == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, String> response = new HashMap<>();
        response.put("nomComplet", nomComplet);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/get-info-consultant/{idFireBase}")
    public ResponseEntity<Map<String, String>> getConsultantInfoByIdFireBase(@PathVariable String idFireBase) {
        String result = consultantRepository.findNomCompletAndImageByIdFireBase(idFireBase);
        if (result == null || result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Split the result string by commas
        String[] parts = result.split(",", 3);
        if (parts.length < 3) {
            return ResponseEntity.status(500).body(Map.of("error", "Invalid data format"));
        }

        String nomComplet = parts[0] + " " + parts[1];
        String imageConsultant = parts[2];
        
        java.nio.file.Path path = Paths.get(imageConsultant);
        imageConsultant=path.getFileName().toString();

        Map<String, String> response = new HashMap<>();
        response.put("nomComplet", nomComplet);
        response.put("imageConsultant", imageConsultant);

        return ResponseEntity.ok(response);
    }
}
