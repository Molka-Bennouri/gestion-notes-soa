package com.example.gestion.enseignantservice.entity;


import jakarta.persistence.Entity;
import com.example.gestion.common.entity.Utilisateur;

import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enseignant")
public class Enseignant extends Utilisateur {

    private String specialite;

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
