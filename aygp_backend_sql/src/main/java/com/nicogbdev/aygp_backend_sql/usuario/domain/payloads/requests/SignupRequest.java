package com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.requests;

import com.nicogbdev.aygp_backend_sql.rol.domain.entity.Rol;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class SignupRequest {
    // Propiedades.
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    private Set<String> roles;



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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
