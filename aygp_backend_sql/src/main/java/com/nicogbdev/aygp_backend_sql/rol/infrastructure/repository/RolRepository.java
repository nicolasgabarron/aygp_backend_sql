package com.nicogbdev.aygp_backend_sql.rol.infrastructure.repository;

import com.nicogbdev.aygp_backend_sql.rol.domain.entity.ERol;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(ERol nombre);
}
