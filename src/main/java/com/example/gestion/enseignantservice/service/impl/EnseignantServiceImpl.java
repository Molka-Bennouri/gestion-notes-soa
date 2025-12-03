package com.example.gestion.enseignantservice.service.impl;

import com.example.gestion.enseignantservice.dto.*;
import com.example.gestion.enseignantservice.entity.Enseignant;
import com.example.gestion.enseignantservice.entity.Note;
import com.example.gestion.enseignantservice.repository.EnseignantRepository;
import com.example.gestion.enseignantservice.repository.NoteRepository;
import com.example.gestion.enseignantservice.service.EnseignantService;   // ✅ IMPORTANT

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository enseignantRepository;
    private final NoteRepository noteRepository;

    public EnseignantServiceImpl(EnseignantRepository enseignantRepository, NoteRepository noteRepository) {
        this.enseignantRepository = enseignantRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public String creerCompte(EnseignantCompteRequest req) {
        Enseignant e = new Enseignant();
        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setEmail(req.getEmail());
        e.setMotDePasse(req.getMotDePasse());
        e.setSpecialite(req.getSpecialite());

        enseignantRepository.save(e);
        return "Compte enseignant créé avec succès";
    }

    @Override
    public String modifierCompte(Long id, EnseignantCompteRequest req) {
        Enseignant e = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        e.setNom(req.getNom());
        e.setPrenom(req.getPrenom());
        e.setEmail(req.getEmail());
        e.setMotDePasse(req.getMotDePasse());
        e.setSpecialite(req.getSpecialite());

        enseignantRepository.save(e);
        return "Compte enseignant modifié";
    }

    @Override
    public NoteResponse ajouterNote(NoteRequest req) {
        Note n = new Note();
        n.setEtudiantId(req.getEtudiantId());
        n.setModuleId(req.getModuleId());
        n.setValeur(req.getValeur());
        n.setType(req.getType());
        n.setPublier(false);

        noteRepository.save(n);

        return new NoteResponse(
                n.getIdNote(),
                n.getValeur(),
                n.getType(),
                n.getEtudiantId(),
                n.getModuleId(),
                n.isPublier()
        );
    }

    @Override
    public NoteResponse modifierNote(Long noteId, NoteRequest req) {
        Note n = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));

        n.setValeur(req.getValeur());
        n.setType(req.getType());

        noteRepository.save(n);

        return new NoteResponse(
                n.getIdNote(),
                n.getValeur(),
                n.getType(),
                n.getEtudiantId(),
                n.getModuleId(),
                n.isPublier()
        );
    }

    @Override
    public void supprimerNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    @Override
    public MoyenneResponse consulterMoyenne(Long etudiantId, Long moduleId) {
        List<Note> notes = noteRepository.findByEtudiantIdAndModuleId(etudiantId, moduleId);

        if (notes.isEmpty())
            return new MoyenneResponse(etudiantId, moduleId, 0.0);

        double moyenne = notes.stream()
                .mapToDouble(Note::getValeur)
                .average()
                .orElse(0.0);

        return new MoyenneResponse(etudiantId, moduleId, moyenne);
    }

}
