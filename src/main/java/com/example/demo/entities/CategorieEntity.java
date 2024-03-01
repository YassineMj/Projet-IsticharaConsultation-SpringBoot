package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
public class CategorieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTable;

    @Column(name = "idCategorie")
    private String idCategorie;

    @ManyToOne
    @JoinColumn(name = "fk_id_domaine")
    private DomaineEntity domaine;

    @Column(name = "nomCategorie")
    private String nomCategorie;

    @Column(name = "descriptionCategorie")
    private String descriptionCategorie;

	public String getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(String idCategorie) {
		this.idCategorie = idCategorie;
	}

	public DomaineEntity getDomaine() {
		return domaine;
	}

	public void setDomaine(DomaineEntity domaine) {
		this.domaine = domaine;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	public String getDescriptionCategorie() {
		return descriptionCategorie;
	}

	public void setDescriptionCategorie(String descriptionCategorie) {
		this.descriptionCategorie = descriptionCategorie;
	}
    
}
