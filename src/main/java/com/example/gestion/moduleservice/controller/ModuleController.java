package com.example.gestion.moduleservice.controller;

import com.example.gestion.moduleservice.entity.Module;
import com.example.gestion.moduleservice.service.ModuleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "http://localhost:63342")
public class ModuleController {

    private final ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @PostMapping(
            value = "/add",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public Module ajouter(@RequestBody Module module) {
        return service.ajouterModule(module);
    }

    @PutMapping("/update/{id}")
    public Module modifier(@PathVariable Integer id, @RequestBody Module module) {
        return service.modifierModule(id, module);
    }

    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable Integer id) {
        service.supprimerModule(id);
    }

    @GetMapping("/{id}")
    public Module get(@PathVariable Integer id) {
        return service.getModule(id);
    }

    @GetMapping("/all")
    public List<Module> all() {
        return service.getAllModules();
    }

    @PutMapping("/assign/{moduleId}/{enseignantId}")
    public Module assignerEnseignant(
            @PathVariable Integer moduleId,
            @PathVariable Integer enseignantId
    ) {
        return service.assignerEnseignant(moduleId, enseignantId);
    }

    @GetMapping("/niveau")
    public List<Module> filtrerParNiveau(@RequestParam String niveau) {
        return service.filtrerParNiveau(niveau);
    }

    @GetMapping("/enseignant/{enseignantId}")
    public List<Module> modulesParEns(@PathVariable Integer enseignantId) {
        return service.modulesParEnseignant(enseignantId);
    }
}