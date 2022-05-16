package com.nicogbdev.aygp_backend_sql.entrada_diario.application.service;

import com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto.EntradaDiarioDTO;
import com.nicogbdev.aygp_backend_sql.exceptions.EntradaDiarioNotFound;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;

import java.util.List;

public interface EntradaDiarioService {
    List<EntradaDiarioDTO> obtenerEntradasDiarioUsuario(Long id);
    List<EntradaDiarioDTO> obtenerEntradasDiarioUsuario(String nombreUsuario);
    EntradaDiarioDTO obtenerEntradaDiario(Long idEntradaDiario) throws EntradaDiarioNotFound;
    EntradaDiarioDTO obtenerEntradaDiario(String nombreUsuario, Long idEntradaDiario) throws EntradaDiarioNotFound;
    EntradaDiarioDTO crearEntradaDiario(EntradaDiarioDTO entradaDiarioDTO);
    EntradaDiarioDTO crearEntradaDiario(String username, EntradaDiarioDTO entradaDiarioDTO) throws UsuarioNotFoundException;
    void eliminarEntradaDiario(Long idEntradaDiario);
    void eliminarEntradaDiario(String username, Long idEntradaDiario) throws UsuarioNotFoundException, EntradaDiarioNotFound, SinPermisoException;


}
