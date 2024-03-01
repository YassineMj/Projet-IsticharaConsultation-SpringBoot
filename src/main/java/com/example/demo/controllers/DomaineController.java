package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.DomaineEntity;
import com.example.demo.services.DomaineService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("IsticharaConsultation/api/domaines")
public class DomaineController {
    @Autowired
    private DomaineService domaineService;

    @GetMapping
    public List<DomaineEntity> getAllDomaines() {
        return domaineService.getAllDomaines();
    }
}
