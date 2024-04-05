package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.RendezVousEntity;

public interface RendezVousRepository extends JpaRepository<RendezVousEntity, Long> {

}
