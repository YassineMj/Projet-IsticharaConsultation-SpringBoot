package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClientEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String idClientStripe;
    
    private String idCardStripe;
    
    private String numCard;
    
    private String nomClient;
    
    private String emailClient;
    
    private String telephone;

    
    private String villeClient;
    
    private String paysClient;
    
    private String adresseClient;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdClientStripe() {
        return idClientStripe;
    }

    public void setIdClientStripe(String idClientStripe) {
        this.idClientStripe = idClientStripe;
    }

    public String getIdCardStripe() {
        return idCardStripe;
    }

    public void setIdCardStripe(String idCardStripe) {
        this.idCardStripe = idCardStripe;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getVilleClient() {
        return villeClient;
    }

    public void setVilleClient(String villeClient) {
        this.villeClient = villeClient;
    }

    public String getPaysClient() {
        return paysClient;
    }

    public void setPaysClient(String paysClient) {
        this.paysClient = paysClient;
    }
    
    public String getAdresseClient() {
        return adresseClient;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNumCard() {
		return numCard;
	}

	public void setNumCard(String numCard) {
		this.numCard = numCard;
	}
}