package com.nicogbdev.aygp_backend_sql.recordatorio.application.service;

import com.nicogbdev.aygp_backend_sql.exceptions.RecordatorioNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.dto.RecordatorioDTO;

import java.util.List;

public interface RecordatorioService {
    List<RecordatorioDTO> obtenerRecordatorios();
    List<RecordatorioDTO> obtenerRecordatoriosUsuario(Long id);
    List<RecordatorioDTO> obtenerRecordatoriosUsuario(String username);
    RecordatorioDTO obtenerRecordatorio(Long idRecordatorio) throws RecordatorioNotFoundException;
    RecordatorioDTO obtenerRecordatorio(String username, Long idRecordatorio) throws RecordatorioNotFoundException;
    RecordatorioDTO crearRecordatorio(RecordatorioDTO recordatorioDTO);
    RecordatorioDTO crearRecordatorio(String username, RecordatorioDTO recordatorioDTO) throws UsuarioNotFoundException;
    RecordatorioDTO modificarRecordatorio(Long id, RecordatorioDTO recordatorioDTO) throws RecordatorioNotFoundException;
    RecordatorioDTO modificarRecordatorio(String username, Long id, RecordatorioDTO recordatorioDTO) throws RecordatorioNotFoundException, UsuarioNotFoundException, SinPermisoException;
    RecordatorioDTO cambiarRealizado(Long id) throws RecordatorioNotFoundException;
    RecordatorioDTO cambiarRealizado(String username, Long id) throws RecordatorioNotFoundException, UsuarioNotFoundException, SinPermisoException;
    void eliminarRecordatorio(Long idRecordatorio);
    void eliminarRecordatorio(String username, Long idRecordatorio) throws UsuarioNotFoundException, RecordatorioNotFoundException, SinPermisoException;
}
