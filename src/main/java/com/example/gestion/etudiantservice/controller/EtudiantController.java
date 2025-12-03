package com.example.gestion.etudiantservice.controller;

import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.etudiantservice.dto.CreateEtudiantRequest;
import com.example.gestion.etudiantservice.dto.EtudiantDTO;
import com.example.gestion.etudiantservice.service.EtudiantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin(origins = "*")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    /**
     * Authentifier un étudiant
     * POST /api/etudiants/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = etudiantService.authentifierEtudiant(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(false, "Erreur lors de l'authentification: " + e.getMessage()));
        }
    }

    /**
     * Créer un nouvel étudiant
     * POST /api/etudiants
     */
    @PostMapping
    public ResponseEntity<?> creerEtudiant(@RequestBody CreateEtudiantRequest request) {
        try {
            EtudiantDTO etudiant = etudiantService.creerEtudiant(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(etudiant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de l'étudiant: " + e.getMessage());
        }
    }

    /**
     * Récupérer tous les étudiants
     * GET /api/etudiants
     */
    @GetMapping
    public ResponseEntity<List<EtudiantDTO>> getAllEtudiants() {
        try {
            List<EtudiantDTO> etudiants = etudiantService.getAllEtudiants();
            return ResponseEntity.ok(etudiants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer un étudiant par ID
     * GET /api/etudiants/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEtudiantById(@PathVariable Integer id) {
        try {
            EtudiantDTO etudiant = etudiantService.getEtudiantById(id);
            return ResponseEntity.ok(etudiant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération de l'étudiant: " + e.getMessage());
        }
    }

    /**
     * Rechercher des étudiants par nom
     * GET /api/etudiants/recherche?nom=...
     */
    @GetMapping("/recherche")
    public ResponseEntity<List<EtudiantDTO>> rechercherParNom(@RequestParam String nom) {
        try {
            List<EtudiantDTO> etudiants = etudiantService.rechercherParNom(nom);
            return ResponseEntity.ok(etudiants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les étudiants par niveau
     * GET /api/etudiants/niveau/{niveau}
     */
    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<EtudiantDTO>> getEtudiantsByNiveau(@PathVariable String niveau) {
        try {
            List<EtudiantDTO> etudiants = etudiantService.getEtudiantsByNiveau(niveau);
            return ResponseEntity.ok(etudiants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Supprimer un étudiant
     * DELETE /api/etudiants/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerEtudiant(@PathVariable Integer id) {
        try {
            etudiantService.supprimerEtudiant(id);
            return ResponseEntity.ok("Étudiant supprimé avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression de l'étudiant: " + e.getMessage());
        }
    }
}
