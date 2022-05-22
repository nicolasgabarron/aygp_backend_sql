package com.nicogbdev.aygp_backend_sql.usuario.application.mapper;

import com.nicogbdev.aygp_backend_sql.mapper.EntityMapper;
import com.nicogbdev.aygp_backend_sql.usuario.application.dto.UsuarioDTO;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {
    default Usuario fromId(Long id){
        if (id == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }
}
