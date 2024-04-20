package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.RendezVousAccepteEntity;

public interface RendezVousAccepteRepository extends JpaRepository<RendezVousAccepteEntity, Long> {

	@Query(value = "SELECT r.id , c.nom_client , c.email_client , c.adresse_client , c.telephone , c.pays_client , c.ville_client , r.paiement , p.date_jour_debut , p.heure_debut , p.heure_fin , c.id_card_stripe , r.message , r.id_plan , c.num_card , ra.lien_meet from client_entity c inner join rendez_vous_entity r on c.id=r.id_client inner join plan_consultation_entity p on r.id_plan=p.id inner join consultant_entity cons on r.id_consultant=cons.id inner join rendez_vous_accepte_entity ra on r.id=ra.id_rendez_vous WHERE cons.id_consultant=:idConsultant AND r.accepte is true", nativeQuery = true)
	List<String> getRendezVousAccepterByIdConsultant(
			@Param("idConsultant") String  idConsultant);
}
