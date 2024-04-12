package com.example.demo.requests;

public class DemandeRequest {

    private String idClientStripe;
    private String idCardStripe;
    private String nomClient;
    private String emailClient;
    private String villeClient;
    private String paysClient;
    private String adresseClient;
    private Long idConsultant;
    private Long idPlan;
    private double prixTotal;
    private String message;
    private String telephone;



    // Constructeur par défaut
    public DemandeRequest() {}

    // Getters et setters
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

    public Long getIdConsultant() {
        return idConsultant;
    }

    public void setIdConsultant(Long idConsultant) {
        this.idConsultant = idConsultant;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
