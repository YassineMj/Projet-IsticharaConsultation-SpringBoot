package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.CategorieEntity;
import com.example.demo.reponses.CategorieResponse;
import com.example.demo.repositories.CategorieRepository;

@Service
public class CategorieService {
	
	@Autowired
	CategorieRepository categorieRepository;
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
}
