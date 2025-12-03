package com.example.gestion.common.dto;

import lombok.Data;

@Data
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String type;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}
