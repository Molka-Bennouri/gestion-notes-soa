package com.example.gestion.etudiantservice.service;

import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.etudiantservice.dto.CreateEtudiantRequest;
import com.example.gestion.etudiantservice.dto.EtudiantDTO;

import java.util.List;

public interface EtudiantService {

    /**
     * Authentifier un étudiant
     */
    LoginResponse authentifierEtudiant(LoginRequest loginRequest);

    /**
     * Créer un nouvel étudiant
     */
    EtudiantDTO creerEtudiant(CreateEtudiantRequest request);

    List<EtudiantDTO> getAllEtudiants();

    EtudiantDTO getEtudiantById(Integer id);

    List<EtudiantDTO> rechercherParNom(String nom);

    void supprimerEtudiant(Integer id);

    List<EtudiantDTO> getEtudiantsByNiveau(String niveau);
}
