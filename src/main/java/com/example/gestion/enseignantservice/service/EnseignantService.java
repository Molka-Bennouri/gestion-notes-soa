package com.example.gestion.enseignantservice.service;

import com.example.gestion.enseignantservice.dto.*;

public interface EnseignantService {

    String creerCompte(EnseignantCompteRequest request);

    String modifierCompte(Long id, EnseignantCompteRequest request);

    NoteResponse ajouterNote(NoteRequest request);

    NoteResponse modifierNote(Long noteId, NoteRequest request);

    void supprimerNote(Long noteId);

    MoyenneResponse consulterMoyenne(Long etudiantId, Long moduleId);
}
