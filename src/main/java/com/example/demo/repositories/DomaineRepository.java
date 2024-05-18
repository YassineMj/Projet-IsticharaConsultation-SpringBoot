package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.DomaineEntity;

public interface DomaineRepository extends JpaRepository<DomaineEntity, Long> {
	DomaineEntity findByIdDomaine(String idDomaine);
	
	// Requête 10 - TOTAL
    @Query(value = "SELECT COUNT(*) FROM domaine_entity d " +
            "INNER JOIN consultant_entity co ON d.id_table=co.fk_id_domaine " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE d.id_table = ?1", nativeQuery = true)
    long countTotalByDomainId(long domainId);

    // Requête 10 - ACCEPTE
    @Query(value = "SELECT COUNT(*) FROM domaine_entity d " +
            "INNER JOIN consultant_entity co ON d.id_table=co.fk_id_domaine " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE d.id_table = ?1 AND rv.accepte = true", nativeQuery = true)
    long countAccepteByDomainId(long domainId);

    // Requête 10 - REFUSE
    @Query(value = "SELECT COUNT(*) FROM domaine_entity d " +
            "INNER JOIN consultant_entity co ON d.id_table=co.fk_id_domaine " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE d.id_table = ?1 AND rv.refuse = true", nativeQuery = true)
    long countRefuseByDomainId(long domainId);

    // Requête 10 - EN ATTENTE
    @Query(value = "SELECT COUNT(*) FROM domaine_entity d " +
            "INNER JOIN consultant_entity co ON d.id_table=co.fk_id_domaine " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE d.id_table = ?1 AND rv.accepte IS NULL AND rv.refuse IS NULL", nativeQuery = true)
    long countEnAttenteByDomainId(long domainId);
    
    
 // Requête 12 - NOMBRE DE CONSULTATIONS PAR DOMAINE
    @Query(value = "SELECT d.nom_domaine AS nomDomaine, COUNT(con.id) AS nombreConsultations " +
            "FROM domaine_entity d " +
            "JOIN consultant_entity co ON d.id_table = co.fk_id_domaine " +
            "JOIN consultation_entity con ON co.id = con.consultant_id " +
            "GROUP BY d.nom_domaine", nativeQuery = true)
    List<Object[]> countConsultationsByDomaine();

    // Requête 12 - NOMBRE DE PLANS PAR DOMAINE
    @Query(value = "SELECT d.nom_domaine AS nomDomaine, COUNT(p.id) AS nombrePlans " +
            "FROM domaine_entity d " +
            "LEFT JOIN consultant_entity co ON d.id_table = co.fk_id_domaine " +
            "LEFT JOIN consultation_entity con ON co.id = con.consultant_id " +
            "LEFT JOIN plan_consultation_entity p ON con.id = p.consultation_id " +
            "GROUP BY d.nom_domaine", nativeQuery = true)
    List<Object[]> countPlansByDomaine();
}
