package com.example.demo.services;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.repositories.DomaineRepository;

@Service
public class DomaineService {
    @Autowired
    private DomaineRepository domaineRepository;

    public DomaineEntity createDomaine(DomaineEntity domaine) {
        // Vous pouvez ajouter des vérifications et des validations ici avant de sauvegarder dans la base de données
        return domaineRepository.save(domaine);
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

}
