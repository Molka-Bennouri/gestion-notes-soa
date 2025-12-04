package com.example.gestion.adminservice.dto;

public class StatistiquesResponse {
    private Long totalEtudiants;
    private Long totalEnseignants;
    private Long totalModules;
    private Long totalAdmins;

    public StatistiquesResponse(Long totalEtudiants, Long totalEnseignants,
                                Long totalModules, Long totalAdmins) {
        this.totalEtudiants = totalEtudiants;
        this.totalEnseignants = totalEnseignants;
        this.totalModules = totalModules;
        this.totalAdmins = totalAdmins;
    }

    // Getters et Setters
    public Long getTotalEtudiants() { return totalEtudiants; }
    public void setTotalEtudiants(Long totalEtudiants) { this.totalEtudiants = totalEtudiants; }

    public Long getTotalEnseignants() { return totalEnseignants; }
    public void setTotalEnseignants(Long totalEnseignants) { this.totalEnseignants = totalEnseignants; }

    public Long getTotalModules() { return totalModules; }
    public void setTotalModules(Long totalModules) { this.totalModules = totalModules; }

    public Long getTotalAdmins() { return totalAdmins; }
    public void setTotalAdmins(Long totalAdmins) { this.totalAdmins = totalAdmins; }
}