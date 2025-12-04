package com.example.gestion.enseignantservice.repository;

import com.example.gestion.enseignantservice.entity.Enseignant;
import com.example.gestion.etudiantservice.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {

    /**
     * Authentifier un enseignant par email et mot de passe
     */
    @Query("SELECT e FROM Enseignant e WHERE e.utilisateur.email = :email " +
            "AND e.utilisateur.motDePasse = :motDePasse")
    Optional<Enseignant> findByEmailAndMotDePasse(@Param("email") String email,
                                                @Param("motDePasse") String motDePasse);// Changé de Long à Integer

    List<Enseignant> findBySpecialite(String specialite);
}