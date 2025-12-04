package com.example.gestion.noteservice.controller;

import com.example.gestion.noteservice.dto.CreateNoteRequest;
import com.example.gestion.noteservice.dto.NoteDTO;
import com.example.gestion.noteservice.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des notes
 */
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Créer une nouvelle note
     * POST /api/notes
     */
    @PostMapping
    public ResponseEntity<?> creerNote(@RequestBody CreateNoteRequest request) {
        try {
            NoteDTO note = noteService.creerNote(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(note);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de la note: " + e.getMessage());
        }
    }

    /**
     * Récupérer toutes les notes
     * GET /api/notes
     */
    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        try {
            List<NoteDTO> notes = noteService.getAllNotes();
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer une note par ID
     * GET /api/notes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable Integer id) {
        try {
            NoteDTO note = noteService.getNoteById(id);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération de la note: " + e.getMessage());
        }
    }

    /**
     * Récupérer les notes d'un étudiant
     * GET /api/notes/etudiant/{etudiantId}
     */
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<NoteDTO>> getNotesByEtudiant(@PathVariable Integer etudiantId) {
        try {
            List<NoteDTO> notes = noteService.getNotesByEtudiant(etudiantId);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les notes publiées d'un étudiant
     * GET /api/notes/etudiant/{etudiantId}/publiees
     */
    @GetMapping("/etudiant/{etudiantId}/publiees")
    public ResponseEntity<List<NoteDTO>> getPublishedNotesByEtudiant(@PathVariable Integer etudiantId) {
        try {
            List<NoteDTO> notes = noteService.getPublishedNotesByEtudiant(etudiantId);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les notes d'un module
     * GET /api/notes/module/{moduleId}
     */
    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<NoteDTO>> getNotesByModule(@PathVariable Integer moduleId) {
        try {
            List<NoteDTO> notes = noteService.getNotesByModule(moduleId);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Récupérer les notes d'un étudiant pour un module
     * GET /api/notes/etudiant/{etudiantId}/module/{moduleId}
     */
    @GetMapping("/etudiant/{etudiantId}/module/{moduleId}")
    public ResponseEntity<List<NoteDTO>> getNotesByEtudiantAndModule(
            @PathVariable Integer etudiantId,
            @PathVariable Integer moduleId) {
        try {
            List<NoteDTO> notes = noteService.getNotesByEtudiantAndModule(etudiantId, moduleId);
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Publier une note
     * PUT /api/notes/{id}/publier
     */
    @PutMapping("/{id}/publier")
    public ResponseEntity<?> publierNote(@PathVariable Integer id) {
        try {
            NoteDTO note = noteService.publierNote(id);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la publication de la note: " + e.getMessage());
        }
    }

    /**
     * Dépublier une note
     * PUT /api/notes/{id}/depublier
     */
    @PutMapping("/{id}/depublier")
    public ResponseEntity<?> depublierNote(@PathVariable Integer id) {
        try {
            NoteDTO note = noteService.depublierNote(id);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la dépublication de la note: " + e.getMessage());
        }
    }

    /**
     * Modifier une note
     * PUT /api/notes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierNote(@PathVariable Integer id, @RequestBody CreateNoteRequest request) {
        try {
            NoteDTO note = noteService.modifierNote(id, request);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la modification de la note: " + e.getMessage());
        }
    }

    /**
     * Supprimer une note
     * DELETE /api/notes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerNote(@PathVariable Integer id) {
        try {
            noteService.supprimerNote(id);
            return ResponseEntity.ok("Note supprimée avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression de la note: " + e.getMessage());
        }
    }

    /**
     * Calculer la moyenne d'un étudiant pour un module
     * GET /api/notes/moyenne/etudiant/{etudiantId}/module/{moduleId}
     */
    @GetMapping("/moyenne/etudiant/{etudiantId}/module/{moduleId}")
    public ResponseEntity<?> calculerMoyenneEtudiantModule(
            @PathVariable Integer etudiantId,
            @PathVariable Integer moduleId) {
        try {
            Double moyenne = noteService.calculerMoyenneEtudiantModule(etudiantId, moduleId);
            Map<String, Object> response = new HashMap<>();
            response.put("etudiantId", etudiantId);
            response.put("moduleId", moduleId);
            response.put("moyenne", moyenne);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors du calcul de la moyenne: " + e.getMessage());
        }
    }

    /**
     * Calculer la moyenne générale d'un étudiant
     * GET /api/notes/moyenne/etudiant/{etudiantId}
     */
    @GetMapping("/moyenne/etudiant/{etudiantId}")
    public ResponseEntity<?> calculerMoyenneGenerale(@PathVariable Integer etudiantId) {
        try {
            Double moyenne = noteService.calculerMoyenneGenerale(etudiantId);
            Map<String, Object> response = new HashMap<>();
            response.put("etudiantId", etudiantId);
            response.put("moyenneGenerale", moyenne);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors du calcul de la moyenne générale: " + e.getMessage());
        }
    }
}
