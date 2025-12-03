package com.example.gestion.etudiantservice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.example.gestion.common.entity.Utilisateur;

/**
 * Entité Etudiant - Hérite de Utilisateur
 * Utilise une stratégie d'héritage par table jointe (Joined Table Inheritance)
 * La clé primaire est également une clé étrangère vers la table utilisateur
 */
@Entity
@Table(name = "etudiant")
public class Etudiant {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Utilisateur utilisateur;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "niveau")
    private String niveau;

    // Constructeurs
    public Etudiant() {
    }

    public Etudiant(Utilisateur utilisateur, LocalDate dateNaissance, String niveau) {
        this.utilisateur = utilisateur;
        this.dateNaissance = dateNaissance;
        this.niveau = niveau;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
