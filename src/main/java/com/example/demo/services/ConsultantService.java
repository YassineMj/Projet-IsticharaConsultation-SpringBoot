package com.example.demo.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.requests.ConsultantRequest;

@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;

    public String saveConsultant(ConsultantRequest consultantRequest) throws IOException {
        // Enregistrez le profil du consultant dans la base de données
        ConsultantEntity consultantEntity = new ConsultantEntity();
        consultantEntity.setFormations(consultantRequest.getFormations());
        consultantEntity.setEducations(consultantRequest.getEducations());
        consultantEntity.setExperiencesPro(consultantRequest.getExperiencesPro());
        consultantEntity.setSpecialisation(consultantRequest.getSpecialisation());
        consultantEntity.setDescriptionProfile(consultantRequest.getDescriptionProfile());
        consultantEntity.setFrancais(consultantRequest.isFrancais());
        consultantEntity.setAnglais(consultantRequest.isAnglais());
        consultantEntity.setArabe(consultantRequest.isArabe());
        consultantEntity.setEspagnol(consultantRequest.isEspagnol());
        consultantEntity.setNom(consultantRequest.getNom());
        consultantEntity.setPrenom(consultantRequest.getPrenom());
        consultantEntity.setPays(consultantRequest.getPays());
        consultantEntity.setVille(consultantRequest.getVille());
        consultantEntity.setEmail(consultantRequest.getEmail());
        consultantEntity.setMotDePasse(consultantRequest.getMotDePasse());
        consultantEntity.setNumeroTelephone(consultantRequest.getNumeroTelephone());
        consultantEntity.setCin(consultantRequest.getCin());
        consultantEntity.setAdresse(consultantRequest.getAdresse());
        consultantEntity.setRib(consultantRequest.getRib());

        // Gérez le stockage de la photo de profil
        if (consultantRequest.getPhotoProfile() != null && !consultantRequest.getPhotoProfile().isEmpty()) {
        	// Supprimez le préfixe s'il existe
            String base64String = consultantRequest.getPhotoProfile();
            if (base64String.startsWith("data:image/jpeg;base64,")) {
                base64String = base64String.replace("data:image/jpeg;base64,", "");
            }

            // Décodez la chaîne Base64
            byte[] decodedImage = Base64.getDecoder().decode(base64String);
            
            String imagePath = "C:\\Users\\yassi\\Desktop\\IsticharaConsultation\\src\\assets\\images-consultants\\" + "profile_image_" + System.currentTimeMillis() + ".jpg";
            Files.write(Paths.get(imagePath), decodedImage);
            consultantEntity.setPhotoProfile(imagePath);
        }

        consultantRepository.save(consultantEntity);
        return consultantEntity.getIdConsultant();
    }
    
    public void deleteConsultant(Long consultantId) {
        consultantRepository.deleteById(consultantId);
    }
}