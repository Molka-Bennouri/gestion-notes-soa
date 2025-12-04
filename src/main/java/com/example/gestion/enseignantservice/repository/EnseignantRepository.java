package com.example.gestion.enseignantservice.repository;

import com.example.gestion.enseignantservice.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {  // Changé de Long à Integer

    Optional<Enseignant> findByEmail(String email);

    List<Enseignant> findByNomContainingIgnoreCase(String nom);

    List<Enseignant> findBySpecialite(String specialite);
}