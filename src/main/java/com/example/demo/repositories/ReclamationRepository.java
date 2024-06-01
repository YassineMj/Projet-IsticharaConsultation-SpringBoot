package com.example.demo.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.ReclamationEntity;

public interface ReclamationRepository extends JpaRepository<ReclamationEntity, Long> {

    @Query(value = "SELECT rec.code_reclamation, cl.nom_client , rec.avis , d.nom_domaine , c.nom , c.prenom ,rec.manque_de_connaissances,rec.mauvaise_qualite_audio,rec.connexion_interrompue,rec.consultant_pas_parle,rec.consultant_pas_participe , rec.mauvaise_qualite_video,rec.probleme_voix\r\n"
    		+ "FROM reclamation_entity rec INNER JOIN client_entity cl on rec.client_id=cl.id INNER JOIN consultant_entity c on rec.consultant_id=c.id inner JOIN domaine_entity d on c.fk_id_domaine=d.id_table\r\n"
    		+ "WHERE rec.code_reclamation in (SELECT ra.id_reclamation FROM rendez_vous_accepte_entity ra ) \r\n"
    		+ "ORDER BY c.nom , c.prenom", nativeQuery = true)
     List<Map<String, String>> findAllReclamationsOrderedByConsultantName();
    
    @Query("SELECT " +
            "SUM(CASE WHEN r.consultantPasParle = false AND r.consultantPasParticipe = false AND r.problemeVoix = false AND r.mauvaiseQualiteVideo = false AND r.manqueDeConnaissances = false AND r.mauvaiseQualiteAudio = false AND r.connexionInterrompue = false THEN 1 ELSE 0 END) AS favCount, " +
            "SUM(CASE WHEN r.consultantPasParle = true OR r.consultantPasParticipe = true OR r.problemeVoix = true OR r.mauvaiseQualiteVideo = true OR r.manqueDeConnaissances = true OR r.mauvaiseQualiteAudio = true OR r.connexionInterrompue = true THEN 1 ELSE 0 END) AS defavCount " +
            "FROM ReclamationEntity r WHERE r.consultant.idConsultant = :idConsultant")
     Map<String, Long> countFavAndDefavByConsultantId(@Param("idConsultant") String idConsultant);

}
