package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.CategorieEntity;
import com.example.demo.entities.DomaineEntity;
import com.example.demo.methodeStatic.IdGenerator;
import com.example.demo.reponses.CategorieResponse;
import com.example.demo.repositories.CategorieRepository;
import com.example.demo.repositories.DomaineRepository;
import com.example.demo.requests.CategorieRequest;

@Service
public class CategorieService {
	
	@Autowired
	CategorieRepository categorieRepository;
	
	@Autowired DomaineRepository domaineRepository;
	
	public CategorieEntity createCategorie(String idDomaine, CategorieRequest categorieRequest) {
		CategorieEntity categorieEntity= new CategorieEntity();
		categorieEntity.setDescriptionCategorie(categorieRequest.getDescriptionCategorie());
		categorieEntity.setNomCategorie(categorieRequest.getNomCategorie());
		categorieEntity.setIdCategorie(IdGenerator.generateId().toString());

        DomaineEntity domaine = domaineRepository.findByIdDomaine(idDomaine);
        if (domaine != null) {
        	categorieEntity.setDomaine(domaine);
            return categorieRepository.save(categorieEntity);
        }
        return null; // Gérer le cas où le domaine n'est pas trouvé
    }
	
	public List<CategorieResponse> getCategoriesByDomaineId(String idDomaine) {
		List<CategorieEntity> listeCategoriesEntity= categorieRepository.findByDomaineIdDomaine(idDomaine);
		
		List<CategorieResponse> listeCategoriesResponse= new ArrayList<>();
		for(CategorieEntity c : listeCategoriesEntity) {
			CategorieResponse cr= new CategorieResponse();
			BeanUtils.copyProperties(c,cr);
			listeCategoriesResponse.add(cr);
		}
		
		return listeCategoriesResponse;
	}

	public List<CategorieEntity> getAllCategories() {
		return categorieRepository.findAll();
	}

	public CategorieEntity getCategorie(String idCategory) {
		return categorieRepository.findByIdCategorie(idCategory);
	}

	public CategorieEntity updateCategorie(String idCategory, String idDomaine, CategorieRequest categorieRequest) {
		CategorieEntity categorieEntity=categorieRepository.findByIdCategorie(idCategory);
		categorieEntity.setDescriptionCategorie(categorieRequest.getDescriptionCategorie());
		categorieEntity.setNomCategorie(categorieRequest.getNomCategorie());
		DomaineEntity domaine = domaineRepository.findByIdDomaine(idDomaine);
        if (domaine != null) {
        	categorieEntity.setDomaine(domaine);
            return categorieRepository.save(categorieEntity);
        }
		return null;
	}

	public void deleteCategorie(String idCategorie) {
		CategorieEntity categorieEntity=categorieRepository.findByIdCategorie(idCategorie);
		categorieRepository.delete(categorieEntity);
	}
}
