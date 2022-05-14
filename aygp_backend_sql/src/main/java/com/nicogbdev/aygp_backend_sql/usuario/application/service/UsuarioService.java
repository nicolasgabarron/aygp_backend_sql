package com.nicogbdev.aygp_backend_sql.usuario.application.service;

import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    // TODO: Utilizar DTOs.
    List<Usuario> obtenerTodosUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    Optional<Usuario> obtenerUsuarioPorEmail(String email);
    Optional<Usuario> obtenerUsuarioPorUsername(String username);
    Usuario crearUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
}
