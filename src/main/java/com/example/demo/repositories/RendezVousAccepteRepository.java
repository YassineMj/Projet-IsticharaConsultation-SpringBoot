package com.example.demo.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.entities.RendezVousAccepteEntity;

public interface RendezVousAccepteRepository extends JpaRepository<RendezVousAccepteEntity, Long> {

	@Query(value = "SELECT r.id , c.nom_client , c.email_client , c.adresse_client , c.telephone , c.pays_client , c.ville_client , r.paiement , p.date_jour_debut , p.heure_debut , p.heure_fin , c.id_card_stripe , r.message , r.id_plan , c.num_card , ra.lien_meet from client_entity c inner join rendez_vous_entity r on c.id=r.id_client inner join plan_consultation_entity p on r.id_plan=p.id inner join consultant_entity cons on r.id_consultant=cons.id inner join rendez_vous_accepte_entity ra on r.id=ra.id_rendez_vous WHERE cons.id_consultant=:idConsultant AND r.accepte is true", nativeQuery = true)
	List<String> getRendezVousAccepterByIdConsultant(
			@Param("idConsultant") String  idConsultant);
	
	@Query(value = "SELECT rv.id, rva.lien_meet, rv.paiement, pc.date_jour_debut, pc.heure_debut, pc.heure_fin, " +
            "c.nom_client, con.nom, con.prenom, cone.duree, cone.prix, d.nom_domaine " +
            "FROM rendez_vous_accepte_entity rva " +
            "INNER JOIN rendez_vous_entity rv ON rva.id_rendez_vous = rv.id " +
            "INNER JOIN plan_consultation_entity pc ON rv.id_plan = pc.id " +
            "INNER JOIN client_entity c ON rv.id_client = c.id " +
            "INNER JOIN consultant_entity con ON rv.id_consultant = con.id " +
            "INNER JOIN consultation_entity cone ON con.id = cone.consultant_id " +
            "INNER JOIN domaine_entity d ON con.fk_id_domaine = d.id_table " +
            "WHERE rva.id_reclamation = :reclamationId", nativeQuery = true)
	Map<String, Object> findRendezVousByReclamationId(@Param("reclamationId") String reclamationId);

	RendezVousAccepteEntity findByRendezVousId(long idRendezVous);

}
