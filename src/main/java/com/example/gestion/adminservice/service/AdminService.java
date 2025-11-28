package com.example.gestion.adminservice.service;

import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.adminservice.dto.StatistiquesResponse;
import com.example.gestion.adminservice.entity.Utilisateur;
import java.util.Optional;

public interface AdminService {
    LoginResponse authentifierAdmin(LoginRequest loginRequest);
    Utilisateur creerAdmin(Utilisateur admin);
    boolean adminExists(String email);
    Optional<Utilisateur> getAdminByEmail(String email);

    StatistiquesResponse getStatistiques();
}