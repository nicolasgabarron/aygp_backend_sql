package com.nicogbdev.aygp_backend_sql.usuario.application.service;

import com.nicogbdev.aygp_backend_sql.usuario.application.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<UsuarioDTO> obtenerUsuarios();
    Optional<UsuarioDTO> obtenerUsuarioPorId(Long id);
    void eliminarUsuario(Long id);
}
