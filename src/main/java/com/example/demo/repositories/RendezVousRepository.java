package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.RendezVousEntity;

public interface RendezVousRepository extends JpaRepository<RendezVousEntity, Long> {

	/*@Query(value = "SELECT c.nom_client , c.email_client , c.adresse_client , c.telephone , c.pays_client , c.ville_client , r.paiement , p.date_jour_debut , p.heure_debut , p.heure_fin , c.id_card_stripe , r.message from client_entity c inner join rendez_vous_entity r on c.id=r.id_client inner join plan_consultation_entity p on r.id_plan=p.id WHERE r.id_consultant=:idConsultant", nativeQuery = true)
	String getRendezVousByIdConsultant(
			@Param("idConsultant") Long  idConsultant);*/
}
