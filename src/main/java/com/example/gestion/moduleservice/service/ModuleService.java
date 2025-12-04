package com.example.gestion.moduleservice.service;

import com.example.gestion.moduleservice.entity.Module;
import java.util.List;

public interface ModuleService {
    Module ajouterModule(Module module);

    Module modifierModule(Integer id, Module updated);

    void supprimerModule(Integer id);

    Module getModule(Integer id);

    List<Module> getAllModules();

    // CHANGÉ : Accepte deux Integer (moduleId, enseignantId) au lieu de (Integer, Enseignant)
    Module assignerEnseignant(Integer moduleId, Integer enseignantId);

    List<Module> filtrerParNiveau(String niveau);
    List<Module> modulesParEnseignant(Integer enseignantId);
}