package com.example.gestion.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_utilisateur")
    private TypeUtilisateur typeUtilisateur;

    // Constructeurs
    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, TypeUtilisateur typeUtilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeUtilisateur = typeUtilisateur;
    }

    // Getters et Setters
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getMotDePasse() {return motDePasse;}
    public void setMotDePasse(String motDePasse) {this.motDePasse = motDePasse;}

    public TypeUtilisateur getTypeUtilisateur() {return typeUtilisateur;}
    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {this.typeUtilisateur = typeUtilisateur;}

    // Utilisez les mêmes valeurs que dans votre base MySQL
    public enum TypeUtilisateur {
        Etudiant,
        Enseignant,
        Admin
    }
}