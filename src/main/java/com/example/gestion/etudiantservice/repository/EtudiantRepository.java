package com.example.gestion.etudiantservice.repository;

import com.example.gestion.etudiantservice.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {

    /**
     * Authentifier un étudiant par email et mot de passe
     */
    @Query("SELECT e FROM Etudiant e WHERE e.utilisateur.email = :email " +
            "AND e.utilisateur.motDePasse = :motDePasse")
    Optional<Etudiant> findByEmailAndMotDePasse(@Param("email") String email,
                                                @Param("motDePasse") String motDePasse);

    /**
     * Vérifier si un étudiant existe avec cet email
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
            "FROM Etudiant e WHERE e.utilisateur.email = :email")
    boolean existsByEmail(@Param("email") String email);

    /**
     * Rechercher des étudiants par niveau
     */
    List<Etudiant> findByNiveau(String niveau);

    /**
     * Rechercher des étudiants par nom et prénom
     */
    @Query("SELECT e FROM Etudiant e WHERE LOWER(e.utilisateur.nom) = LOWER(:nom) " +
            "AND LOWER(e.utilisateur.prenom) = LOWER(:prenom)")
    List<Etudiant> findByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    /**
     * Rechercher des étudiants par nom (contient, insensible à la casse)
     */
    @Query("SELECT e FROM Etudiant e WHERE LOWER(e.utilisateur.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Etudiant> findByNomContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Rechercher des étudiants par prénom (contient, insensible à la casse)
     */
    @Query("SELECT e FROM Etudiant e WHERE LOWER(e.utilisateur.prenom) LIKE LOWER(CONCAT('%', :prenom, '%'))")
    List<Etudiant> findByPrenomContainingIgnoreCase(@Param("prenom") String prenom);
}