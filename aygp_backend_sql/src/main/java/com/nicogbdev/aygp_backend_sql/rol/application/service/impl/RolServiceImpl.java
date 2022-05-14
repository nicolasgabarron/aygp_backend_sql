package com.nicogbdev.aygp_backend_sql.rol.application.service.impl;

import com.nicogbdev.aygp_backend_sql.rol.application.service.RolService;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.ERol;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.Rol;
import com.nicogbdev.aygp_backend_sql.rol.infrastructure.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RolServiceImpl implements RolService {

    // Inyección de dependencias.
    // TODO: Posteriormente inyectar el mapper.
    private RolRepository rolRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> obtenerRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles;
    }

    @Override
    public Optional<Rol> obtenerRolPorId(Integer id) {
        Optional<Rol> rolPorId = rolRepository.findById(id);

        return rolPorId;
    }

    @Override
    public Optional<Rol> obtenerRolPorNombre(ERol nombre) {
        Optional<Rol> rolPorNombre = rolRepository.findByNombre(nombre);

        return rolPorNombre;
    }

    @Override
    public Rol crearRol(Rol rol) {
        rol = rolRepository.save(rol);

        return rol;
    }

    @Override
    public void eliminarRol(Integer rol) {
        rolRepository.deleteById(rol);
    }
}
