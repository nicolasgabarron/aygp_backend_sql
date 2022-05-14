package com.nicogbdev.aygp_backend_sql.rol.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    // Propiedades.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private ERol nombre;

    // Constructor por defecto.

    public Rol() {
    }


    // Getters y setters.

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERol getNombre() {
        return nombre;
    }

    public void setNombre(ERol nombre) {
        this.nombre = nombre;
    }
}
