package com.example.gestion.moduleservice.service;

import com.example.gestion.moduleservice.entity.Module;

import java.util.List;

public interface ModuleService {

    Module ajouterModule(Module module);

    Module modifierModule(Long id, Module module);

    void supprimerModule(Long id);

    Module getModule(Long id);

    List<Module> getAllModules();

    Module assignerEnseignant(Long moduleId, Long enseignantId);

    List<Module> filtrerParNiveau(String niveau);

    List<Module> modulesParEnseignant(Long enseignantId);
}
