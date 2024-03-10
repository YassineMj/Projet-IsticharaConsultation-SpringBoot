package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.DomaineEntity;
import com.example.demo.methodeStatic.IdGenerator;
import com.example.demo.reponses.ConsultantResponseDomaine;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.DomaineRepository;
import com.example.demo.requests.ConsultantRequest;

import jakarta.persistence.criteria.Path;

@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;
    
    @Autowired
    private DomaineRepository domaineRepository;

    public String saveConsultant(ConsultantRequest consultantRequest) throws IOException {
    	DomaineEntity domaineEntity=domaineRepository.findByIdDomaine(consultantRequest.getIdDomaine());
        // Enregistrez le profil du consultant dans la base de données
        ConsultantEntity consultantEntity = new ConsultantEntity();
        consultantEntity.setIdConsultant(IdGenerator.generateId().toString());
        consultantEntity.setFormations(consultantRequest.getFormations());
        consultantEntity.setEducations(consultantRequest.getEducations());
        consultantEntity.setExperiencesPro(consultantRequest.getExperiencesPro());
        consultantEntity.setDomaine(domaineEntity);
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
        consultantEntity.setBanque(consultantRequest.getBanque());
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

            File file = new File("C:\\Users\\HP\\Desktop\\PROJET CONSULTATION\\IsticharaProjet");
            if(file.exists()==true) {
                imagePath = "C:\\Users\\HP\\Desktop\\PROJET CONSULTATION\\IsticharaProjet\\src\\assets\\images-consultants" + "profile_image_" + System.currentTimeMillis() + ".jpg";
            }
            
            Files.write(Paths.get(imagePath), decodedImage);
            consultantEntity.setPhotoProfile(imagePath);
        }

        consultantRepository.save(consultantEntity);
        return consultantEntity.getIdConsultant();
    }
    
    public ConsultantEntity getConsultantById(String idConsultant) {
        try {
            // Use the repository to find the consultant by ID
            ConsultantEntity entityConsultant = consultantRepository.findByIdConsultant(idConsultant);

            java.nio.file.Path path = Paths.get(entityConsultant.getPhotoProfile());
            entityConsultant.setPhotoProfile(path.getFileName().toString());
            // Return the consultant if present, otherwise return null
            return entityConsultant;
        } catch (Exception e) {
            // Handle exceptions as needed (e.g., log the error)
            e.printStackTrace(); // You may want to log the exception using a logging framework

            // You might also want to throw a custom exception or handle it differently based on your use case
            throw new RuntimeException("Error fetching consultant by ID: " + idConsultant, e);
        }
    }
    
    public List<ConsultantResponseDomaine> getConsultantsByDomaine(String idDomaine) {
        List<ConsultantEntity> consultants = consultantRepository.findByDomaineIdDomaine(idDomaine);
        
        List<ConsultantResponseDomaine> listResponse = new ArrayList<>();
        for (ConsultantEntity consultantEntity : consultants) {
            ConsultantResponseDomaine responseDomaine = new ConsultantResponseDomaine();
            BeanUtils.copyProperties(consultantEntity, responseDomaine);
            java.nio.file.Path path = Paths.get(responseDomaine.getPhotoProfile());
            responseDomaine.setPhotoProfile(path.getFileName().toString());
            listResponse.add(responseDomaine);
        }

        return listResponse;
    }
    
    public ConsultantEntity updateAllAttributesById(ConsultantEntity consultant){
        ConsultantEntity existingConsultant = consultantRepository.findByIdConsultant(consultant.getIdConsultant());
        if (existingConsultant != null) {
            // Update the consultant entity with the provided values
            existingConsultant.setFormations(consultant.getFormations());
            existingConsultant.setEducations(consultant.getEducations());
            existingConsultant.setExperiencesPro(consultant.getExperiencesPro());

            existingConsultant.setDescriptionProfile(consultant.getDescriptionProfile());
            existingConsultant.setFrancais(consultant.isFrancais());
            existingConsultant.setAnglais(consultant.isAnglais());
            existingConsultant.setArabe(consultant.isArabe());
            existingConsultant.setEspagnol(consultant.isEspagnol());
            existingConsultant.setNom(consultant.getNom());
            existingConsultant.setPrenom(consultant.getPrenom());
            existingConsultant.setPays(consultant.getPays());
            existingConsultant.setVille(consultant.getVille());
            existingConsultant.setEmail(consultant.getEmail());
            existingConsultant.setMotDePasse(consultant.getMotDePasse());
            existingConsultant.setNumeroTelephone(consultant.getNumeroTelephone());
            existingConsultant.setCin(consultant.getCin());
            existingConsultant.setAdresse(consultant.getAdresse());
            existingConsultant.setRib(consultant.getRib());
            existingConsultant.setBanque(consultant.getBanque());            
            
            
            java.nio.file.Path path = Paths.get(existingConsultant.getPhotoProfile());
            String imageNom=path.getFileName().toString(); 
            
            if ((consultant.getPhotoProfile() != null && !consultant.getPhotoProfile().isEmpty()) && imageNom.equals(consultant.getPhotoProfile()) ==false ) {

            	// Supprimez le préfixe s'il existe
                String base64String = consultant.getPhotoProfile();
                if (base64String.startsWith("data:image/jpeg;base64,")) {
                    base64String = base64String.replace("data:image/jpeg;base64,", "");

                }

                // Décodez la chaîne Base64
                byte[] decodedImage = Base64.getDecoder().decode(base64String);
                
                String imagePath = "C:\\Users\\yassi\\Desktop\\IsticharaConsultation\\src\\assets\\images-consultants\\" + "profile_image_" + System.currentTimeMillis() + ".jpg";

                File file = new File("C:\\Users\\HP\\Desktop\\PROJET CONSULTATION\\IsticharaProjet");
                if(file.exists()==true) {
                    imagePath = "C:\\Users\\HP\\Desktop\\PROJET CONSULTATION\\IsticharaProjet\\src\\assets\\images-consultants" + "profile_image_" + System.currentTimeMillis() + ".jpg";
                }
                
                try {
					Files.write(Paths.get(imagePath), decodedImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                existingConsultant.setPhotoProfile(imagePath);
            }

            return consultantRepository.save(existingConsultant);
        }
        return null;
    }
    
    public void deleteConsultant(Long consultantId) {
        consultantRepository.deleteById(consultantId);
    }
}