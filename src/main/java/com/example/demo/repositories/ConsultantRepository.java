package com.example.demo.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.ConsultantEntity;
import com.example.demo.entities.DomaineEntity;
import com.example.demo.reponses.ConsultantResponseDomaine;

public interface ConsultantRepository extends JpaRepository<ConsultantEntity, Long> {
	List<ConsultantEntity> findByDomaineIdDomaine(String idDomaine);
	ConsultantEntity findByIdConsultant(String idConsultant);
	Optional<ConsultantEntity> findByEmail(String email);
	boolean existsByEmail(String email);
	long countByDomaine(DomaineEntity domaine);
	
	// Requête 11 - TOTAL RENDEZ-VOUS PAR CONSULTANT
    @Query(value = "SELECT COUNT(*) FROM consultant_entity co " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE co.id = ?1", nativeQuery = true)
    long countTotalRvByConsultantId(long consultantId);

    // Requête 11 - RENDEZ-VOUS ACCEPTE PAR CONSULTANT
    @Query(value = "SELECT COUNT(*) FROM consultant_entity co " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE co.id = ?1 AND rv.accepte = true", nativeQuery = true)
    long countAccepteRvByConsultantId(long consultantId);

    // Requête 11 - RENDEZ-VOUS REFUSE PAR CONSULTANT
    @Query(value = "SELECT COUNT(*) FROM consultant_entity co " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE co.id = ?1 AND rv.refuse = true", nativeQuery = true)
    long countRefuseRvByConsultantId(long consultantId);

    // Requête 11 - RENDEZ-VOUS EN ATTENTE PAR CONSULTANT
    @Query(value = "SELECT COUNT(*) FROM consultant_entity co " +
            "INNER JOIN rendez_vous_entity rv ON co.id=rv.id_consultant " +
            "WHERE co.id = ?1 AND rv.refuse IS NULL AND rv.accepte IS NULL", nativeQuery = true)
    long countEnAttenteRvByConsultantId(long consultantId);
}