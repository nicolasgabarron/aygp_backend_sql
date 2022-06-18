package com.nicogbdev.aygp_backend_sql.suceso_clave.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "sucesos_clave")
public class SucesoClave {
    // Propiedades.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUCESOS_CLAVE_SEQUENCE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "fecha_suceso")
    private Date fechaSuceso;

    @Column(name = "titulo", nullable = false)
    @Size(min = 3, max =  500)
    private String titulo;

    @Column(name = "contenido", nullable = false)
    @Size(min = 3, max = 200000)
    private String contenido;

    @Column(name = "valoracion", nullable = false)
    private @Min(value = 0) @Max(value = 10) Double valoracion;


    // Getters y setters.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaSuceso() {
        return fechaSuceso;
    }

    public void setFechaSuceso(Date fechaSuceso) {
        this.fechaSuceso = fechaSuceso;
    }
}
