package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.PlanConsultationEntity;

public interface PlanConsultationRepository extends JpaRepository<PlanConsultationEntity,Long>{
	
    @Query(value = "SELECT c.nom , c.prenom , pce.jour_debut , pce.date_jour_debut , pce.heure_debut , pce.heure_fin , ce.prix , c.francais , c.arabe , c.anglais , c.espagnol , c.id FROM plan_consultation_entity pce INNER JOIN consultation_entity ce on pce.consultation_id=ce.id inner join consultant_entity c on ce.consultant_id=c.id where pce.id= :idPlan", nativeQuery = true)
	String detailConsultationByIdPlan(
			@Param("idPlan") Long  idPlan);
    
    
}
