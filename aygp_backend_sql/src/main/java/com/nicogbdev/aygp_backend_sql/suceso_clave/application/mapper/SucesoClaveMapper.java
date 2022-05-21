package com.nicogbdev.aygp_backend_sql.suceso_clave.application.mapper;

import com.nicogbdev.aygp_backend_sql.mapper.EntityMapper;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.dto.SucesoClaveDTO;
import com.nicogbdev.aygp_backend_sql.suceso_clave.domain.entity.SucesoClave;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SucesoClaveMapper extends EntityMapper<SucesoClaveDTO, SucesoClave> {
    default SucesoClave fromId(Long id){
        if (id == null) {
            return null;
        }

        SucesoClave sucesoClave = new SucesoClave();
        sucesoClave.setId(id);
        return sucesoClave;
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
    SucesoClave toEntity(SucesoClaveDTO dto);

    @Override
    @Mapping(source = "usuario.id", target = "usuarioId")
    SucesoClaveDTO toDto(SucesoClave entity);
}
