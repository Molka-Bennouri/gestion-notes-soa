package com.example.gestion.enseignantservice.service;

import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.enseignantservice.dto.*;

public interface EnseignantService {

    LoginResponse authentifierEnseignant(LoginRequest loginRequest);

    String modifierCompte(Integer id, EnseignantDTO req); // Changé Long → Integer
    NoteResponse ajouterNote(NoteRequest req);
    NoteResponse modifierNote(Long noteId, NoteRequest req);
    void supprimerNote(Long noteId);
    MoyenneResponse consulterMoyenne(Long etudiantId, Long moduleId);
}