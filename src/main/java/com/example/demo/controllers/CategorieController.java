package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.CategorieEntity;
import com.example.demo.reponses.CategorieResponse;
import com.example.demo.requests.CategorieRequest;
import com.example.demo.services.CategorieService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/IsticharaConsultation/api/categories")
public class CategorieController {
	
	@Autowired
	CategorieService categorieService;
	
	//*
    @PostMapping("add-category/{idDomaine}")
    public ResponseEntity<CategorieEntity> createCategorie(@PathVariable String idDomaine, @RequestBody CategorieRequest categorieRequest) {
        CategorieEntity createdCategorie = categorieService.createCategorie(idDomaine, categorieRequest);
        if (createdCategorie != null) {
            return ResponseEntity.ok(createdCategorie);
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }
    
    //*
    @GetMapping("get-all-category")
    public ResponseEntity<List<CategorieEntity>> getAllCategories() {
        List<CategorieEntity> Categories = categorieService.getAllCategories();
        if (Categories != null) {
            return ResponseEntity.ok(Categories);
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }
    
    //*
    @GetMapping("get-category/{idCategory}")
    public ResponseEntity<CategorieEntity> getCatgorie(@PathVariable String idCategory) {
        CategorieEntity Categorie = categorieService.getCategorie(idCategory);
        if (Categorie != null) {
            return ResponseEntity.ok(Categorie);
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }
    
    //*
    @PutMapping("put-category/{idCategory}/{idDomaine}")
    public ResponseEntity<CategorieEntity> getCatgorie(@PathVariable String idCategory,@PathVariable String idDomaine,@RequestBody CategorieRequest categorieRequest) {
        CategorieEntity Categorie = categorieService.updateCategorie(idCategory,idDomaine,categorieRequest);
        if (Categorie != null) {
            return ResponseEntity.ok(Categorie);
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }
    
    //*
    @DeleteMapping("delete-category/{idCategorie}")
    public ResponseEntity<Map<String, Object>> deleteCategorie(@PathVariable String idCategorie) {
        Map<String, Object> response = new HashMap();
        try {
            categorieService.deleteCategorie(idCategorie);
            response.put("message", "Categorie supprimé avec succès");
            response.put("ok", true);
        } catch (Exception e) {
            response.put("message", "Erreur lors de la suppression du Categorie");
            response.put("ok", false);
        }
        return ResponseEntity.ok(response);
    }
    
	@GetMapping("/{idDomaine}")
	public List<CategorieResponse> getCategoriesByDomaineId(@PathVariable String idDomaine) {
	    return categorieService.getCategoriesByDomaineId(idDomaine);
	}
}
