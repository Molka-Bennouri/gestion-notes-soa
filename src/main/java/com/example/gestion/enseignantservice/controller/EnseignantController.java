package com.example.gestion.enseignantservice.controller;

import com.example.gestion.enseignantservice.dto.*;
import com.example.gestion.enseignantservice.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enseignants")
@CrossOrigin(origins = "*")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    // ===============================
    // ✔ CRÉER COMPTE (JSON ONLY)
    // ===============================
    @PostMapping(
            value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String creerCompte(@RequestBody EnseignantCompteRequest request) {
        return enseignantService.creerCompte(request);
    }

    // ===============================
    // ✔ MODIFIER COMPTE (JSON ONLY)
    // ===============================
    @PutMapping(
            value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String modifierCompte(@PathVariable Integer id, @RequestBody EnseignantCompteRequest request) { // Long → Integer
        return enseignantService.modifierCompte(id, request);
    }

    // ===============================
    // ✔ AJOUTER NOTE (JSON ONLY)
    // ===============================
    @PostMapping(
            value = "/notes/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public NoteResponse ajouterNote(@RequestBody NoteRequest request) {
        return enseignantService.ajouterNote(request);
    }

    // ===============================
    // ✔ MODIFIER NOTE (JSON ONLY)
    // ===============================
    @PutMapping(
            value = "/notes/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public NoteResponse modifierNote(@PathVariable Integer id, @RequestBody NoteRequest request) { // Long → Integer
        return enseignantService.modifierNote(id.longValue(), request); // Convertir Integer → Long si nécessaire
    }

    // ===============================
    // ✔ SUPPRIMER NOTE (JSON ONLY)
    // ===============================
    @DeleteMapping("/notes/delete/{id}")
    public void supprimerNote(@PathVariable Integer id) { // Long → Integer
        enseignantService.supprimerNote(id.longValue()); // Convertir Integer → Long si nécessaire
    }

    // ===============================
    // ✔ CONSULTER MOYENNE (JSON ONLY)
    // ===============================
    @GetMapping(
            value = "/moyenne",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public MoyenneResponse consulterMoyenne(
            @RequestParam Long etudiantId,  // Garder Long pour l'API
            @RequestParam Long moduleId      // Garder Long pour l'API
    ) {
        return enseignantService.consulterMoyenne(etudiantId, moduleId);
    }
}