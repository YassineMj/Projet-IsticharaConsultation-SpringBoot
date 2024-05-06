package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.DemandeCompteEntity;

public interface DemandeCompteRepository extends JpaRepository<DemandeCompteEntity, Long>{
	
	boolean existsByEmail(String email);

	DemandeCompteEntity findByIdDemande(String idDemande);
}
