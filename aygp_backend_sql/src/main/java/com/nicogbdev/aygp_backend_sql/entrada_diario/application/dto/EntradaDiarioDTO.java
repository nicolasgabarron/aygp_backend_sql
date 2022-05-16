package com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto;

import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EntradaDiarioDTO implements Serializable {
    // Propiedades.
    private Long id;
    private Long usuarioId;
    private String titulo;
    private Date fechaCreacion;
    private String contenido;

    // Constructor por defecto.

    public EntradaDiarioDTO() {
    }

    // Getters y setters.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    // Métodos.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntradaDiarioDTO that = (EntradaDiarioDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(usuarioId, that.usuarioId) && Objects.equals(titulo, that.titulo) && Objects.equals(fechaCreacion, that.fechaCreacion) && Objects.equals(contenido, that.contenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, titulo, fechaCreacion, contenido);
    }

    @Override
    public String toString() {
        return "EntradaDiarioDTO{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}
