package com.nicogbdev.aygp_backend_sql.entrada_diario.application.mapper;

import com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto.EntradaDiarioDTO;
import com.nicogbdev.aygp_backend_sql.entrada_diario.domain.entity.EntradaDiario;
import com.nicogbdev.aygp_backend_sql.mapper.EntityMapper;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntradaDiarioMapper extends EntityMapper<EntradaDiarioDTO, EntradaDiario> {

    default EntradaDiario fromId(Long id){
        if (id == null) {
            return null;
        }

        EntradaDiario entradaDiario = new EntradaDiario();
        entradaDiario.setId(id);
        return entradaDiario;
    }

    default Usuario map(Long id){
        if (id == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }

    @Override
    @Mapping(source = "usuarioId", target = "usuario")
    EntradaDiario toEntity(EntradaDiarioDTO dto);

    @Override
    @Mapping(source = "usuario.id", target = "usuarioId")
    EntradaDiarioDTO toDto(EntradaDiario entity);
}
