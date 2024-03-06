package com.example.demo.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ConsultantRequest {

	private String idDomaine;
	private List<String> formations;
    private List<String> educations;
    private List<String> experiencesPro;
    private List<String> specialisation;
    private String descriptionProfile;
    private boolean francais;
    private boolean anglais;
    private boolean arabe;
    private boolean espagnol;
    private String nom;
    private String prenom;
    private String pays;
    private String ville;
    private String email;
    private String motDePasse;
    private String numeroTelephone;
    private String cin;
    private String adresse;
    private String banque;
    private String rib;
    private String photoProfile;
    

	public List<String> getFormations() {
		return formations;
	}
	public void setFormations(List<String> formations) {
		this.formations = formations;
	}
	public List<String> getEducations() {
		return educations;
	}
	public void setEducations(List<String> educations) {
		this.educations = educations;
	}
	public List<String> getExperiencesPro() {
		return experiencesPro;
	}
	public void setExperiencesPro(List<String> experiencesPro) {
		this.experiencesPro = experiencesPro;
	}
	public List<String> getSpecialisation() {
		return specialisation;
	}
	public void setSpecialisation(List<String> specialisation) {
		this.specialisation = specialisation;
	}
	public String getDescriptionProfile() {
		return descriptionProfile;
	}
	public void setDescriptionProfile(String descriptionProfile) {
		this.descriptionProfile = descriptionProfile;
	}
	public boolean isFrancais() {
		return francais;
	}
	public void setFrancais(boolean francais) {
		this.francais = francais;
	}
	public boolean isAnglais() {
		return anglais;
	}
	public void setAnglais(boolean anglais) {
		this.anglais = anglais;
	}
	public boolean isArabe() {
		return arabe;
	}
	public void setArabe(boolean arabe) {
		this.arabe = arabe;
	}
	public boolean isEspagnol() {
		return espagnol;
	}
	public void setEspagnol(boolean espagnol) {
		this.espagnol = espagnol;
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
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getNumeroTelephone() {
		return numeroTelephone;
	}
	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}
	public String  getPhotoProfile() {
		return photoProfile;
	}
	public void setPhotoProfile(String  photoProfile) {
		this.photoProfile = photoProfile;
	}
	public String getIdDomaine() {
		return idDomaine;
	}
	public void setIdDomaine(String idDomaine) {
		this.idDomaine = idDomaine;
	}
	public String getBanque() {
		return banque;
	}
	public void setBanque(String banque) {
		this.banque = banque;
	}

}

