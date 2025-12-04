package com.example.gestion.enseignantservice.dto;

public class NoteRequest {

    private Long etudiantId;
    private Long moduleId;
    private Double valeur;
    private String type;

    // ---------- Constructeur vide obligatoire ----------
    public NoteRequest() {
    }

    // ---------- Getters et setters ----------
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
}
