package com.nicogbdev.aygp_backend_sql.recordatorio.application.service.impl;

import com.nicogbdev.aygp_backend_sql.exceptions.RecordatorioNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.dto.RecordatorioDTO;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.mapper.RecordatorioMapper;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.service.RecordatorioService;
import com.nicogbdev.aygp_backend_sql.recordatorio.domain.entity.Recordatorio;
import com.nicogbdev.aygp_backend_sql.recordatorio.infrastructure.repository.RecordatorioRepository;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecordatorioServiceImpl implements RecordatorioService {
    // Inyección de dependencias.
    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecordatorioMapper recordatorioMapper;

    @Autowired
    public RecordatorioServiceImpl(RecordatorioRepository recordatorioRepository, UsuarioRepository usuarioRepository, RecordatorioMapper recordatorioMapper) {
        this.recordatorioRepository = recordatorioRepository;
        this.usuarioRepository = usuarioRepository;
        this.recordatorioMapper = recordatorioMapper;
    }

    // Métodos.
    @Override
    public List<RecordatorioDTO> obtenerRecordatorios() {
        List<Recordatorio> recordatorios = recordatorioRepository.findAll();
        return recordatorioMapper.toDto(recordatorios);
    }

    @Override
    public List<RecordatorioDTO> obtenerRecordatoriosUsuario(Long id) {
        List<Recordatorio> allByUsuario_id = recordatorioRepository.findAllByUsuario_Id(id);
        return recordatorioMapper.toDto(allByUsuario_id);
    }

    @Override
    public List<RecordatorioDTO> obtenerRecordatoriosUsuario(String username) {
        List<Recordatorio> allByUsuario_username = recordatorioRepository.findAllByUsuario_Username(username);
        return recordatorioMapper.toDto(allByUsuario_username);
    }

    @Override
    public RecordatorioDTO obtenerRecordatorio(Long idRecordatorio) throws RecordatorioNotFoundException {
        Recordatorio recordatorio = recordatorioRepository.findById(idRecordatorio)
                .orElseThrow(() -> new RecordatorioNotFoundException("El recordatorio no ha sido encontrado."));

        return recordatorioMapper.toDto(recordatorio);
    }

    @Override
    public RecordatorioDTO obtenerRecordatorio(String username, Long idRecordatorio) throws RecordatorioNotFoundException {
        Recordatorio byIdAndUsuario_username = recordatorioRepository.findByIdAndUsuario_Username(idRecordatorio, username)
                .orElseThrow(() -> new RecordatorioNotFoundException("Recordatorio no encontrado."));

        return recordatorioMapper.toDto(byIdAndUsuario_username);
    }

    @Override
    public RecordatorioDTO crearRecordatorio(RecordatorioDTO recordatorioDTO) {
        Recordatorio savedRecordatorio = recordatorioRepository.save(recordatorioMapper.toEntity(recordatorioDTO));

        return recordatorioMapper.toDto(savedRecordatorio);
    }

    @Override
    public RecordatorioDTO crearRecordatorio(String username, RecordatorioDTO recordatorioDTO) throws UsuarioNotFoundException {
        // Obtengo el usuario.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no se ha encontrado."));

        // Defino el ID del usuario y la fecha de creación del recordatorio.
        recordatorioDTO.setUsuarioId(usuario.getId());
        recordatorioDTO.setFechaCreacion(new Date());

        // Guardo el suceso en BBDD.
        Recordatorio savedRecordatorio = recordatorioRepository.save(recordatorioMapper.toEntity(recordatorioDTO));

        return recordatorioMapper.toDto(savedRecordatorio);
    }

    @Override
    public RecordatorioDTO modificarRecordatorio(Long id, RecordatorioDTO recordatorioDTO) throws RecordatorioNotFoundException {
        Recordatorio recordatorioModificar = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RecordatorioNotFoundException("El recordatorio no ha sido encontrado."));

        // Modificación.
        recordatorioModificar.setTitulo(recordatorioDTO.getTitulo());
        recordatorioModificar.setDetalle(recordatorioDTO.getDetalle());
        recordatorioModificar.setFechaRecordatorio(recordatorioDTO.getFechaRecordatorio());
        recordatorioModificar.setRealizado(recordatorioDTO.getRealizado());

        // Guardo cambios en BBDD.
        recordatorioModificar = recordatorioRepository.save(recordatorioModificar);

        return recordatorioMapper.toDto(recordatorioModificar);
    }

    @Override
    public RecordatorioDTO modificarRecordatorio(String username, Long id, RecordatorioDTO recordatorioDTO) throws RecordatorioNotFoundException, UsuarioNotFoundException {

        // Obtengo el usuario.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado."));

        // Obtengo el recordatorio a modificar
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RecordatorioNotFoundException("Recordatorio no encontrado."));

        // Compruebo si pertenece el recordatorio al usuario.
        if (recordatorio.getUsuario().getId().equals(usuario.getId())){
            recordatorioDTO = modificarRecordatorio(id, recordatorioDTO);
        }

        return recordatorioDTO;
    }

    @Override
    public RecordatorioDTO cambiarRealizado(Long id) throws RecordatorioNotFoundException {
        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RecordatorioNotFoundException("Recordatorio no encontrado."));

        // Cambio a su opuesto el campo Realizado.
        recordatorio.setRealizado(!recordatorio.getRealizado());

        return recordatorioMapper.toDto(recordatorio);
    }

    @Override
    public RecordatorioDTO cambiarRealizado(String username, Long id) throws RecordatorioNotFoundException, UsuarioNotFoundException {

        // Obtengo el usuario.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado."));

        Recordatorio recordatorio = recordatorioRepository.findById(id)
                .orElseThrow(() -> new RecordatorioNotFoundException("Recordatorio no encontrado."));

        if (recordatorio.getUsuario().getId().equals(usuario.getId())){
            // Cambio a su opuesto el campo Realizado.
            recordatorio.setRealizado(!recordatorio.getRealizado());
        }

        return recordatorioMapper.toDto(recordatorio);
    }

    @Override
    public void eliminarRecordatorio(Long idRecordatorio) {
        recordatorioRepository.deleteById(idRecordatorio);
    }

    @Override
    public void eliminarRecordatorio(String username, Long idRecordatorio) throws UsuarioNotFoundException, RecordatorioNotFoundException {

        // Obtengo el usuario.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado."));

        // Obtengo el recordatorio a modificar
        Recordatorio recordatorio = recordatorioRepository.findById(idRecordatorio)
                .orElseThrow(() -> new RecordatorioNotFoundException("Recordatorio no encontrado."));

        // Compruebo si pertenece el recordatorio al usuario.
        if (recordatorio.getUsuario().getId().equals(usuario.getId())){
            recordatorioRepository.deleteById(idRecordatorio);
        }
    }
}
