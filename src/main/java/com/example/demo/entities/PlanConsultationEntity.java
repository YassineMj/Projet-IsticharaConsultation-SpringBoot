package com.example.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"consultation_id", "jourDebut", "dateJourDebut","jourFin", "dateJourFin","heureDebut","heureFin"}),
})
public class PlanConsultationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @JsonBackReference
    private ConsultationEntity consultation;

    private String jourDebut;
    private LocalDate dateJourDebut;
    private String jourFin;
    private LocalDate dateJourFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ConsultationEntity getConsultation() {
		return consultation;
	}
	public void setConsultation(ConsultationEntity consultation) {
		this.consultation = consultation;
	}
	public String getJourDebut() {
		return jourDebut;
	}
	public void setJourDebut(String jourDebut) {
		this.jourDebut = jourDebut;
	}
	public LocalDate getDateJourDebut() {
		return dateJourDebut;
	}
	public void setDateJourDebut(LocalDate dateJourDebut) {
		this.dateJourDebut = dateJourDebut;
	}
	public String getJourFin() {
		return jourFin;
	}
	public void setJourFin(String jourFin) {
		this.jourFin = jourFin;
	}
	public LocalDate getDateJourFin() {
		return dateJourFin;
	}
	public void setDateJourFin(LocalDate dateJourFin) {
		this.dateJourFin = dateJourFin;
	}
	public LocalTime getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}
	public LocalTime getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}
    

}