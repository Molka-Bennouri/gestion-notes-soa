package com.example.gestion.adminservice.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private Integer adminId;
    private String nomComplet;

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String message, Integer adminId, String nomComplet) {
        this.success = success;
        this.message = message;
        this.adminId = adminId;
        this.nomComplet = nomComplet;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }

    public String getNomComplet() { return nomComplet; }
    public void setNomComplet(String nomComplet) { this.nomComplet = nomComplet; }
}