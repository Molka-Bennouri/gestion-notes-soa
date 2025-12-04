package com.example.gestion.noteservice.service;

import com.example.gestion.noteservice.dto.CreateNoteRequest;
import com.example.gestion.noteservice.dto.NoteDTO;

import java.util.List;

/**
 * Interface du service Note
 */
public interface NoteService {

    /**
     * Créer une nouvelle note
     */
    NoteDTO creerNote(CreateNoteRequest request);

    /**
     * Récupérer toutes les notes
     */
    List<NoteDTO> getAllNotes();

    /**
     * Récupérer une note par ID
     */
    NoteDTO getNoteById(Integer id);

    /**
     * Récupérer les notes d'un étudiant
     */
    List<NoteDTO> getNotesByEtudiant(Integer etudiantId);

    /**
     * Récupérer les notes publiées d'un étudiant
     */
    List<NoteDTO> getPublishedNotesByEtudiant(Integer etudiantId);

    /**
     * Récupérer les notes d'un module
     */
    List<NoteDTO> getNotesByModule(Integer moduleId);

    /**
     * Récupérer les notes d'un étudiant pour un module
     */
    List<NoteDTO> getNotesByEtudiantAndModule(Integer etudiantId, Integer moduleId);

    /**
     * Publier une note
     */
    NoteDTO publierNote(Integer noteId);

    /**
     * Dépublier une note
     */
    NoteDTO depublierNote(Integer noteId);

    /**
     * Modifier une note
     */
    NoteDTO modifierNote(Integer noteId, CreateNoteRequest request);

    /**
     * Supprimer une note
     */
    void supprimerNote(Integer id);

    /**
     * Calculer la moyenne d'un étudiant pour un module
     */
    Double calculerMoyenneEtudiantModule(Integer etudiantId, Integer moduleId);

    /**
     * Calculer la moyenne générale d'un étudiant
     */
    Double calculerMoyenneGenerale(Integer etudiantId);
}
