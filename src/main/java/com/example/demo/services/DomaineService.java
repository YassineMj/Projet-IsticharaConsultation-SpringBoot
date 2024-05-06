package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.methodeStatic.IdGenerator;
import com.example.demo.repositories.DomaineRepository;
import com.example.demo.requests.DomaineRequest;

@Service
public class DomaineService {
    @Autowired
    private DomaineRepository domaineRepository;

    public DomaineEntity createDomaine(DomaineRequest domaine) {
        DomaineEntity domaineEntity=new DomaineEntity();
        domaineEntity.setNomDomaine(domaine.getNomDomain());
        domaineEntity.setDescriptionDomaine(domaine.getDescriptionDomaine());
        domaineEntity.setIdDomaine(IdGenerator.generateId().toString());
        
        // Gérez le stockage de l'image
        if (domaine.getPathImage() != null && !domaine.getPathImage().isEmpty()) {
        	// Supprimez le préfixe s'il existe
            String base64String = domaine.getPathImage();
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
            
            try {
				Files.write(Paths.get(imagePath), decodedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
            domaineEntity.setPathImage(imagePath);
        }

        return domaineRepository.save(domaineEntity);
    }
    
    public List<DomaineEntity> getAllDomaines() {
        List<DomaineEntity> domaines =new ArrayList<>();
        domaines=domaineRepository.findAll();
        for ( DomaineEntity d: domaines) {
        	  java.nio.file.Path path = Paths.get(d.getPathImage());
              d.setPathImage(path.getFileName().toString());
        }
        return domaines;
        
        
    }
    
    public Page<DomaineEntity> getDomainesPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DomaineEntity> domainesPage = domaineRepository.findAll(pageable);
        
        // Appliquer le traitement à chaque entité dans la page
        domainesPage.getContent().forEach(domaine -> {
            java.nio.file.Path path = Paths.get(domaine.getPathImage());
            domaine.setPathImage(path.getFileName().toString());
            // Ajoutez d'autres traitements ici si nécessaire
        });
        
        return domainesPage;
    }

	public DomaineEntity getDomaineById(String idDomain) {
		
		DomaineEntity domaineEntity= domaineRepository.findByIdDomaine(idDomain);
		java.nio.file.Path path = Paths.get(domaineEntity.getPathImage());
		domaineEntity.setPathImage(path.getFileName().toString());
		
		return domaineEntity;
	}

	public DomaineEntity updateDomaine(String idDomain, DomaineRequest domaine) {
		DomaineEntity domaineEntity= domaineRepository.findByIdDomaine(idDomain);
		
		domaineEntity.setNomDomaine(domaine.getNomDomain());
        domaineEntity.setDescriptionDomaine(domaine.getDescriptionDomaine());
        
        // Gérez le stockage de l'image
        if (domaine.getPathImage() != null && !domaine.getPathImage().isEmpty()) {
        	// Supprimez le préfixe s'il existe
            String base64String = domaine.getPathImage();
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
            
            try {
				Files.write(Paths.get(imagePath), decodedImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
            domaineEntity.setPathImage(imagePath);
        }

        return domaineRepository.save(domaineEntity);
	}

	public void deleteDomaine(String idDomain) {
		DomaineEntity domaineEntity=domaineRepository.findByIdDomaine(idDomain);
		domaineRepository.delete(domaineEntity);

	}

}
