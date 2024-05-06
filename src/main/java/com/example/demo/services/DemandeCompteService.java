package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.DemandeCompteEntity;
import com.example.demo.entities.DomaineEntity;
import com.example.demo.methodeStatic.IdGenerator;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.DemandeCompteRepository;
import com.example.demo.repositories.DomaineRepository;
import com.example.demo.requests.DemandeComptetRequest;

@Service
public class DemandeCompteService {

	@Autowired
    private DemandeCompteRepository demandeCompteRepository;
    
    @Autowired
    private DomaineRepository domaineRepository;
    
    @Autowired
    ConsultantRepository consultantRepository;
    
    public String saveDemendeCompte(DemandeComptetRequest demandeComptetRequest) throws IOException {
    	DomaineEntity domaineEntity=domaineRepository.findByIdDomaine(demandeComptetRequest.getIdDomaine());
        // Enregistrez le profil du consultant dans la base de données
        DemandeCompteEntity demandeCompteEntity = new DemandeCompteEntity();
        demandeCompteEntity.setIdDemande(IdGenerator.generateId().toString());
        demandeCompteEntity.setFormations(demandeComptetRequest.getFormations());
        demandeCompteEntity.setEducations(demandeComptetRequest.getEducations());
        demandeCompteEntity.setExperiencesPro(demandeComptetRequest.getExperiencesPro());
        demandeCompteEntity.setDomaine(domaineEntity);
        demandeCompteEntity.setSpecialisation(demandeComptetRequest.getSpecialisation());
        demandeCompteEntity.setDescriptionProfile(demandeComptetRequest.getDescriptionProfile());
        demandeCompteEntity.setFrancais(demandeComptetRequest.isFrancais());
        demandeCompteEntity.setAnglais(demandeComptetRequest.isAnglais());
        demandeCompteEntity.setArabe(demandeComptetRequest.isArabe());
        demandeCompteEntity.setEspagnol(demandeComptetRequest.isEspagnol());
        demandeCompteEntity.setNom(demandeComptetRequest.getNom());
        demandeCompteEntity.setPrenom(demandeComptetRequest.getPrenom());
        demandeCompteEntity.setPays(demandeComptetRequest.getPays());
        demandeCompteEntity.setVille(demandeComptetRequest.getVille());
        demandeCompteEntity.setEmail(demandeComptetRequest.getEmail());
        demandeCompteEntity.setMotDePasse(demandeComptetRequest.getMotDePasse());
        demandeCompteEntity.setNumeroTelephone(demandeComptetRequest.getNumeroTelephone());
        demandeCompteEntity.setCin(demandeComptetRequest.getCin());
        demandeCompteEntity.setAdresse(demandeComptetRequest.getAdresse());
        demandeCompteEntity.setBanque(demandeComptetRequest.getBanque());
        demandeCompteEntity.setRib(demandeComptetRequest.getRib());

        // Gérez le stockage de la photo de profil
        if (demandeComptetRequest.getPhotoProfile() != null && !demandeComptetRequest.getPhotoProfile().isEmpty()) {
        	// Supprimez le préfixe s'il existe
            String base64String = demandeComptetRequest.getPhotoProfile();
            if (base64String.startsWith("data:image/jpeg;base64,")) {
                base64String = base64String.replace("data:image/jpeg;base64,", "");
            }

            // Décodez la chaîne Base64
            byte[] decodedImage = Base64.getDecoder().decode(base64String);
            
            String imagePath = "C:\\Users\\yassi\\Desktop\\IsticharaConsultation\\src\\assets\\images-consultants\\" + "profile_image_" + System.currentTimeMillis() + ".jpg";
            File file = new File("C:\\Users\\HP\\Desktop\\PROJET ANGULAR ISTICHARA CONSULTATION\\IsticharaProjet");
            if(file.exists()==true) {
                imagePath = "C:\\Users\\HP\\Desktop\\PROJET ANGULAR ISTICHARA CONSULTATION\\IsticharaProjet\\src\\assets\\images-consultants\\" + "profile_image_" + System.currentTimeMillis() + ".jpg";
            }
            
            Files.write(Paths.get(imagePath), decodedImage);
            demandeCompteEntity.setPhotoProfile(imagePath);
        }

        demandeCompteRepository.save(demandeCompteEntity);
        return demandeCompteEntity.getIdDemande();
    }

	public List<DemandeCompteEntity> getAllDemande() {
		List<DemandeCompteEntity> listDemande=demandeCompteRepository.findAll();
		
		for(DemandeCompteEntity d : listDemande) {
			java.nio.file.Path path = Paths.get(d.getPhotoProfile());
            d.setPhotoProfile(path.getFileName().toString());
		}
		
		return listDemande;
	}

	public DemandeCompteEntity getDemandeCompteById(String idDemande) {
		DemandeCompteEntity demandeCompteEntity=demandeCompteRepository.findByIdDemande(idDemande);
		
		java.nio.file.Path path = Paths.get(demandeCompteEntity.getPhotoProfile());
		demandeCompteEntity.setPhotoProfile(path.getFileName().toString());
		return demandeCompteEntity ;
	}

	public ResponseEntity<Map<String, String>> acitiveCompte(String idDemande) {
		Map<String, String> response = new HashMap<>();

        // Rechercher la demande par son id
        DemandeCompteEntity demande = demandeCompteRepository.findByIdDemande(idDemande);

        if (demande != null) {
            // Créer une nouvelle entité Consultant avec les données de la demande
            ConsultantEntity consultant = new ConsultantEntity();
            consultant.setFormations(new ArrayList<>(demande.getFormations()));
            consultant.setEducations(new ArrayList<>(demande.getEducations()));
            consultant.setExperiencesPro(new ArrayList<>(demande.getExperiencesPro()));
            consultant.setSpecialisation(new ArrayList<>(demande.getSpecialisation()));
            consultant.setDomaine(demande.getDomaine());
            consultant.setDescriptionProfile(demande.getDescriptionProfile());
            consultant.setFrancais(demande.isFrancais());
            consultant.setAnglais(demande.isAnglais());
            consultant.setArabe(demande.isArabe());
            consultant.setEspagnol(demande.isEspagnol());
            consultant.setNom(demande.getNom());
            consultant.setPrenom(demande.getPrenom());
            consultant.setPays(demande.getPays());
            consultant.setVille(demande.getVille());
            consultant.setEmail(demande.getEmail());
            consultant.setMotDePasse(demande.getMotDePasse());
            consultant.setNumeroTelephone(demande.getNumeroTelephone());
            consultant.setCin(demande.getCin());
            consultant.setAdresse(demande.getAdresse());
            consultant.setRib(demande.getRib());
            consultant.setBanque(demande.getBanque());
            consultant.setPhotoProfile(demande.getPhotoProfile());
            consultant.setIdConsultant(idDemande);

            // Sauvegarder le consultant dans la base de données
            consultantRepository.save(consultant);

            // Supprimer la demande correspondante
            demandeCompteRepository.delete(demande);

            response.put("message", "Compte activé avec succès pour la demande " + idDemande);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Demande introuvable pour l'ID " + idDemande);
            return ResponseEntity.badRequest().body(response);
        }
    }

}
