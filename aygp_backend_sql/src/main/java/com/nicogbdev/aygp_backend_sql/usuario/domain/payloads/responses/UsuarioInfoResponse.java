package com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class UsuarioInfoResponse extends UsuarioResponse{
    // Propiedades.
    private Long id;
    private String username;

    private String email;

    private String nombre;

    private String apellidos;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaNacimiento;

    private String ciudadNacimiento;

    private List<String> roles;

    // Constructor


    public UsuarioInfoResponse() {
    }

    public UsuarioInfoResponse(Long id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }


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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }
}
