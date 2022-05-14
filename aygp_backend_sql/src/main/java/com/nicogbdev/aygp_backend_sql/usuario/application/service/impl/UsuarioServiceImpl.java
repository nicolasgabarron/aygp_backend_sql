package com.nicogbdev.aygp_backend_sql.usuario.application.service.impl;

import com.nicogbdev.aygp_backend_sql.usuario.application.service.UsuarioService;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    // Inyeción de dependencias.
    // TODO: Utilizar Mapper cuando se haya implementado DTOs.
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario;
    }



    @Override
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioPorEmail = usuarioRepository.findByEmail(email);

        return usuarioPorEmail;
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorUsername(String username) {
        Optional<Usuario> usuarioPorUsername = usuarioRepository.findByUsername(username);

        return usuarioPorUsername;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        usuario = usuarioRepository.save(usuario);

        return usuario;
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
