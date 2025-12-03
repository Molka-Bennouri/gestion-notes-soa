package com.example.gestion.moduleservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.example.gestion.enseignantservice.entity.Enseignant;

/**
 * Entité Module
 * Relation 1..* : Un module est enseigné par exactement 1 enseignant
 */
@Entity
@Table(name = "module")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_module")
    private Integer idModule;

    @Column(name = "nom_module", nullable = false)
    private String nomModule;

    @Column(name = "coefficient", nullable = false)
    private BigDecimal coefficient;

    @Column(name = "niveau")
    private String niveau;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    // Constructeurs
    public Module() {
    }

    public Module(String nomModule, BigDecimal coefficient, String niveau, Enseignant enseignant) {
        this.nomModule = nomModule;
        this.coefficient = coefficient;
        this.niveau = niveau;
        this.enseignant = enseignant;
    }

    // Getters et Setters
    public Integer getIdModule() {return idModule;}
    public void setIdModule(Integer idModule) {this.idModule = idModule;}

    public String getNomModule() {return nomModule;}
    public void setNomModule(String nomModule) {this.nomModule = nomModule;}

    public BigDecimal getCoefficient() {return coefficient;}
    public void setCoefficient(BigDecimal coefficient) {this.coefficient = coefficient;}

    public String getNiveau() {return niveau;}
    public void setNiveau(String niveau) {this.niveau = niveau;}

    public Enseignant getEnseignant() {return enseignant;}
    public void setEnseignant(Enseignant enseignant) {this.enseignant = enseignant;}
}
