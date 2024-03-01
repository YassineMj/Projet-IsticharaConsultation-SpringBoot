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

    public List<DomaineEntity> getAllDomaines() {
        return domaineRepository.findAll();
    }
}
