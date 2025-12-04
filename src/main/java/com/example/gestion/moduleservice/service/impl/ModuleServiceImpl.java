package com.example.gestion.moduleservice.service.impl;

import com.example.gestion.moduleservice.entity.Module;
import com.example.gestion.moduleservice.repository.ModuleRepository;
import com.example.gestion.moduleservice.service.ModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository repository;

    public ModuleServiceImpl(ModuleRepository repository) {
        this.repository = repository;
    }

    // ============================================
    // ✔ AJOUTER MODULE
    // ============================================
    @Override
    public Module ajouterModule(Module module) {
        return repository.save(module);
    }

    // ============================================
    // ✔ MODIFIER MODULE
    // ============================================
    @Override
    public Module modifierModule(Long id, Module updated) {
        Module module = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));

        module.setNomModule(updated.getNomModule());
        module.setCoefficient(updated.getCoefficient());
        module.setNiveau(updated.getNiveau());
        module.setEnseignantId(updated.getEnseignantId());

        return repository.save(module);
    }

    // ============================================
    // ✔ SUPPRIMER MODULE
    // ============================================
    @Override
    public void supprimerModule(Long id) {
        repository.deleteById(id);
    }

    // ============================================
    // ✔ GET MODULE BY ID
    // ============================================
    @Override
    public Module getModule(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module introuvable"));
    }

    // ============================================
    // ✔ GET ALL MODULES
    // ============================================
    @Override
    public List<Module> getAllModules() {
        return repository.findAll();
    }

    // ============================================
    // ✔ ASSIGNER ENSEIGNANT À UN MODULE
    // ============================================
    @Override
    public Module assignerEnseignant(Long moduleId, Long enseignantId) {
        Module module = repository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module introuvable"));

        module.setEnseignantId(enseignantId);

        return repository.save(module);
    }

    // ============================================
    // ✔ FILTRER PAR NIVEAU
    // ============================================
    @Override
    public List<Module> filtrerParNiveau(String niveau) {
        return repository.findByNiveau(niveau);
    }

    // ============================================
    // ✔ MODULES D'UN ENSEIGNANT
    // ============================================
    @Override
    public List<Module> modulesParEnseignant(Long enseignantId) {
        return repository.findByEnseignantId(enseignantId);
    }
}
