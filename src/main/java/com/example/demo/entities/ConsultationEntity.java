package com.example.demo.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ConsultationEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idConsultation")
    private String idConsultation;
    
    @Column(length = 5000)
    private String description;
    private float prix;
    private int duree;
    
    @ManyToOne
    private ConsultantEntity consultant;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PlanConsultationEntity> plans;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdConsultation() {
		return idConsultation;
	}

	public void setIdConsultation(String idConsultation) {
		this.idConsultation = idConsultation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public ConsultantEntity getConsultant() {
		return consultant;
	}

	public void setConsultant(ConsultantEntity consultant) {
		this.consultant = consultant;
	}

	public List<PlanConsultationEntity> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanConsultationEntity> plans) {
		this.plans = plans;
	}
    
}
