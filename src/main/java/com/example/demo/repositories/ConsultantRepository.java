package com.example.demo.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.reponses.ConsultantResponseDomaine;

public interface ConsultantRepository extends JpaRepository<ConsultantEntity, Long> {
	List<ConsultantEntity> findByDomaineIdDomaine(String idDomaine);
	ConsultantEntity findByIdConsultant(String idConsultant);
	Optional<ConsultantEntity> findByEmail(String email);
	boolean existsByEmail(String email);
}