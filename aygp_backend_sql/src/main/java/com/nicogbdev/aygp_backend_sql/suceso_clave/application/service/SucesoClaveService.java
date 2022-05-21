package com.nicogbdev.aygp_backend_sql.suceso_clave.application.service;

import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.SucesoClaveNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.dto.SucesoClaveDTO;

import java.util.List;

public interface SucesoClaveService {
    List<SucesoClaveDTO> obtenerSucesosClave();
    List<SucesoClaveDTO> obtenerSucesosClaveUsuario(Long id);
    List<SucesoClaveDTO> obtenerSucesosClaveUsuario(String username);
    SucesoClaveDTO obtenerSucesoClave(Long idSuceso) throws SucesoClaveNotFoundException;
    SucesoClaveDTO obtenerSucesoClave(String username, Long idSuceso) throws SucesoClaveNotFoundException;
    SucesoClaveDTO crearSucesoClave(SucesoClaveDTO sucesoClaveDTO);
    SucesoClaveDTO crearSucesoClave(String username, SucesoClaveDTO sucesoClaveDTO) throws UsuarioNotFoundException;
    void eliminarSucesoClave(Long idSuceso);
    void eliminarSucesoClave(String username, Long idSuceso) throws UsuarioNotFoundException, SucesoClaveNotFoundException, SinPermisoException;
}
