package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.services.DomaineService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("IsticharaConsultation/api/domaines")
public class DomaineController {
    @Autowired
    private DomaineService domaineService;

    @PostMapping
    public ResponseEntity<DomaineEntity> createDomaine(@RequestBody DomaineEntity domaine) {
        DomaineEntity createdDomaine = domaineService.createDomaine(domaine);
        return ResponseEntity.ok(createdDomaine);
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
