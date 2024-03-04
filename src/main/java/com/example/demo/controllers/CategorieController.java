package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.CategorieEntity;
import com.example.demo.reponses.CategorieResponse;
import com.example.demo.services.CategorieService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/IsticharaConsultation/api/categories")
public class CategorieController {
	
	@Autowired
	CategorieService categorieService;
	
	@GetMapping("/{idDomaine}")
	public List<CategorieResponse> getCategoriesByDomaineId(@PathVariable String idDomaine) {
	    return categorieService.getCategoriesByDomaineId(idDomaine);
	}
}
