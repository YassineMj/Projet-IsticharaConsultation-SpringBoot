package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RendezVousAccepteEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_rendez_vous")
    private RendezVousEntity rendezVous;
    
    private String lienMeet;
    
    private String idReclamation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RendezVousEntity getRendezVous() {
		return rendezVous;
	}

	public void setRendezVous(RendezVousEntity rendezVous) {
		this.rendezVous = rendezVous;
	}

	public String getLienMeet() {
		return lienMeet;
	}

	public void setLienMeet(String lienMeet) {
		this.lienMeet = lienMeet;
	}

	public String getIdReclamation() {
		return idReclamation;
	}

	public void setIdReclamation(String idReclamation) {
		this.idReclamation = idReclamation;
	}
}
