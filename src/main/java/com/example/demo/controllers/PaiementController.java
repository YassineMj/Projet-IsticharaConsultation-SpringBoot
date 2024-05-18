package com.example.demo.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.RendezVousAccepteEntity;
import com.example.demo.entities.RendezVousEntity;
import com.example.demo.methodeStatic.IdGenerator;
import com.example.demo.requests.DemandeRequest;
import com.example.demo.requests.LienMeetRequest;
import com.example.demo.services.PaiementService;
import com.stripe.Stripe;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;


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
    public ResponseEntity<RendezVousEntity> creerDemande(@RequestBody DemandeRequest demandeRequest) {
        RendezVousEntity rendezVousEntity = paiementService.creerDemande(demandeRequest);
        if (rendezVousEntity != null) {
            return ResponseEntity.ok(rendezVousEntity);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/rendez-vous-By-id-consultant/{idConsultant}")
    public ResponseEntity<?> getRendezVousByIdConsultant(@PathVariable String idConsultant) {
        ResponseEntity<?> response = paiementService.getRendezVousByIdConsultant(idConsultant);
        return response;
    }
    
    @GetMapping("/rendez-vous-refuser-By-id-consultant/{idConsultant}")
    public ResponseEntity<?> getRendezVousRefuserByIdConsultant(@PathVariable String idConsultant) {
        ResponseEntity<?> response = paiementService.getRendezVousRefuserByIdConsultant(idConsultant);
        return response;
    }
    
    @GetMapping("/rendez-vous-accepter-By-id-consultant/{idConsultant}")
    public ResponseEntity<?> getRendezVousAccepterByIdConsultant(@PathVariable String idConsultant) {
        ResponseEntity<?> response = paiementService.getRendezVousAccepterByIdConsultant(idConsultant);
        return response;
    }
    
    @GetMapping("/refuse-rendez-vous/{idRendezVous}")
    public ResponseEntity<?> refuseRendezVous(@PathVariable long idRendezVous) {
        try {
            // Appel de la méthode de service pour refuser le rendez-vous
            ResponseEntity<?> response = paiementService.refuseRendezVous(idRendezVous);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } 

    @PostMapping("/accepte-rendez-vous")
    public ResponseEntity<?> sendLienMeet(@RequestBody LienMeetRequest lienRequest) {
        try {
            String[] recipients = {lienRequest.emailClient, lienRequest.emailConsultant};
            String idReclamation=IdGenerator.generateId();
            ResponseEntity<Object> response = paiementService.sendEmail(recipients,
                "lien consultation",
                "date de consultation : " + lienRequest.date + "\n\nheure debut : " + lienRequest.heureDebut + "\n\nCliquez sur le lien ci-dessous pour accéder à la consultation : \n\n" + lienRequest.lien +"\n\nPour soumettre une réclamation, veuillez utiliser le code suivant : "+idReclamation+ " \n\nsur le formulaire de réclamation.",
                lienRequest.idRendezVous,
                lienRequest.lien,idReclamation);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
    
    @GetMapping("/reclamation/{reclamationId}")
    public Map<String, Object> getRendezVous(@PathVariable String reclamationId) {
        return paiementService.getRendezVousByReclamationId(reclamationId);
    }
    
    @PostMapping("/valide-reclamation/{idRendezVous}")
    public ResponseEntity<Map<String, Object>> valideReclamation(@PathVariable long idRendezVous) {
        Map<String, Object> response = paiementService.valideReclamation(idRendezVous);
        if ("success".equals(response.get("status"))) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}