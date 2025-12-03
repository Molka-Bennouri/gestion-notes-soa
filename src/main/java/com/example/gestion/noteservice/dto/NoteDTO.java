package com.example.gestion.noteservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO pour transférer les données d'une note
 */
public class NoteDTO {
    private Integer idNote;
    private BigDecimal valeur;
    private String type;
    private LocalDate dateNote;
    private Boolean publier;
    private Integer etudiantId;
    private String etudiantNom;
    private String etudiantPrenom;
    private Integer moduleId;
    private String moduleNom;

    // Constructeurs
    public NoteDTO() {
    }

    public NoteDTO(Integer idNote, BigDecimal valeur, String type, LocalDate dateNote, Boolean publier,
            Integer etudiantId, String etudiantNom, String etudiantPrenom,
            Integer moduleId, String moduleNom) {
        this.idNote = idNote;
        this.valeur = valeur;
        this.type = type;
        this.dateNote = dateNote;
        this.publier = publier;
        this.etudiantId = etudiantId;
        this.etudiantNom = etudiantNom;
        this.etudiantPrenom = etudiantPrenom;
        this.moduleId = moduleId;
        this.moduleNom = moduleNom;
    }

    // Getters et Setters
    public Integer getIdNote() {
        return idNote;
    }

    public void setIdNote(Integer idNote) {
        this.idNote = idNote;
    }

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

    public String getEtudiantNom() {
        return etudiantNom;
    }

    public void setEtudiantNom(String etudiantNom) {
        this.etudiantNom = etudiantNom;
    }

    public String getEtudiantPrenom() {
        return etudiantPrenom;
    }

    public void setEtudiantPrenom(String etudiantPrenom) {
        this.etudiantPrenom = etudiantPrenom;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleNom() {
        return moduleNom;
    }

    public void setModuleNom(String moduleNom) {
        this.moduleNom = moduleNom;
    }
}
