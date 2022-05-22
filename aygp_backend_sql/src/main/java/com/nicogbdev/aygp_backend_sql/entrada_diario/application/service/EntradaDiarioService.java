package com.nicogbdev.aygp_backend_sql.entrada_diario.application.service;

import com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto.EntradaDiarioDTO;
import com.nicogbdev.aygp_backend_sql.exceptions.EntradaDiarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;

import java.util.List;

public interface EntradaDiarioService {
    List<EntradaDiarioDTO> obtenerEntradasDiarioUsuario(Long id);
    List<EntradaDiarioDTO> obtenerEntradasDiarioUsuario(String nombreUsuario);
    EntradaDiarioDTO obtenerEntradaDiario(Long idEntradaDiario) throws EntradaDiarioNotFoundException;
    EntradaDiarioDTO obtenerEntradaDiario(String nombreUsuario, Long idEntradaDiario) throws EntradaDiarioNotFoundException;
    EntradaDiarioDTO crearEntradaDiario(EntradaDiarioDTO entradaDiarioDTO);
    EntradaDiarioDTO crearEntradaDiario(String username, EntradaDiarioDTO entradaDiarioDTO) throws UsuarioNotFoundException;
    EntradaDiarioDTO modificarEntradaDiario(Long idEntrada, EntradaDiarioDTO entradaDiarioDTO) throws EntradaDiarioNotFoundException;
    EntradaDiarioDTO modificarEntradaDiario(String username, Long idEntrada, EntradaDiarioDTO entradaDiarioDTO) throws EntradaDiarioNotFoundException, UsuarioNotFoundException, SinPermisoException;
    void eliminarEntradaDiario(Long idEntradaDiario);
    void eliminarEntradaDiario(String username, Long idEntradaDiario) throws UsuarioNotFoundException, EntradaDiarioNotFoundException, SinPermisoException;


}
