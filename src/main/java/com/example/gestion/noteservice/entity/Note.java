package com.example.gestion.noteservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.gestion.etudiantservice.entity.Etudiant;
import com.example.gestion.moduleservice.entity.Module;

/**
 * Entité Note
 * Relations :
 * - 1..* Etudiant -> Note (une note appartient à exactement 1 étudiant)
 * - 1..* Module -> Note (une note est donnée pour exactement 1 module)
 */
@Entity
@Table(name = "note", uniqueConstraints = {
        @UniqueConstraint(name = "unique_note_module_etudiant", columnNames = { "etudiant_id", "module_id", "type" })
})
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_note")
    private Integer idNote;

    @Column(name = "valeur", nullable = false)
    private BigDecimal valeur;

    @Column(name = "type")
    private String type; // Ex: 'CC', 'TP', 'Examen'

    @Column(name = "date_note")
    private LocalDate dateNote;

    @Column(name = "publier")
    private Boolean publier = false;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    // Constructeurs
    public Note() {
    }

    public Note(BigDecimal valeur, String type, LocalDate dateNote, Boolean publier, Etudiant etudiant, Module module) {
        this.valeur = valeur;
        this.type = type;
        this.dateNote = dateNote;
        this.publier = publier;
        this.etudiant = etudiant;
        this.module = module;
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

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
