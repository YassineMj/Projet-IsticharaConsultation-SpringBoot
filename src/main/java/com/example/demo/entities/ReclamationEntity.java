package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class ReclamationEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codeReclamation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private ClientEntity client;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    @JsonIgnore
    private ConsultantEntity consultant;
    
    
    private String avis;
    private String commentaire;
    private boolean consultantPasParle;
    private boolean consultantPasParticipe;
    private boolean problemeVoix;
    private boolean mauvaiseQualiteVideo;
    private boolean manqueDeConnaissances;
    private boolean mauvaiseQualiteAudio;
    private boolean connexionInterrompue;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeReclamation() {
        return codeReclamation;
    }

    public void setCodeReclamation(String codeReclamation) {
        this.codeReclamation = codeReclamation;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public boolean isConsultantPasParle() {
        return consultantPasParle;
    }

    public void setConsultantPasParle(boolean consultantPasParle) {
        this.consultantPasParle = consultantPasParle;
    }

    public boolean isConsultantPasParticipe() {
        return consultantPasParticipe;
    }

    public void setConsultantPasParticipe(boolean consultantPasParticipe) {
        this.consultantPasParticipe = consultantPasParticipe;
    }

    public boolean isProblemeVoix() {
        return problemeVoix;
    }

    public void setProblemeVoix(boolean problemeVoix) {
        this.problemeVoix = problemeVoix;
    }

    public boolean isMauvaiseQualiteVideo() {
        return mauvaiseQualiteVideo;
    }

    public void setMauvaiseQualiteVideo(boolean mauvaiseQualiteVideo) {
        this.mauvaiseQualiteVideo = mauvaiseQualiteVideo;
    }

    public boolean isManqueDeConnaissances() {
        return manqueDeConnaissances;
    }

    public void setManqueDeConnaissances(boolean manqueDeConnaissances) {
        this.manqueDeConnaissances = manqueDeConnaissances;
    }

    public boolean isMauvaiseQualiteAudio() {
        return mauvaiseQualiteAudio;
    }

    public void setMauvaiseQualiteAudio(boolean mauvaiseQualiteAudio) {
        this.mauvaiseQualiteAudio = mauvaiseQualiteAudio;
    }

    public boolean isConnexionInterrompue() {
        return connexionInterrompue;
    }

    public void setConnexionInterrompue(boolean connexionInterrompue) {
        this.connexionInterrompue = connexionInterrompue;
    }

    public ConsultantEntity getConsultant() {
		return consultant;
	}

	public void setConsultant(ConsultantEntity consultant) {
		this.consultant = consultant;
	}

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	
}
