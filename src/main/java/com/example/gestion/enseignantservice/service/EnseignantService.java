package com.example.gestion.enseignantservice.service;

import com.example.gestion.enseignantservice.dto.*;

public interface EnseignantService {
    String creerCompte(EnseignantCompteRequest req);
    String modifierCompte(Integer id, EnseignantCompteRequest req); // Changé Long → Integer
    NoteResponse ajouterNote(NoteRequest req);
    NoteResponse modifierNote(Long noteId, NoteRequest req);
    void supprimerNote(Long noteId);
    MoyenneResponse consulterMoyenne(Long etudiantId, Long moduleId);
}