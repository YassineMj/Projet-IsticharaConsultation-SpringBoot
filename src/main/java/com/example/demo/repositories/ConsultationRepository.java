package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.ConsultationEntity;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long>  {

	ConsultationEntity findByConsultantIdConsultant(String idConsultation);

	ConsultationEntity findByIdConsultation(String consultationId);

    Page<ConsultationEntity> findByConsultantDomaineIdDomaineAndConsultantSpecialisation(String idDomaine, String specialisation, org.springframework.data.domain.Pageable pageable);

}
