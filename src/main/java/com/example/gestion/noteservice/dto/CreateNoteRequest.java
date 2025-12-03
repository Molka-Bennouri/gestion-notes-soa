package com.example.gestion.noteservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO pour la création d'une nouvelle note
 */
public class CreateNoteRequest {
    private BigDecimal valeur;
    private String type;
    private LocalDate dateNote;
    private Boolean publier;
    private Integer etudiantId;
    private Integer moduleId;

    // Constructeurs
    public CreateNoteRequest() {
    }

    public CreateNoteRequest(BigDecimal valeur, String type, LocalDate dateNote, Boolean publier,
            Integer etudiantId, Integer moduleId) {
        this.valeur = valeur;
        this.type = type;
        this.dateNote = dateNote;
        this.publier = publier;
        this.etudiantId = etudiantId;
        this.moduleId = moduleId;
    }

    // Getters et Setters
    public BigDecimal getValeur() {
        return valeur;
    }

    public void setValeur(BigDecimal valeur) {
        this.valeur = valeur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateNote() {
        return dateNote;
    }

    public void setDateNote(LocalDate dateNote) {
        this.dateNote = dateNote;
    }

    public Boolean getPublier() {
        return publier;
    }

    public void setPublier(Boolean publier) {
        this.publier = publier;
    }

    public Integer getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Integer etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}
