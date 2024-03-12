package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ConsultationEntity;
import com.example.demo.entities.PlanConsultationEntity;
import com.example.demo.repositories.ConsultationRepository;
import com.example.demo.repositories.PlanConsultationRepository;
import com.example.demo.requests.PlanConsultationRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlanConsultationService {

    @Autowired
    private PlanConsultationRepository planConsultationRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public PlanConsultationEntity ajouterPlanConsultation(PlanConsultationRequest planRequest) {
        // Vérifie si la consultation existe
        ConsultationEntity consultation = consultationRepository.findByIdConsultation(planRequest.getConsultationId());
        PlanConsultationEntity planConsultation = new PlanConsultationEntity();
        planConsultation.setConsultation(consultation);
        planConsultation.setJourDebut(planRequest.getJourDebut());
        planConsultation.setDateJourDebut(planRequest.getDateJourDebut());
        planConsultation.setJourFin(planRequest.getJourFin());
        planConsultation.setDateJourFin(planRequest.getDateJourFin());
        planConsultation.setHeureDebut(planRequest.getHeureDebut());
        planConsultation.setHeureFin(planRequest.getHeureFin());

        return planConsultationRepository.save(planConsultation);
    }
    
    public void supprimerPlanConsultation(Long id) {
        planConsultationRepository.deleteById(id);
    }
}

