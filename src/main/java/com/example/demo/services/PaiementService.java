package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ClientEntity;
import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.PlanConsultationEntity;
import com.example.demo.entities.QuestionEntity;
import com.example.demo.entities.RendezVousEntity;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.PlanConsultationRepository;
import com.example.demo.repositories.RendezVousRepository;
import com.example.demo.requests.DemandeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaiementService {

	@Autowired
    private PlanConsultationRepository planConsultationRepository;
	
	@Autowired
    private ClientRepository clientRepository;
	
    @Autowired
    private ConsultantRepository consultantRepository;
    
    @Autowired
    private RendezVousRepository rendezVousRepository;
    
    @Value("${stripe.apikey}")
    private String stripeSecretkey;
	
	public ResponseEntity<?> getDetailsConsultation(Long idPlan) {
        String details = planConsultationRepository.detailConsultationByIdPlan(idPlan);
        String[] parts = details.split(",");

        Map<String, String> detailsMap = new HashMap();
        if (details.equals("")==false) {
        	detailsMap.put("nom", parts[0]);
             detailsMap.put("prenom", parts[1]);
             detailsMap.put("jourDebut", parts[2]);
             detailsMap.put("dateJourDebut", parts[3]);
             detailsMap.put("heureDebut", parts[4]);
             detailsMap.put("heureFin", parts[5]);
             detailsMap.put("prixConsultation", parts[6]);
             detailsMap.put("francais", parts[7]);
             detailsMap.put("arabe", parts[8]);
             detailsMap.put("anglais", parts[9]);
             detailsMap.put("espagnol", parts[10]);
             detailsMap.put("idConsultant", parts[11]);

            return ResponseEntity.ok(detailsMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
    
    public RendezVousEntity creerDemande(DemandeRequest demandeRequest) {
        try {
            // Création d'une nouvelle entité Client
            ClientEntity client = new ClientEntity();
            client.setIdClientStripe(demandeRequest.getIdClientStripe());
            client.setIdCardStripe(demandeRequest.getIdCardStripe());
            client.setNomClient(demandeRequest.getNomClient());
            client.setEmailClient(demandeRequest.getEmailClient());
            client.setVilleClient(demandeRequest.getVilleClient());
            client.setPaysClient(demandeRequest.getPaysClient());
            client.setAdresseClient(demandeRequest.getAdresseClient());
            client.setTelephone(demandeRequest.getTelephone());

            client = clientRepository.save(client);

            // Configuration de la clé secrète de Stripe en mode test
            Stripe.apiKey = stripeSecretkey;

            // Création du paiement depuis le client source vers votre compte Stripe
            PaymentIntent intent = PaymentIntent.create(PaymentIntentCreateParams.builder()
                    .setAmount((long) demandeRequest.getPrixTotal()*10) // Montant à transférer en centimes
                    .setCurrency("eur") // Devise du montant
                    .setCustomer(demandeRequest.getIdClientStripe()) // Identifiant du client source
                    .build());

            // Affichage de l'ID du paiement créé
            System.out.println("Payment created: " + intent.getId());
            
          //Rendez-vous
            RendezVousEntity rendezVousEntity= new RendezVousEntity();
            rendezVousEntity.setClient(client);
            ConsultantEntity consultant=consultantRepository.findById(demandeRequest.getIdConsultant())        
            		.orElseThrow(() -> new EntityNotFoundException("Consultant not found with id: " + demandeRequest.getIdConsultant()));
;
            rendezVousEntity.setConsultant(consultant);
            
            PlanConsultationEntity plan = planConsultationRepository.findById(demandeRequest.getIdPlan())
                    .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + demandeRequest.getIdPlan()));
;
            rendezVousEntity.setPlan(plan);
            rendezVousEntity.setPaiement(intent.getId());
            rendezVousEntity.setMessage(demandeRequest.getMessage());
            

            // Confirmation du paiement avec une URL de retour fictive
            PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
                    .setPaymentMethod(demandeRequest.getIdCardStripe())
                    .setReturnUrl("http://example.com")
                    .build();
            intent.confirm(confirmParams);

            
            // Retour de la réponse de succès
            return rendezVousRepository.save(rendezVousEntity);

        } catch (StripeException e) {
            // Gestion des erreurs et retour d'une réponse d'erreur appropriée
            e.printStackTrace();
        }
        return null;
    }
    
    
    /*public ResponseEntity<?> getRendezVousByIdConsultant(Long idConsultant) {
        String details = rendezVousRepository.getRendezVousByIdConsultant(idConsultant);
        String[] parts = details.split(",");

        Map<String, String> detailsMap = new HashMap();
        if (details.equals("")==false) {
        	detailsMap.put("nom", parts[0]);
             detailsMap.put("email", parts[1]);
             detailsMap.put("adresse", parts[2]);
             detailsMap.put("telephone", parts[3]);
             detailsMap.put("pays", parts[4]);
             detailsMap.put("ville", parts[5]);
             detailsMap.put("id_paiement", parts[6]);
             detailsMap.put("date_jour", parts[7]);
             detailsMap.put("heure_debut", parts[8]);
             detailsMap.put("heure_fin", parts[9]);
             detailsMap.put("id_card", parts[10]);
             detailsMap.put("message", parts[11]);

            return ResponseEntity.ok(detailsMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    
}
