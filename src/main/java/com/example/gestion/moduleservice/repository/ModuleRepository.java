package com.example.gestion.moduleservice.repository;

import com.example.gestion.moduleservice.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Module
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    /**
     * Récupérer les modules par niveau
     */
    List<Module> findByNiveau(String niveau);

    /**
     * Récupérer les modules d'un enseignant
     */
    @Query("SELECT m FROM Module m WHERE m.enseignant.id = :enseignantId")
    List<Module> findByEnseignantId(@Param("enseignantId") Integer enseignantId);

    /**
     * Rechercher des modules par nom
     */
    @Query("SELECT m FROM Module m WHERE LOWER(m.nomModule) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Module> findByNomModuleContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Vérifier si un module existe par nom
     */
    boolean existsByNomModule(String nomModule);
}
