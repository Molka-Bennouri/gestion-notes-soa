package com.example.gestion.enseignantservice.entity;

import jakarta.persistence.*;
import com.example.gestion.common.entity.Utilisateur;

/**
 * Entité Enseignant - Hérite de Utilisateur
 * Utilise une stratégie d'héritage par table jointe (Joined Table Inheritance)
 * La clé primaire est également une clé étrangère vers la table utilisateur
 */
@Entity
@Table(name = "enseignant")
public class Enseignant {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Utilisateur utilisateur;

    @Column(name = "specialite")
    private String specialite;

    // Constructeurs
    public Enseignant() {
    }

    public Enseignant(Utilisateur utilisateur, String specialite) {
        this.utilisateur = utilisateur;
        this.specialite = specialite;
    }

    // Getters et Setters
    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
