package com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository;

import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Boolean existsByNombreUsuario(String nombreUsuario);
    Boolean existsByEmail(String email);
}
