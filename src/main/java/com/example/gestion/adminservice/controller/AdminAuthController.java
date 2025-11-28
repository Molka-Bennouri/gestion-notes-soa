package com.example.gestion.adminservice.controller;

import com.example.gestion.adminservice.dto.StatistiquesResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.gestion.adminservice.entity.Utilisateur;
import com.example.gestion.adminservice.dto.LoginRequest;
import com.example.gestion.adminservice.dto.LoginResponse;
import com.example.gestion.adminservice.service.AdminService;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // ✅ Pour les tests frontend
public class AdminAuthController {

    private final AdminService adminService;

    public AdminAuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ✅ Endpoint d'authentification
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = adminService.authentifierAdmin(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(false, "Erreur lors de l'authentification: " + e.getMessage()));
        }
    }

    // ✅ Endpoint de création d'admin (pour les tests)
    @PostMapping("/creer")
    public ResponseEntity<?> creerAdmin(@RequestBody Utilisateur admin) {
        try {
            Utilisateur nouvelAdmin = adminService.creerAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(false, e.getMessage()));
        }
    }

    // ✅ Endpoint pour vérifier si un admin existe
    @GetMapping("/existe/{email}")
    public ResponseEntity<Boolean> adminExiste(@PathVariable String email) {
        boolean existe = adminService.adminExists(email);
        return ResponseEntity.ok(existe);
    }

    // ✅ Endpoint pour récupérer les infos d'un admin par email
    @GetMapping("/{email}")
    public ResponseEntity<?> getAdminByEmail(@PathVariable String email) {
        Optional<Utilisateur> admin = adminService.getAdminByEmail(email);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponse(false, "Admin non trouvé"));
        }
    }

    // ✅ Endpoint de santé
    @GetMapping("/sante")
    public ResponseEntity<String> sante() {
        return ResponseEntity.ok("Service Admin opérationnel");
    }

    @GetMapping("/statistiques")
    public ResponseEntity<StatistiquesResponse> getStatistiques() {
        StatistiquesResponse stats = adminService.getStatistiques();
        return ResponseEntity.ok(stats);
    }
}