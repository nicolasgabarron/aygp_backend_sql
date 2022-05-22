package com.nicogbdev.aygp_backend_sql.suceso_clave.application.service;

import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.SucesoClaveNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.mapper.dto.SucesoClaveDTO;

import java.util.List;

public interface SucesoClaveService {
    List<SucesoClaveDTO> obtenerSucesosClave(); // Administrador
    List<SucesoClaveDTO> obtenerSucesosClaveUsuario(Long id); // Usuario, especialista
    List<SucesoClaveDTO> obtenerSucesosClaveUsuario(String username); // Usuario, especialista
    SucesoClaveDTO obtenerSucesoClave(Long idSuceso) throws SucesoClaveNotFoundException; // Administrador
    SucesoClaveDTO obtenerSucesoClave(String username, Long idSuceso) throws SucesoClaveNotFoundException; // Usuario, especialista
    SucesoClaveDTO crearSucesoClave(SucesoClaveDTO sucesoClaveDTO); // Administrador
    SucesoClaveDTO crearSucesoClave(String username, SucesoClaveDTO sucesoClaveDTO) throws UsuarioNotFoundException; // Usuario, especialista
    SucesoClaveDTO modificarSucesoClave(Long id, SucesoClaveDTO sucesoClaveDTO) throws SucesoClaveNotFoundException;
    SucesoClaveDTO modificarSucesoClave(String username, Long id, SucesoClaveDTO sucesoClaveDTO) throws UsuarioNotFoundException, SucesoClaveNotFoundException, SinPermisoException;
    void eliminarSucesoClave(Long idSuceso); //
    void eliminarSucesoClave(String username, Long idSuceso) throws UsuarioNotFoundException, SucesoClaveNotFoundException, SinPermisoException;
}
