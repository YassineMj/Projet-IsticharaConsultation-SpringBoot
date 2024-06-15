package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.requests.DomaineRequest;
import com.example.demo.services.DomaineService;


@CrossOrigin(origins = {"http://localhost:4200", "https://yassinemj.github.io"})
@RestController
@RequestMapping("IsticharaConsultation/api/domaines")
public class DomaineController {
    @Autowired
    private DomaineService domaineService;

    //*
    @PostMapping("add-domaine")
    public ResponseEntity<DomaineEntity> createDomaine(@RequestBody DomaineRequest domaine) {
        DomaineEntity createdDomaine = domaineService.createDomaine(domaine);
        return ResponseEntity.ok(createdDomaine);
    }
    
    //*
    @GetMapping("get-all-domaines")
    public ResponseEntity<List<DomaineEntity>> getAllDomaine() {
        List<DomaineEntity> domaines = domaineService.getAllDomaines();
        return ResponseEntity.ok(domaines);
    }
    
    //*
    @GetMapping("get-domaine/{idDomaine}")
    public ResponseEntity<DomaineEntity> getDomaineById(@PathVariable String idDomaine) {
        DomaineEntity domaine = domaineService.getDomaineById(idDomaine);
        return ResponseEntity.ok(domaine);
    }
    
    //*
    @PutMapping("put-domaine/{idDomaine}")
    public ResponseEntity<DomaineEntity> updateDomaine(@RequestBody DomaineRequest domaineRequest ,@PathVariable String idDomaine) {
        DomaineEntity domaine = domaineService.updateDomaine(idDomaine , domaineRequest);
        return ResponseEntity.ok(domaine);
    }
    
    //*
    @DeleteMapping("delete-domaine/{idDomaine}")
    public ResponseEntity<Map<String, Object>> deleteDomaine(@PathVariable String idDomaine) {
        Map<String, Object> response = new HashMap();
        try {
            domaineService.deleteDomaine(idDomaine);
            response.put("message", "Domaine supprimé avec succès");
            response.put("ok", true);
        } catch (Exception e) {
            response.put("message", "Erreur lors de la suppression du domaine");
            response.put("ok", false);
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<DomaineEntity>> getAllDomaines() {
        List<DomaineEntity> domaines = domaineService.getAllDomaines();
        return ResponseEntity.ok(domaines);
    }
    
    
    @GetMapping("/page")
    public ResponseEntity<Page<DomaineEntity>> getDomainesPage(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size) {
        Page<DomaineEntity> domainesPage = domaineService.getDomainesPage(page, size);
        return ResponseEntity.ok(domainesPage);
    }

}
