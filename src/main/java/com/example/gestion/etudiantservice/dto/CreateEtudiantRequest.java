package com.example.gestion.etudiantservice.dto;

import java.time.LocalDate;

/**
 * DTO pour la création d'un nouvel étudiant
 */
public class CreateEtudiantRequest {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private LocalDate dateNaissance;
    private String niveau;

    // Constructeurs
    public CreateEtudiantRequest() {
    }

    public CreateEtudiantRequest(String nom, String prenom, String email, String motDePasse,
            LocalDate dateNaissance, String niveau) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
        this.niveau = niveau;
    }

    // Getters et Setters
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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
