package com.example.gestion.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestion.common.entity.Utilisateur;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Utilisateur, Integer> {

    // SPRING DATA génère automatiquement la query
    Optional<Utilisateur> findByEmailAndTypeUtilisateur(String email, Utilisateur.TypeUtilisateur typeUtilisateur);

    Optional<Utilisateur> findByEmailAndMotDePasseAndTypeUtilisateur(String email, String motDePasse, Utilisateur.TypeUtilisateur typeUtilisateur);

    boolean existsByEmailAndTypeUtilisateur(String email, Utilisateur.TypeUtilisateur typeUtilisateur);

    // Requêtes natives pour les statistiques
    @Query(value = "SELECT COUNT(*) FROM utilisateur WHERE type_utilisateur = 'Etudiant'", nativeQuery = true)
    Long countTotalEtudiants();

    @Query(value = "SELECT COUNT(*) FROM utilisateur WHERE type_utilisateur = 'Enseignant'", nativeQuery = true)
    Long countTotalEnseignants();

    @Query(value = "SELECT COUNT(*) FROM module", nativeQuery = true)
    Long countTotalModules();

    @Query(value = "SELECT COUNT(*) FROM utilisateur WHERE type_utilisateur = 'Admin'", nativeQuery = true)
    Long countTotalAdmins();
}