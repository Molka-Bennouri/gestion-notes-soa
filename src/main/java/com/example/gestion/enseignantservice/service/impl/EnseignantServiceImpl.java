package com.example.gestion.enseignantservice.service.impl;

import com.example.gestion.enseignantservice.dto.*;
import com.example.gestion.enseignantservice.entity.Enseignant;
import com.example.gestion.enseignantservice.repository.EnseignantRepository;
import com.example.gestion.enseignantservice.service.EnseignantService;
import com.example.gestion.noteservice.entity.Note;
import com.example.gestion.noteservice.repository.NoteRepository;
import com.example.gestion.etudiantservice.entity.Etudiant;
import com.example.gestion.etudiantservice.repository.EtudiantRepository;
import com.example.gestion.moduleservice.entity.Module;
import com.example.gestion.moduleservice.repository.ModuleRepository;
import com.example.gestion.common.entity.Utilisateur;
import com.example.gestion.common.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final ModuleRepository moduleRepository;
    private final UtilisateurRepository utilisateurRepository;

    public EnseignantServiceImpl(EnseignantRepository enseignantRepository,
                                 NoteRepository noteRepository,
                                 EtudiantRepository etudiantRepository,
                                 ModuleRepository moduleRepository,
                                 UtilisateurRepository utilisateurRepository) {
        this.enseignantRepository = enseignantRepository;
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.moduleRepository = moduleRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public String creerCompte(EnseignantCompteRequest req) {
        // 1. Créer l'Utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(req.getNom());
        utilisateur.setPrenom(req.getPrenom());
        utilisateur.setEmail(req.getEmail());
        utilisateur.setMotDePasse(req.getMotDePasse());
        utilisateur.setTypeUtilisateur(Utilisateur.TypeUtilisateur.Enseignant);

        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);

        // 2. Créer l'Enseignant lié à l'Utilisateur
        Enseignant enseignant = new Enseignant();
        enseignant.setUtilisateur(savedUtilisateur);
        enseignant.setSpecialite(req.getSpecialite());

        enseignantRepository.save(enseignant);
        return "Compte enseignant créé avec succès";
    }

    @Override
    public String modifierCompte(Integer id, EnseignantCompteRequest req) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        // Mettre à jour l'Utilisateur associé
        Utilisateur utilisateur = enseignant.getUtilisateur();
        utilisateur.setNom(req.getNom());
        utilisateur.setPrenom(req.getPrenom());
        utilisateur.setEmail(req.getEmail());
        utilisateur.setMotDePasse(req.getMotDePasse());

        utilisateurRepository.save(utilisateur);

        // Mettre à jour la spécialité de l'enseignant
        enseignant.setSpecialite(req.getSpecialite());
        enseignantRepository.save(enseignant);

        return "Compte enseignant modifié";
    }

    @Override
    public NoteResponse ajouterNote(NoteRequest req) {
        // Convertir Long en Integer pour les IDs
        Integer etudiantId = req.getEtudiantId().intValue();
        Integer moduleId = req.getModuleId().intValue();

        // Récupérer l'étudiant et le module
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module introuvable"));

        // Créer la note avec la nouvelle structure
        Note note = new Note();
        note.setValeur(req.getValeur());
        note.setType(req.getType());
        note.setDateNote(LocalDate.now());
        note.setPublier(false);
        note.setEtudiant(etudiant);
        note.setModule(module);

        Note savedNote = noteRepository.save(note);

        return new NoteResponse(
                savedNote.getIdNote().longValue(),
                savedNote.getValeur(),
                savedNote.getType(),
                savedNote.getEtudiant().getId().longValue(),
                savedNote.getModule().getIdModule().longValue(),
                savedNote.getPublier()
        );
    }

    @Override
    public NoteResponse modifierNote(Long noteId, NoteRequest req) {
        Integer noteIdInt = noteId.intValue();

        Note note = noteRepository.findById(noteIdInt)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));

        note.setValeur(req.getValeur());
        note.setType(req.getType());

        Note updatedNote = noteRepository.save(note);

        return new NoteResponse(
                updatedNote.getIdNote().longValue(),
                updatedNote.getValeur(),
                updatedNote.getType(),
                updatedNote.getEtudiant().getId().longValue(),
                updatedNote.getModule().getIdModule().longValue(),
                updatedNote.getPublier()
        );
    }

    @Override
    public void supprimerNote(Long noteId) {
        noteRepository.deleteById(noteId.intValue());
    }

    @Override
    public MoyenneResponse consulterMoyenne(Long etudiantId, Long moduleId) {
        Integer etudiantIdInt = etudiantId.intValue();
        Integer moduleIdInt = moduleId.intValue();

        // Récupérer toutes les notes de l'étudiant pour ce module
        List<Note> notes = noteRepository.findByEtudiant_IdAndModule_IdModule(
                etudiantIdInt, moduleIdInt);

        if (notes.isEmpty()) {
            return new MoyenneResponse(etudiantId, moduleId, 0.0);
        }

        // Calculer la moyenne
        double somme = 0.0;
        for (Note note : notes) {
            somme += note.getValeur();
        }

        double moyenne = somme / notes.size();

        return new MoyenneResponse(etudiantId, moduleId, moyenne);
    }
}