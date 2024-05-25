package com.example.demo.requests;

public class ReclamationRequest {
	
	private String codeReclamation;
 	private String avis;
    private String commentaire;
    private boolean consultantPasParle;
    private boolean consultantPasParticipe;
    private boolean problemeVoix;
    private boolean mauvaiseQualiteVideo;
    private boolean manqueDeConnaissances;
    private boolean mauvaiseQualiteAudio;
    private boolean connexionInterrompue;
    
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
	public String getCodeReclamation() {
		return codeReclamation;
	}
	public void setCodeReclamation(String codeReclamation) {
		this.codeReclamation = codeReclamation;
	}
}
