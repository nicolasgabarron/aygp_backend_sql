package com.nicogbdev.aygp_backend_sql.security.services;

import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // Inyección de dependencias.
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

        return UserDetailsImpl.build(usuario);
    }
}
