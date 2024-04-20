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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ClientEntity;
import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.PlanConsultationEntity;
import com.example.demo.entities.QuestionEntity;
import com.example.demo.entities.RendezVousAccepteEntity;
import com.example.demo.entities.RendezVousEntity;
import com.example.demo.reponses.RendezVousResponse;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.PlanConsultationRepository;
import com.example.demo.repositories.RendezVousAccepteRepository;
import com.example.demo.repositories.RendezVousRepository;
import com.example.demo.requests.DemandeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;

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
    
    @Autowired
	private JavaMailSender javaMailSender;
    
    @Autowired
    private RendezVousAccepteRepository rendezVousAccepteRepository;
        
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
            client.setNumCard(demandeRequest.getNumCard());
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
    
    
    public ResponseEntity<?> getRendezVousByIdConsultant(String idConsultant) {
        List<String> details = rendezVousRepository.getRendezVousByIdConsultant(idConsultant);
        List<RendezVousResponse> listRendezVousResponse=new ArrayList<>();
        
        for(int i=0;i<details.size();i++) {
        	
        	String[] parts = details.get(i).split(",");
        	RendezVousResponse rendezVousResponse = new RendezVousResponse();
            if (details.equals("")==false) {
            	
            	rendezVousResponse.detailsMap.put("idRendezVous", parts[0]);
            	rendezVousResponse.detailsMap.put("nom", parts[1]);
            	rendezVousResponse.detailsMap.put("email", parts[2]);
            	rendezVousResponse.detailsMap.put("adresse", parts[3]);
            	rendezVousResponse.detailsMap.put("telephone", parts[4]);
            	rendezVousResponse.detailsMap.put("pays", parts[5]);
            	rendezVousResponse.detailsMap.put("ville", parts[6]);
            	rendezVousResponse.detailsMap.put("id_paiement", parts[7]);
            	rendezVousResponse.detailsMap.put("date_jour", parts[8]);
            	rendezVousResponse.detailsMap.put("heure_debut", parts[9]);
            	rendezVousResponse.detailsMap.put("heure_fin", parts[10]);
            	rendezVousResponse.detailsMap.put("id_card", parts[11]);
            	rendezVousResponse.detailsMap.put("message", parts[12]);
            	rendezVousResponse.detailsMap.put("idPlan", parts[13]);
            	rendezVousResponse.detailsMap.put("numCard", parts[14]);
            }
          listRendezVousResponse.add(rendezVousResponse);
        } 
        if(listRendezVousResponse.size()>0)
        {
        	return ResponseEntity.ok(listRendezVousResponse);
        }
        return ResponseEntity.notFound().build();
    }
    
    
    public ResponseEntity<?> refuseRendezVous(long idRendezVous){
        
    	try {
    		RendezVousEntity rendezVousEntity=rendezVousRepository.getById(idRendezVous);
        	rendezVousEntity.setRefuse(true);
        	
        	// Configuration de la clé secrète de Stripe en mode test
            Stripe.apiKey = stripeSecretkey;

            // Création du remboursement
            RefundCreateParams refundParams = RefundCreateParams.builder()
                    .setPaymentIntent(rendezVousEntity.getPaiement()) // Identifiant du paiement à rembourser
                    .build();

            Refund refund = Refund.create(refundParams);

            // Envoi de l'email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("istichara66@gmail.com");
            message.setTo(rendezVousEntity.getClient().getEmailClient());
            message.setText("Nous regrettons de devoir refuser le rendez-vous et vous assurez que le remboursement effectué.");
            message.setSubject("Refus de rendez-vous");
            javaMailSender.send(message);
            
            // Affichage de l'ID du remboursement créé
            System.out.println("Refund created: " + refund.getId());

            
            // Retour de la réponse de succès        	
        	return ResponseEntity.ok(rendezVousRepository.save(rendezVousEntity));
    	}catch (Exception e) {
            return ResponseEntity.notFound().build();
		}
    	
     }
 
    public ResponseEntity<Object> sendEmail(String[] toEmail, String subject, String body, long idRendezVous, String lien) {
        try {
            // Modification du rendez-vous principal
            RendezVousEntity rendezVousEntity = rendezVousRepository.getById(idRendezVous);
            rendezVousEntity.setAccepte(true);
            rendezVousEntity = rendezVousRepository.save(rendezVousEntity);

            // Recherche des rendez-vous avec le même idPlan
            List<RendezVousEntity> autresRendezVous = rendezVousRepository.findByPlanId(rendezVousEntity.getPlan().getId());

            // Modification des autres rendez-vous en mettant refuse à true
            for (RendezVousEntity rdv : autresRendezVous) {
                if (rdv.getId() != rendezVousEntity.getId()) {
                    refuseRendezVous(rdv.getId());
                }
            }

            // Ajout du rendez-vous accepté dans la table accepte
            RendezVousAccepteEntity rendezVousAccepteEntity = new RendezVousAccepteEntity();
            rendezVousAccepteEntity.setRendezVous(rendezVousEntity);
            rendezVousAccepteEntity.setLienMeet(lien);
            rendezVousAccepteRepository.save(rendezVousAccepteEntity);

            // Envoi de l'email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("istichara66@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            javaMailSender.send(message);

            return ResponseEntity.ok("Email envoyé avec succès");
        } catch (Exception e) {
            // Gérer l'exception selon vos besoins
            System.out.println("Une erreur s'est produite : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi de l'email");
        }
    }
    
    public ResponseEntity<?> getRendezVousRefuserByIdConsultant(String idConsultant) {
        List<String> details = rendezVousRepository.getRendezVousRefuserByIdConsultant(idConsultant);
        List<RendezVousResponse> listRendezVousResponse=new ArrayList<>();
        
        for(int i=0;i<details.size();i++) {
        	
        	String[] parts = details.get(i).split(",");
        	RendezVousResponse rendezVousResponse = new RendezVousResponse();
            if (details.equals("")==false) {
            	
            	rendezVousResponse.detailsMap.put("idRendezVous", parts[0]);
            	rendezVousResponse.detailsMap.put("nom", parts[1]);
            	rendezVousResponse.detailsMap.put("email", parts[2]);
            	rendezVousResponse.detailsMap.put("adresse", parts[3]);
            	rendezVousResponse.detailsMap.put("telephone", parts[4]);
            	rendezVousResponse.detailsMap.put("pays", parts[5]);
            	rendezVousResponse.detailsMap.put("ville", parts[6]);
            	rendezVousResponse.detailsMap.put("id_paiement", parts[7]);
            	rendezVousResponse.detailsMap.put("date_jour", parts[8]);
            	rendezVousResponse.detailsMap.put("heure_debut", parts[9]);
            	rendezVousResponse.detailsMap.put("heure_fin", parts[10]);
            	rendezVousResponse.detailsMap.put("id_card", parts[11]);
            	rendezVousResponse.detailsMap.put("message", parts[12]);
            	rendezVousResponse.detailsMap.put("idPlan", parts[13]);
            	rendezVousResponse.detailsMap.put("numCard", parts[14]);
            }
          listRendezVousResponse.add(rendezVousResponse);
        } 
        if(listRendezVousResponse.size()>0)
        {
        	return ResponseEntity.ok(listRendezVousResponse);
        }
        return ResponseEntity.notFound().build();
    }
    
    
    public ResponseEntity<?> getRendezVousAccepterByIdConsultant(String idConsultant) {
        List<String> details = rendezVousAccepteRepository.getRendezVousAccepterByIdConsultant(idConsultant);
        List<RendezVousResponse> listRendezVousResponse=new ArrayList<>();
        
        for(int i=0;i<details.size();i++) {
        	
        	String[] parts = details.get(i).split(",");
        	RendezVousResponse rendezVousResponse = new RendezVousResponse();
            if (details.equals("")==false) {
            	
            	rendezVousResponse.detailsMap.put("idRendezVous", parts[0]);
            	rendezVousResponse.detailsMap.put("nom", parts[1]);
            	rendezVousResponse.detailsMap.put("email", parts[2]);
            	rendezVousResponse.detailsMap.put("adresse", parts[3]);
            	rendezVousResponse.detailsMap.put("telephone", parts[4]);
            	rendezVousResponse.detailsMap.put("pays", parts[5]);
            	rendezVousResponse.detailsMap.put("ville", parts[6]);
            	rendezVousResponse.detailsMap.put("id_paiement", parts[7]);
            	rendezVousResponse.detailsMap.put("date_jour", parts[8]);
            	rendezVousResponse.detailsMap.put("heure_debut", parts[9]);
            	rendezVousResponse.detailsMap.put("heure_fin", parts[10]);
            	rendezVousResponse.detailsMap.put("id_card", parts[11]);
            	rendezVousResponse.detailsMap.put("message", parts[12]);
            	rendezVousResponse.detailsMap.put("idPlan", parts[13]);
            	rendezVousResponse.detailsMap.put("numCard", parts[14]);
            	rendezVousResponse.detailsMap.put("lien", parts[15]);

            }
          listRendezVousResponse.add(rendezVousResponse);
        } 
        if(listRendezVousResponse.size()>0)
        {
        	return ResponseEntity.ok(listRendezVousResponse);
        }
        return ResponseEntity.notFound().build();
    }
    
    
}
