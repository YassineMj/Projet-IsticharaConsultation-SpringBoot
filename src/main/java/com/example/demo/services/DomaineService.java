package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        return domaineRepository.findAll();
    }
}
