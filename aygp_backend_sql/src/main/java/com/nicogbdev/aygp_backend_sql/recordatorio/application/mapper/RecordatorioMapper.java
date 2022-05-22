package com.nicogbdev.aygp_backend_sql.recordatorio.application.mapper;

import com.nicogbdev.aygp_backend_sql.mapper.EntityMapper;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.dto.RecordatorioDTO;
import com.nicogbdev.aygp_backend_sql.recordatorio.domain.entity.Recordatorio;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecordatorioMapper extends EntityMapper<RecordatorioDTO, Recordatorio> {
    default Recordatorio fromId(Long id){
        if (id == null) {
            return null;
        }

        Recordatorio recordatorio = new Recordatorio();
        recordatorio.setId(id);
        return recordatorio;
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
    Recordatorio toEntity(RecordatorioDTO dto);

    @Override
    @Mapping(source = "usuario.id", target = "usuarioId")
    RecordatorioDTO toDto(Recordatorio entity);
}
