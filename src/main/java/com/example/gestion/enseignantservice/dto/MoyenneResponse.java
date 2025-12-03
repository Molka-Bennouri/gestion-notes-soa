package com.example.gestion.enseignantservice.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MoyenneResponse {

    private Long etudiantId;
    private Long moduleId;
    private Double moyenne;
    private String message;

    // constructeur utilisé par ton service
    public MoyenneResponse(Long etudiantId, Long moduleId, Double moyenne) {
        this.etudiantId = etudiantId;
        this.moduleId = moduleId;
        this.moyenne = moyenne;
        this.message = "Moyenne récupérée avec succès";
    }

    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public Double getMoyenne() { return moyenne; }
    public void setMoyenne(Double moyenne) { this.moyenne = moyenne; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
