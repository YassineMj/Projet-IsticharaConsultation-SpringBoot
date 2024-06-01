package com.example.demo.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.aspectj.weaver.tools.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.PlanConsultationEntity;

public interface PlanConsultationRepository extends JpaRepository<PlanConsultationEntity,Long>{
	
    @Query(value = "SELECT c.nom , c.prenom , pce.jour_debut , pce.date_jour_debut , pce.heure_debut , pce.heure_fin , ce.prix , c.francais , c.arabe , c.anglais , c.espagnol , c.id FROM plan_consultation_entity pce INNER JOIN consultation_entity ce on pce.consultation_id=ce.id inner join consultant_entity c on ce.consultant_id=c.id where pce.id= :idPlan", nativeQuery = true)
	String detailConsultationByIdPlan(
			@Param("idPlan") Long  idPlan);
    
    @Query(value = "SELECT COUNT(*) FROM consultant_entity co " +
            "INNER JOIN consultation_entity c ON co.id=c.consultant_id " +
            "INNER JOIN plan_consultation_entity p ON c.id=p.consultation_id " +
            "WHERE co.id = ?1 " +
            "GROUP BY c.id_consultation", nativeQuery = true)
    long countPlanByConsultantId(long consultantId);   
    
    @Query("SELECT MONTH(p.dateJourDebut) AS month, COUNT(p) AS count " +
            "FROM PlanConsultationEntity p " +
            "JOIN p.consultation c " +
            "JOIN c.consultant consultant " +
            "WHERE consultant.idConsultant = :idConsultant AND YEAR(p.dateJourDebut) = :currentYear " +
            "GROUP BY MONTH(p.dateJourDebut)")
     List<Map<String, Object>> countPlansByMonthForYear(@Param("idConsultant") String idConsultant, @Param("currentYear") int currentYear);

    
    @Query(value = "SELECT p.id AS planId, p.date_jour_debut AS dateJourDebut, p.date_jour_fin AS dateJourFin, " +
            "p.heure_debut AS heureDebut, p.heure_fin AS heureFin, p.jour_debut AS jourDebut, p.jour_fin AS jourFin, " +
            "r.accepte AS accepte, r.refuse AS refuse " +
            "FROM plan_consultation_entity p " +
            "LEFT JOIN rendez_vous_entity r ON p.id = r.id_plan " +
            "INNER JOIN consultant_entity c ON r.id_consultant = c.id " +
            "WHERE c.id_consultant = :idConsultant", nativeQuery = true)
    List<Map<String, Object>> findPlansWithRendezVousByConsultant(@Param("idConsultant") String idConsultant);



}
