package com.example.gestion.enseignantservice.dto;

public class NoteResponse {

    private Long id;
    private Double valeur;
    private String type;
    private Long etudiantId;
    private Long moduleId;
    private boolean publier;

    // -------- Constructeur vide obligatoire pour XML et JSON ----------
    public NoteResponse() {
    }

    // -------- Constructeur complet ----------
    public NoteResponse(Long id, Double valeur, String type, Long etudiantId, Long moduleId, boolean publier) {
        this.id = id;
        this.valeur = valeur;
        this.type = type;
        this.etudiantId = etudiantId;
        this.moduleId = moduleId;
        this.publier = publier;
    }

    // -------- Getters & Setters ----------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValeur() {
        return valeur;
    }

    public void setValeur(Double valeur) {
        this.valeur = valeur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isPublier() {
        return publier;
    }

    public void setPublier(boolean publier) {
        this.publier = publier;
    }
}
