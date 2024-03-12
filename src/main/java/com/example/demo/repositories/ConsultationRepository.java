package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.ConsultationEntity;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long>  {

	ConsultationEntity findByConsultantIdConsultant(String idConsultation);

	ConsultationEntity findByIdConsultation(String consultationId);

}
