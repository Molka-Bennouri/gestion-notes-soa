package com.example.gestion.moduleservice.service.impl;

import com.example.gestion.moduleservice.entity.Module;
import com.example.gestion.moduleservice.repository.ModuleRepository;
import com.example.gestion.moduleservice.service.ModuleService;
import com.example.gestion.enseignantservice.entity.Enseignant;
import com.example.gestion.enseignantservice.repository.EnseignantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final EnseignantRepository enseignantRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository,
                             EnseignantRepository enseignantRepository) {
        this.moduleRepository = moduleRepository;
        this.enseignantRepository = enseignantRepository;
    }

    // ============================================
    // ✔ AJOUTER MODULE
    // ============================================
    @Override
    public Module ajouterModule(Module module) {
        return moduleRepository.save(module);
    }

    // ============================================
    // ✔ MODIFIER MODULE
    // ============================================
    @Override
    public Module modifierModule(Integer id, Module updated) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));

        module.setNomModule(updated.getNomModule());
        module.setCoefficient(updated.getCoefficient());
        module.setNiveau(updated.getNiveau());
        module.setEnseignant(updated.getEnseignant());

        return moduleRepository.save(module);
    }

    // ============================================
    // ✔ SUPPRIMER MODULE
    // ============================================
    @Override
    public void supprimerModule(Integer id) {
        moduleRepository.deleteById(id);
    }

    // ============================================
    // ✔ GET MODULE BY ID
    // ============================================
    @Override
    public Module getModule(Integer id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module introuvable"));
    }

    // ============================================
    // ✔ GET ALL MODULES
    // ============================================
    @Override
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    // ============================================
    // ✔ ASSIGNER ENSEIGNANT À UN MODULE
    // ============================================
    @Override
    public Module assignerEnseignant(Integer moduleId, Integer enseignantId) {
        // 1. Récupérer le module
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module introuvable"));

        // 2. Récupérer l'enseignant par son ID
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        // 3. Assigner l'enseignant au module
        module.setEnseignant(enseignant);

        // 4. Sauvegarder
        return moduleRepository.save(module);
    }

    // ============================================
    // ✔ FILTRER PAR NIVEAU
    // ============================================
    @Override
    public List<Module> filtrerParNiveau(String niveau) {
        return moduleRepository.findByNiveau(niveau);
    }

    // ============================================
    // ✔ MODULES D'UN ENSEIGNANT
    // ============================================
    @Override
    public List<Module> modulesParEnseignant(Integer enseignantId) {
        return moduleRepository.findByEnseignantId(enseignantId);
    }
}