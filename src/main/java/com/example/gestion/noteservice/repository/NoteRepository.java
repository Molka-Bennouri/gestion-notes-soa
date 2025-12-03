package com.example.gestion.noteservice.repository;

import com.example.gestion.noteservice.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Note
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    /**
     * Récupérer toutes les notes d'un étudiant
     */
    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId")
    List<Note> findByEtudiantId(@Param("etudiantId") Integer etudiantId);

    /**
     * Récupérer toutes les notes publiées d'un étudiant
     */
    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.publier = true")
    List<Note> findPublishedNotesByEtudiantId(@Param("etudiantId") Integer etudiantId);

    /**
     * Récupérer toutes les notes d'un module
     */
    @Query("SELECT n FROM Note n WHERE n.module.idModule = :moduleId")
    List<Note> findByModuleId(@Param("moduleId") Integer moduleId);

    /**
     * Récupérer les notes d'un étudiant pour un module spécifique
     */
    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.module.idModule = :moduleId")
    List<Note> findByEtudiantIdAndModuleId(@Param("etudiantId") Integer etudiantId,
            @Param("moduleId") Integer moduleId);

    /**
     * Récupérer une note spécifique par étudiant, module et type
     */
    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId " +
            "AND n.module.idModule = :moduleId AND n.type = :type")
    Optional<Note> findByEtudiantIdAndModuleIdAndType(@Param("etudiantId") Integer etudiantId,
            @Param("moduleId") Integer moduleId,
            @Param("type") String type);

    /**
     * Récupérer toutes les notes par type (CC, TP, Examen)
     */
    List<Note> findByType(String type);

    /**
     * Récupérer toutes les notes publiées
     */
    @Query("SELECT n FROM Note n WHERE n.publier = true")
    List<Note> findAllPublishedNotes();

    /**
     * Récupérer toutes les notes non publiées
     */
    @Query("SELECT n FROM Note n WHERE n.publier = false")
    List<Note> findAllUnpublishedNotes();

    /**
     * Vérifier si une note existe pour un étudiant, module et type donnés
     */
    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Note n " +
            "WHERE n.etudiant.id = :etudiantId AND n.module.idModule = :moduleId AND n.type = :type")
    boolean existsByEtudiantIdAndModuleIdAndType(@Param("etudiantId") Integer etudiantId,
            @Param("moduleId") Integer moduleId,
            @Param("type") String type);

    /**
     * Calculer la moyenne des notes d'un étudiant pour un module
     */
    @Query("SELECT AVG(n.valeur) FROM Note n WHERE n.etudiant.id = :etudiantId " +
            "AND n.module.idModule = :moduleId AND n.publier = true")
    Double calculateAverageByEtudiantAndModule(@Param("etudiantId") Integer etudiantId,
            @Param("moduleId") Integer moduleId);

    /**
     * Calculer la moyenne générale d'un étudiant (toutes notes publiées)
     */
    @Query("SELECT AVG(n.valeur) FROM Note n WHERE n.etudiant.id = :etudiantId AND n.publier = true")
    Double calculateGeneralAverageByEtudiant(@Param("etudiantId") Integer etudiantId);
}
