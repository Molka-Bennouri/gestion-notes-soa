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
    public String modifierCompte(@PathVariable Long id, @RequestBody EnseignantCompteRequest request) {
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
    public NoteResponse modifierNote(@PathVariable Long id, @RequestBody NoteRequest request) {
        return enseignantService.modifierNote(id, request);
    }

    // ===============================
    // ✔ SUPPRIMER NOTE (JSON ONLY)
    // ===============================
    @DeleteMapping("/notes/delete/{id}")
    public void supprimerNote(@PathVariable Long id) {
        enseignantService.supprimerNote(id);
    }

    // ===============================
    // ✔ CONSULTER MOYENNE (JSON ONLY)
    // ===============================
    @GetMapping(
            value = "/moyenne",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public MoyenneResponse consulterMoyenne(
            @RequestParam Long etudiantId,
            @RequestParam Long moduleId
    ) {
        return enseignantService.consulterMoyenne(etudiantId, moduleId);
    }
}
