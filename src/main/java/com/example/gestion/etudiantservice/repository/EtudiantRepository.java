package com.example.gestion.etudiantservice.repository;

import com.example.gestion.etudiantservice.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Etudiant
 * Gère les opérations CRUD et les requêtes personnalisées
 */
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {

    /**
     * Rechercher des étudiants par nom (insensible à la casse)
     * 
     * @param nom Le nom à rechercher
     * @return Liste des étudiants correspondants
     */
    @Query("SELECT e FROM Etudiant e WHERE LOWER(e.utilisateur.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Etudiant> findByNomContainingIgnoreCase(@Param("nom") String nom);

    /**
     * Rechercher un étudiant par email (pour l'authentification)
     * 
     * @param email L'email de l'étudiant
     * @return L'étudiant si trouvé
     */
    @Query("SELECT e FROM Etudiant e WHERE e.utilisateur.email = :email")
    Optional<Etudiant> findByEmail(@Param("email") String email);

    /**
     * Authentifier un étudiant par email et mot de passe
     * 
     * @param email      L'email de l'étudiant
     * @param motDePasse Le mot de passe de l'étudiant
     * @return L'étudiant si les identifiants sont corrects
     */
    @Query("SELECT e FROM Etudiant e WHERE e.utilisateur.email = :email " +
            "AND e.utilisateur.motDePasse = :motDePasse " +
            "AND e.utilisateur.typeUtilisateur = 'Etudiant'")
    Optional<Etudiant> findByEmailAndMotDePasse(@Param("email") String email,
            @Param("motDePasse") String motDePasse);

    /**
     * Vérifier si un étudiant existe avec cet email
     * 
     * @param email L'email à vérifier
     * @return true si l'email existe déjà
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Etudiant e WHERE e.utilisateur.email = :email")
    boolean existsByEmail(@Param("email") String email);

    /**
     * Rechercher des étudiants par niveau
     * 
     * @param niveau Le niveau à rechercher (ex: "L1", "L2", "M1")
     * @return Liste des étudiants du niveau spécifié
     */
    List<Etudiant> findByNiveau(String niveau);

    /**
     * Rechercher des étudiants par nom et prénom
     * 
     * @param nom    Le nom de l'étudiant
     * @param prenom Le prénom de l'étudiant
     * @return Liste des étudiants correspondants
     */
    @Query("SELECT e FROM Etudiant e WHERE LOWER(e.utilisateur.nom) = LOWER(:nom) " +
            "AND LOWER(e.utilisateur.prenom) = LOWER(:prenom)")
    List<Etudiant> findByNomAndPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

}
