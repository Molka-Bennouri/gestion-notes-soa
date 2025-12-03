package com.example.gestion.etudiantservice.service.impl;

import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.common.entity.Utilisateur;
import com.example.gestion.etudiantservice.dto.CreateEtudiantRequest;
import com.example.gestion.etudiantservice.dto.EtudiantDTO;
import com.example.gestion.etudiantservice.entity.Etudiant;
import com.example.gestion.etudiantservice.repository.EtudiantRepository;
import com.example.gestion.etudiantservice.service.EtudiantService;
import com.example.gestion.adminservice.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du service Etudiant
 */
@Service
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final AdminRepository utilisateurRepository;

    public EtudiantServiceImpl(EtudiantRepository etudiantRepository, AdminRepository utilisateurRepository) {
        this.etudiantRepository = etudiantRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public LoginResponse authentifierEtudiant(LoginRequest loginRequest) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findByEmailAndMotDePasse(
                loginRequest.getEmail(),
                loginRequest.getMotDePasse());

        if (etudiantOpt.isPresent()) {
            Etudiant etudiant = etudiantOpt.get();
            Utilisateur user = etudiant.getUtilisateur();
            return new LoginResponse(
                    true,
                    "Authentification étudiant réussie",
                    etudiant.getId(),
                    user.getPrenom() + " " + user.getNom());
        } else {
            return new LoginResponse(false, "Email ou mot de passe incorrect");
        }
    }

    @Override
    @Transactional
    public EtudiantDTO creerEtudiant(CreateEtudiantRequest request) {
        // Vérifier si l'email existe déjà
        if (etudiantRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un étudiant avec cet email existe déjà");
        }

        // Créer l'utilisateur
        Utilisateur utilisateur = new Utilisateur(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getMotDePasse(),
                Utilisateur.TypeUtilisateur.Etudiant);
        utilisateur = utilisateurRepository.save(utilisateur);

        // Créer l'étudiant
        Etudiant etudiant = new Etudiant(
                utilisateur,
                request.getDateNaissance(),
                request.getNiveau());
        etudiant = etudiantRepository.save(etudiant);

        return convertToDTO(etudiant);
    }

    @Override
    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EtudiantDTO getEtudiantById(Integer id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID: " + id));
        return convertToDTO(etudiant);
    }

    @Override
    public List<EtudiantDTO> rechercherParNom(String nom) {
        return etudiantRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void supprimerEtudiant(Integer id) {
        if (!etudiantRepository.existsById(id)) {
            throw new RuntimeException("Étudiant non trouvé avec l'ID: " + id);
        }
        etudiantRepository.deleteById(id);
    }

    @Override
    public List<EtudiantDTO> getEtudiantsByNiveau(String niveau) {
        return etudiantRepository.findByNiveau(niveau)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convertir une entité Etudiant en DTO
     */
    private EtudiantDTO convertToDTO(Etudiant etudiant) {
        Utilisateur user = etudiant.getUtilisateur();
        return new EtudiantDTO(
                etudiant.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                etudiant.getDateNaissance(),
                etudiant.getNiveau());
    }
}
