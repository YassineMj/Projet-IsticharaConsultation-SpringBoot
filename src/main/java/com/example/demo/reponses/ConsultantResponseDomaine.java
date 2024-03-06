package com.example.demo.reponses;

import java.util.List;

public class ConsultantResponseDomaine {
	
    private String idConsultant;
    private String nom;
    private String prenom;
    private List<String> specialisation;
    private String photoProfile;
	public String getIdConsultant() {
		return idConsultant;
	}
	public void setIdConsultant(String idConsultant) {
		this.idConsultant = idConsultant;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public List<String> getSpecialisation() {
		return specialisation;
	}
	public void setSpecialisation(List<String> specialisation) {
		this.specialisation = specialisation;
	}
	public String getPhotoProfile() {
		return photoProfile;
	}
	public void setPhotoProfile(String photoProfile) {
		this.photoProfile = photoProfile;
	}

}
