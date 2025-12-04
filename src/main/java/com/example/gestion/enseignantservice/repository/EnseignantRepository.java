package com.example.gestion.enseignantservice.repository;

import com.example.gestion.enseignantservice.entity.Enseignant;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    Optional<Enseignant> findByEmail(String email);

    List<Enseignant> findByNomContainingIgnoreCase(String nom);

    List<Enseignant> findBySpecialite(String specialite);
}
