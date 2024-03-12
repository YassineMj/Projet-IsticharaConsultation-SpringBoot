package com.example.demo.requests;

import java.time.LocalDate;
import java.time.LocalTime;

public class PlanConsultationRequest {

    private String consultationId;
    private String jourDebut;
    private LocalDate dateJourDebut;
    private String jourFin;
    private LocalDate dateJourFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    // Les getters et setters

    public String getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
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