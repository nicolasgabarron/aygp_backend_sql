package com.nicogbdev.aygp_backend_sql.suceso_clave.application.mapper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class SucesoClaveDTO {
    // Propiedades.
    private Long id;
    private Long usuarioId;
    private Date fechaCreacion;
    private Date fechaSuceso;
    private String titulo;
    private String contenido;
    private Double valoracion;

    // Constructor por defecto.
    public SucesoClaveDTO() {
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public Date getFechaSuceso() {
        return fechaSuceso;
    }

    public void setFechaSuceso(Date fechaSuceso) {
        this.fechaSuceso = fechaSuceso;
    }

    // Métodos.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SucesoClaveDTO that = (SucesoClaveDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(usuarioId, that.usuarioId) && Objects.equals(fechaCreacion, that.fechaCreacion) && Objects.equals(titulo, that.titulo) && Objects.equals(contenido, that.contenido) && Objects.equals(valoracion, that.valoracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, fechaCreacion, titulo, contenido, valoracion);
    }

    @Override
    public String toString() {
        return "SucesoClaveDTO{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", fechaCreacion=" + fechaCreacion +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }
}
