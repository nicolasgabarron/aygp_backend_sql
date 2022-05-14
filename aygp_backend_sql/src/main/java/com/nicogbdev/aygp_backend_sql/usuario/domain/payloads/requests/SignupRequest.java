package com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.requests;

public class SignupRequest {
    // Propiedades.
    private String username;
    private String email;
    private String password;

    // Getters y setters.

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
