package com.nicogbdev.aygp_backend_sql.rol.application.service;

import com.nicogbdev.aygp_backend_sql.rol.domain.entity.ERol;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.Rol;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RolService {
    // TODO: Reemplazar por DTOs.

    List<Rol> obtenerRoles();
    Optional<Rol> obtenerRolPorId(Integer id);
    Optional<Rol> obtenerRolPorNombre(ERol nombre);
    Rol crearRol(Rol rol);
    void eliminarRol(Integer rol);

}
