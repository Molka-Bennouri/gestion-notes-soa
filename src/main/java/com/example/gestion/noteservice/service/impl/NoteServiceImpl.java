package com.example.gestion.noteservice.service.impl;

import com.example.gestion.etudiantservice.entity.Etudiant;
import com.example.gestion.etudiantservice.repository.EtudiantRepository;
import com.example.gestion.moduleservice.entity.Module;
import com.example.gestion.moduleservice.repository.ModuleRepository;
import com.example.gestion.noteservice.dto.CreateNoteRequest;
import com.example.gestion.noteservice.dto.NoteDTO;
import com.example.gestion.noteservice.entity.Note;
import com.example.gestion.noteservice.repository.NoteRepository;
import com.example.gestion.noteservice.service.NoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service Note
 */
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final ModuleRepository moduleRepository;

    public NoteServiceImpl(NoteRepository noteRepository,
            EtudiantRepository etudiantRepository,
            ModuleRepository moduleRepository) {
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    @Transactional
    public NoteDTO creerNote(CreateNoteRequest request) {
        // Vérifier si une note existe déjà pour cet étudiant, module et type
        if (noteRepository.existsByEtudiantIdAndModuleIdAndType(
                request.getEtudiantId(),
                request.getModuleId(),
                request.getType())) {
            throw new RuntimeException("Une note de type '" + request.getType() +
                    "' existe déjà pour cet étudiant et ce module");
        }

        // Récupérer l'étudiant
        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID: " + request.getEtudiantId()));

        // Récupérer le module
        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> new RuntimeException("Module non trouvé avec l'ID: " + request.getModuleId()));

        // Créer la note
        Note note = new Note(
                request.getValeur(),
                request.getType(),
                request.getDateNote(),
                request.getPublier() != null ? request.getPublier() : false,
                etudiant,
                module);

        note = noteRepository.save(note);
        return convertToDTO(note);
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO getNoteById(Integer id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID: " + id));
        return convertToDTO(note);
    }

    @Override
    public List<NoteDTO> getNotesByEtudiant(Integer etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteDTO> getPublishedNotesByEtudiant(Integer etudiantId) {
        return noteRepository.findPublishedNotesByEtudiantId(etudiantId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteDTO> getNotesByModule(Integer moduleId) {
        return noteRepository.findByModuleId(moduleId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteDTO> getNotesByEtudiantAndModule(Integer etudiantId, Integer moduleId) {
        return noteRepository.findByEtudiantIdAndModuleId(etudiantId, moduleId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NoteDTO publierNote(Integer noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID: " + noteId));
        note.setPublier(true);
        note = noteRepository.save(note);
        return convertToDTO(note);
    }

    @Override
    @Transactional
    public NoteDTO depublierNote(Integer noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID: " + noteId));
        note.setPublier(false);
        note = noteRepository.save(note);
        return convertToDTO(note);
    }

    @Override
    @Transactional
    public NoteDTO modifierNote(Integer noteId, CreateNoteRequest request) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID: " + noteId));

        // Mettre à jour les champs
        if (request.getValeur() != null) {
            note.setValeur(request.getValeur());
        }
        if (request.getType() != null) {
            note.setType(request.getType());
        }
        if (request.getDateNote() != null) {
            note.setDateNote(request.getDateNote());
        }
        if (request.getPublier() != null) {
            note.setPublier(request.getPublier());
        }

        note = noteRepository.save(note);
        return convertToDTO(note);
    }

    @Override
    @Transactional
    public void supprimerNote(Integer id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note non trouvée avec l'ID: " + id);
        }
        noteRepository.deleteById(id);
    }

    @Override
    public Double calculerMoyenneEtudiantModule(Integer etudiantId, Integer moduleId) {
        Double moyenne = noteRepository.calculateAverageByEtudiantAndModule(etudiantId, moduleId);
        return moyenne != null ? moyenne : 0.0;
    }

    @Override
    public Double calculerMoyenneGenerale(Integer etudiantId) {
        Double moyenne = noteRepository.calculateGeneralAverageByEtudiant(etudiantId);
        return moyenne != null ? moyenne : 0.0;
    }

    /**
     * Convertir une entité Note en DTO
     */
    private NoteDTO convertToDTO(Note note) {
        return new NoteDTO(
                note.getIdNote(),
                note.getValeur(),
                note.getType(),
                note.getDateNote(),
                note.getPublier(),
                note.getEtudiant().getId(),
                note.getEtudiant().getUtilisateur().getNom(),
                note.getEtudiant().getUtilisateur().getPrenom(),
                note.getModule().getIdModule(),
                note.getModule().getNomModule());
    }
}
