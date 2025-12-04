package com.example.gestion.enseignantservice.repository;

import com.example.gestion.enseignantservice.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByEtudiantId(Long etudiantId);

    List<Note> findByModuleId(Long moduleId);

    List<Note> findByEtudiantIdAndModuleId(Long etudiantId, Long moduleId);
}
