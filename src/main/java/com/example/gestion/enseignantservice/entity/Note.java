package com.example.gestion.enseignantservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_note")
    private Long idNote;

    @Column(nullable = false)
    private Double valeur;

    private String type;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_note")
    private Date dateNote;

    private boolean publier;

    // ==========================================
    // Pas de relations JPA : tu utilises juste des IDs
    // ==========================================

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId; // FK vers etudiant.id

    @Column(name = "module_id", nullable = false)
    private Long moduleId;   // FK vers module.id_module
}
