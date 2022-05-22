package com.nicogbdev.aygp_backend_sql.usuario.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.Rol;

import java.io.Serializable;
import java.util.*;

public class UsuarioDTO implements Serializable {
    // Propiedades.
    private Long id;
    private String username;
    private String email;
    private String nombre;
    private String apellidos;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fechaNacimiento;
    private String ciudadNacimiento;
    private List<Rol> roles;

    // Constructor por defecto.

    public UsuarioDTO() {
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

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    // Métodos.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(email, that.email)
                && Objects.equals(nombre, that.nombre) && Objects.equals(apellidos, that.apellidos)
                && Objects.equals(fechaNacimiento, that.fechaNacimiento) && Objects.equals(ciudadNacimiento, that.ciudadNacimiento)
                && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, nombre, apellidos, fechaNacimiento, ciudadNacimiento, roles);
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", ciudadNacimiento='" + ciudadNacimiento + '\'' +
                ", roles=" + roles +
                '}';
    }
}
