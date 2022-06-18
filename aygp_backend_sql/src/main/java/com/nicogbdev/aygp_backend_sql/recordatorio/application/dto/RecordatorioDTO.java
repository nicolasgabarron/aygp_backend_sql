package com.nicogbdev.aygp_backend_sql.recordatorio.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class RecordatorioDTO {
    // Propiedades.
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String detalle;
    private Date fechaCreacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+2")
    private Date fechaRecordatorio;
    private Boolean realizado;

    // Constructor.

    public RecordatorioDTO() {
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFechaRecordatorio(Date fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public Boolean getRealizado() {
        return realizado;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }


    // Métodos.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordatorioDTO that = (RecordatorioDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(usuarioId, that.usuarioId) && Objects.equals(titulo, that.titulo) && Objects.equals(detalle, that.detalle) && Objects.equals(fechaCreacion, that.fechaCreacion) && Objects.equals(fechaRecordatorio, that.fechaRecordatorio) && Objects.equals(realizado, that.realizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuarioId, titulo, detalle, fechaCreacion, fechaRecordatorio, realizado);
    }

    @Override
    public String toString() {
        return "RecordatorioDTO{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", titulo='" + titulo + '\'' +
                ", detalles='" + detalle + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaRecordatorio=" + fechaRecordatorio +
                ", realizado=" + realizado +
                '}';
    }
}
