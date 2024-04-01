package com.example.demo.services;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.ConsultationEntity;
import com.example.demo.methodeStatic.IdGenerator;
import com.example.demo.repositories.ConsultantRepository;
import com.example.demo.repositories.ConsultationRepository;
import com.example.demo.requests.ConsultationRequest;

import ch.qos.logback.classic.pattern.Util;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;
    
    @Autowired
    private ConsultantRepository consultatantRepository;

    public ResponseEntity<ConsultationEntity> ajouterConsultation(String idConsultant,ConsultationRequest consultation) {
        try {
            ConsultantEntity consultant = consultatantRepository.findByIdConsultant(idConsultant);
            if (consultant == null) {
                throw new Exception("Consultant introuvable avec l'ID : " + idConsultant);
            }

            ConsultationEntity consultationEntity = new ConsultationEntity();
            BeanUtils.copyProperties(consultation, consultationEntity);
            consultationEntity.setConsultant(consultant);
            consultationEntity.setIdConsultation(IdGenerator.generateId().toString());

            return new ResponseEntity<>(consultationRepository.save(consultationEntity), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    public ConsultationEntity getConsultationByIdConsultant(String idConsultant) {
        return consultationRepository.findByConsultantIdConsultant(idConsultant);
    }

	public ConsultationEntity getConsultationByIdConsultation(String idConsultation) {
		// TODO Auto-generated method stub
		return consultationRepository.findByIdConsultation(idConsultation);
	}
	
	public Page<ConsultationEntity> getConsultationsByIdDomaineAndSpecialisation(String idDomaine, String specialisation, org.springframework.data.domain.Pageable pageable) {
	    Page<ConsultationEntity> pageConsultations = consultationRepository.findByConsultantDomaineIdDomaineAndConsultantSpecialisation(idDomaine, specialisation, pageable);
	    
	    pageConsultations.getContent().forEach(c -> {
	        java.nio.file.Path path = Paths.get(c.getConsultant().getPhotoProfile());
	        c.getConsultant().setPhotoProfile(path.getFileName().toString());
	    });
	    
	    return pageConsultations;
	}

}
