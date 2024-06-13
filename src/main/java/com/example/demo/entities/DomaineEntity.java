package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
public class DomaineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTable;

    @Column(name = "idDomaine")
    private String idDomaine;

    @Column(name = "nomDomaine")
    private String nomDomaine;

    @Column(name = "descriptionDomaine" , length = 5000)
    private String descriptionDomaine;

    @Column(name = "pathImage")
    private String pathImage;

	public Long getIdTable() {
		return idTable;
	}

	public void setIdTable(Long idTable) {
		this.idTable = idTable;
	}

	public String getNomDomaine() {
		return nomDomaine;
	}

	public void setNomDomaine(String nomDomaine) {
		this.nomDomaine = nomDomaine;
	}

	public String getDescriptionDomaine() {
		return descriptionDomaine;
	}

	public void setDescriptionDomaine(String descriptionDomaine) {
		this.descriptionDomaine = descriptionDomaine;
	}

	public String getPathImage() {
		return pathImage;
	}

	public void setPathImage(String pathImage) {
		this.pathImage = pathImage;
	}

	public String getIdDomaine() {
		return idDomaine;
	}

	public void setIdDomaine(String idDomaine) {
		this.idDomaine = idDomaine;
	}
}
