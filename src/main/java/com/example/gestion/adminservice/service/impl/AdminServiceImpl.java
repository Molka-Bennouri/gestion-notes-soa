package com.example.gestion.adminservice.service.impl;

import com.example.gestion.adminservice.dto.StatistiquesResponse;
import org.springframework.stereotype.Service;
import com.example.gestion.adminservice.entity.Utilisateur;
import com.example.gestion.adminservice.repository.AdminRepository;
import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.adminservice.service.AdminService;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public LoginResponse authentifierAdmin(LoginRequest loginRequest) {
        // ✅ Utilisation de la méthode simplifiée du repository
        Optional<Utilisateur> adminOpt = adminRepository.findByEmailAndMotDePasseAndTypeUtilisateur(
                loginRequest.getEmail(),
                loginRequest.getMotDePasse(),
                Utilisateur.TypeUtilisateur.Admin  // ← Référence correcte à l'enum
        );

        if (adminOpt.isPresent()) {
            Utilisateur admin = adminOpt.get();
            return new LoginResponse(
                    true,
                    "Authentification admin réussie",
                    admin.getId(),
                    admin.getPrenom() + " " + admin.getNom()
            );
        } else {
            return new LoginResponse(false, "Email ou mot de passe incorrect pour un administrateur");
        }
    }

    @Override
    public Utilisateur creerAdmin(Utilisateur admin) {
        // ✅ S'assurer que c'est bien un admin
        admin.setTypeUtilisateur(Utilisateur.TypeUtilisateur.Admin);

        // ✅ Vérifier si un admin avec cet email existe déjà
        if (adminRepository.existsByEmailAndTypeUtilisateur(admin.getEmail(), Utilisateur.TypeUtilisateur.Admin)) {
            throw new RuntimeException("Un admin avec cet email existe déjà");
        }

        return adminRepository.save(admin);
    }

    // ✅ Méthode supplémentaire pour vérifier si un admin existe par email
    public boolean adminExists(String email) {
        return adminRepository.existsByEmailAndTypeUtilisateur(email, Utilisateur.TypeUtilisateur.Admin);
    }

    // ✅ Méthode pour récupérer un admin par email
    public Optional<Utilisateur> getAdminByEmail(String email) {
        return adminRepository.findByEmailAndTypeUtilisateur(email, Utilisateur.TypeUtilisateur.Admin);
    }

    @Override
    public StatistiquesResponse getStatistiques() {
        return new StatistiquesResponse(
                adminRepository.countTotalEtudiants(),
                adminRepository.countTotalEnseignants(),
                adminRepository.countTotalModules(),
                adminRepository.countTotalAdmins()
        );
    }
}