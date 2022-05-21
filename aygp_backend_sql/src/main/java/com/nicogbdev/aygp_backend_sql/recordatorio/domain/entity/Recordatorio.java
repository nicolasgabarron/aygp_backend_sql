package com.nicogbdev.aygp_backend_sql.recordatorio.domain.entity;

import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "recordatorios")
public class Recordatorio {
    // Propiedades.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECORDATORIOS_SEQUENCE")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "detalle")
    @Size(min = 5, max = 10000)
    private String detalle;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "fecha_recordatorio", nullable = false)
    private Date fechaRecordatorio;
    @Column(name = "realizado")
    private Boolean realizado;

    // Getters y setters.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
}
