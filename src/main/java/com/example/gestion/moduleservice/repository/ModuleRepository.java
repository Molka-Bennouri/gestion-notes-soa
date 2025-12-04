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
     * Récupérer les modules par niveau (des deux versions)
     */
    List<Module> findByNiveau(String niveau);

    /**
     * Récupérer les modules d'un enseignant par ID
     * Version HEAD: findByEnseignantId(Long enseignantId)
     * Version dev-molka: @Query avec Integer enseignantId
     * On garde la version JPQL avec Integer pour cohérence
     */
    @Query("SELECT m FROM Module m WHERE m.enseignant.id = :enseignantId")
    List<Module> findByEnseignantId(@Param("enseignantId") Integer enseignantId);

    /**
     * Récupérer les modules par nom (nouveau de dev-molka)
     */
    @Query("SELECT m FROM Module m WHERE LOWER(m.nomModule) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Module> findByNomModuleContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Vérifier si un module existe par nom (nouveau de dev-molka)
     */
    boolean existsByNomModule(String nomModule);
}