package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_complet")
    private String nomComplet;

    private String description;

    private String email;

    private String telephone;

    private String pays;

    private String adresse;

    private String travail;
    
    private String motDepasse;
    
    private String idFireBase;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTravail() {
        return travail;
    }

    public void setTravail(String travail) {
        this.travail = travail;
    }

	public String getMotDepasse() {
		return motDepasse;
	}

	public void setMotDepasse(String motDepasse) {
		this.motDepasse = motDepasse;
	}

	public String getIdFireBase() {
		return idFireBase;
	}

	public void setIdFireBase(String idFireBase) {
		this.idFireBase = idFireBase;
	}
}