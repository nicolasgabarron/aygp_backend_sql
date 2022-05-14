package com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.responses;

import java.util.List;

public class UserInfoResponse {
    // Propiedades.
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    // Getters y setters.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
