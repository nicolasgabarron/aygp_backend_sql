package com.nicogbdev.aygp_backend_sql.suceso_clave.application.service.impl;

import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.SucesoClaveNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.mapper.dto.SucesoClaveDTO;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.mapper.SucesoClaveMapper;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.service.SucesoClaveService;
import com.nicogbdev.aygp_backend_sql.suceso_clave.domain.entity.SucesoClave;
import com.nicogbdev.aygp_backend_sql.suceso_clave.infrastructure.repository.SucesoClaveRepository;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SucesoClaveServiceImpl implements SucesoClaveService {
    // Inyección de dependencias.
    private final SucesoClaveRepository sucesoClaveRepository;
    private final SucesoClaveMapper sucesoClaveMapper;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SucesoClaveServiceImpl(SucesoClaveRepository sucesoClaveRepository, SucesoClaveMapper sucesoClaveMapper, UsuarioRepository usuarioRepository) {
        this.sucesoClaveRepository = sucesoClaveRepository;
        this.sucesoClaveMapper = sucesoClaveMapper;
        this.usuarioRepository = usuarioRepository;
    }

    // Fines administrativos.
    @Override
    public List<SucesoClaveDTO> obtenerSucesosClave() {
        List<SucesoClave> sucesosClave = sucesoClaveRepository.findAll();

        return sucesoClaveMapper.toDto(sucesosClave);
    }

    @Override
    public List<SucesoClaveDTO> obtenerSucesosClaveUsuario(Long id) {
        List<SucesoClave> allByUsuario_id = sucesoClaveRepository.findAllByUsuario_Id(id);

        return sucesoClaveMapper.toDto(allByUsuario_id);
    }

    @Override
    public List<SucesoClaveDTO> obtenerSucesosClaveUsuario(String username) {
        List<SucesoClave> allByUsuario_username = sucesoClaveRepository.findAllByUsuario_Username(username);

        return sucesoClaveMapper.toDto(allByUsuario_username);
    }

    @Override
    public SucesoClaveDTO obtenerSucesoClave(Long idSuceso) throws SucesoClaveNotFoundException {
        return sucesoClaveRepository.findById(idSuceso)
                .map(sucesoClave -> sucesoClaveMapper.toDto(sucesoClave))
                .orElseThrow(() -> new SucesoClaveNotFoundException("El suceso clave no ha sido encontrado."));
    }

    @Override
    public SucesoClaveDTO obtenerSucesoClave(String username, Long idSuceso) throws SucesoClaveNotFoundException {
        SucesoClave byIdAndUsuario_username = sucesoClaveRepository.findByIdAndUsuario_Username(idSuceso, username);

        if (byIdAndUsuario_username == null) {
            throw new SucesoClaveNotFoundException("El suceso clave no ha sido encontrado.");
        }

        return sucesoClaveMapper.toDto(byIdAndUsuario_username);
    }

    @Override
    public SucesoClaveDTO crearSucesoClave(SucesoClaveDTO sucesoClaveDTO) {
        SucesoClave savedSuceso = sucesoClaveRepository.save(sucesoClaveMapper.toEntity(sucesoClaveDTO));

        return sucesoClaveMapper.toDto(savedSuceso);
    }

    @Override
    public SucesoClaveDTO crearSucesoClave(String username, SucesoClaveDTO sucesoClaveDTO) throws UsuarioNotFoundException {
        // Obtengo el usuario.
        Usuario usuarioSuceso = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no ha sido encontrado"));

        // Defino el ID del usuario y la fecha de creación del suceso.
        sucesoClaveDTO.setUsuarioId(usuarioSuceso.getId());
        sucesoClaveDTO.setFechaCreacion(new Date());

        if (sucesoClaveDTO.getFechaSuceso() == null) {
            sucesoClaveDTO.setFechaSuceso(new Date());
        }

        // Guardo el Suceso Clave en BBDD.
        SucesoClave savedSuceso = sucesoClaveRepository.save(sucesoClaveMapper.toEntity(sucesoClaveDTO));

        return sucesoClaveMapper.toDto(savedSuceso);
    }

    @Override
    public void eliminarSucesoClave(Long idSuceso) {
        sucesoClaveRepository.deleteById(idSuceso);
    }

    @Override
    public void eliminarSucesoClave(String username, Long idSuceso) throws UsuarioNotFoundException, SucesoClaveNotFoundException, SinPermisoException {
        // Obtengo el usuario.
        Usuario usuarioSuceso = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no ha sido encontrado."));

        // Obtengo el suceso clave.
        SucesoClave sucesoClave = sucesoClaveRepository.findById(idSuceso)
                .orElseThrow(() -> new SucesoClaveNotFoundException("El suceso clave no ha sido encontrado."));

        // Compruebo si el Suceso Clave pertenece al usuario que solicita la eliminación.
        if(usuarioSuceso.getId().equals(sucesoClave.getUsuario().getId()))
            sucesoClaveRepository.deleteById(idSuceso);
        else throw new SinPermisoException("No tienes permiso para eliminar ese Suceso Clave.");
    }
}
