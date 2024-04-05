package com.example.demo.entities;

import java.util.Optional;

import jakarta.persistence.*;

@Entity
public class RendezVousEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "id_consultant")
    private ConsultantEntity consultant;


    private String paiement;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private PlanConsultationEntity plan;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public ConsultantEntity getConsultant() {
        return consultant;
    }

    public void setConsultant(ConsultantEntity consultant) {
        this.consultant = consultant;
    }

    public String getPaiement() {
        return paiement;
    }

    public void setPaiement(String paiement) {
        this.paiement = paiement;
    }

    public PlanConsultationEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanConsultationEntity plan) {
        this.plan = plan;
    }
}
