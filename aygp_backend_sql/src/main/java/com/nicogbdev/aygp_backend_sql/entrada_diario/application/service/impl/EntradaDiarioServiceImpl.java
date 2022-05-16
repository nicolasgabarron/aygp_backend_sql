package com.nicogbdev.aygp_backend_sql.entrada_diario.application.service.impl;

import com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto.EntradaDiarioDTO;
import com.nicogbdev.aygp_backend_sql.entrada_diario.application.mapper.EntradaDiarioMapper;
import com.nicogbdev.aygp_backend_sql.entrada_diario.application.service.EntradaDiarioService;
import com.nicogbdev.aygp_backend_sql.entrada_diario.domain.entity.EntradaDiario;
import com.nicogbdev.aygp_backend_sql.entrada_diario.infrastructure.repository.EntradaDiarioRepository;
import com.nicogbdev.aygp_backend_sql.exceptions.EntradaDiarioNotFound;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EntradaDiarioServiceImpl implements EntradaDiarioService {

    // Inyección de dependencias.
    EntradaDiarioRepository entradaDiarioRepository;
    UsuarioRepository usuarioRepository;
    EntradaDiarioMapper entradaDiarioMapper;


    @Autowired
    public EntradaDiarioServiceImpl(EntradaDiarioRepository entradaDiarioRepository, UsuarioRepository usuarioRepository, EntradaDiarioMapper entradaDiarioMapper) {
        this.entradaDiarioRepository = entradaDiarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.entradaDiarioMapper = entradaDiarioMapper;
    }




    // Métodos.

    @Override
    public List<EntradaDiarioDTO> obtenerEntradasDiarioUsuario(Long id) {
        List<EntradaDiario> entradasDiario = entradaDiarioRepository.findAllByUsuario_Id(id);

        return entradaDiarioMapper.toDto(entradasDiario);
    }

    @Override
    public List<EntradaDiarioDTO> obtenerEntradasDiarioUsuario(String nombreUsuario) {
        List<EntradaDiario> entradasDiario = entradaDiarioRepository.findAllByUsuario_Username(nombreUsuario);

        return entradaDiarioMapper.toDto(entradasDiario);
    }

    @Override
    public EntradaDiarioDTO obtenerEntradaDiario(Long idEntradaDiario) throws EntradaDiarioNotFound {
        return entradaDiarioRepository.findById(idEntradaDiario)
                .map(entradaDiario -> entradaDiarioMapper.toDto(entradaDiario))
                .orElseThrow(() -> new EntradaDiarioNotFound("La entrada de diario no ha sido encontrada."));
    }

    @Override
    public EntradaDiarioDTO obtenerEntradaDiario(String nombreUsuario, Long idEntradaDiario) throws EntradaDiarioNotFound{
        // TODO: Posible punto problemático. No sé si obtiene correctamente la Entrada de Diario del repositorio.
        EntradaDiario entradaDiario = entradaDiarioRepository.findByIdAndUsuario_Username(idEntradaDiario, nombreUsuario);

        if (entradaDiario == null) {
            throw new EntradaDiarioNotFound("La entrada del diario no ha sido encontrada.");
        }

        return entradaDiarioMapper.toDto(entradaDiario);
    }

    @Override
    public EntradaDiarioDTO crearEntradaDiario(EntradaDiarioDTO entradaDiarioDTO) {
        EntradaDiario entradaDiario = entradaDiarioMapper.toEntity(entradaDiarioDTO);
        EntradaDiario entradaGuardada = entradaDiarioRepository.save(entradaDiario);

        return entradaDiarioMapper.toDto(entradaGuardada);
    }

    @Override
    public EntradaDiarioDTO crearEntradaDiario(String username, EntradaDiarioDTO entradaDiarioDTO) throws UsuarioNotFoundException {
        // Obtengo el usuario.
        Usuario usuarioEntrada = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no ha sido encontrado."));

        // Defino el ID del usuario y la fecha de creación.
        entradaDiarioDTO.setUsuarioId(usuarioEntrada.getId());
        entradaDiarioDTO.setFechaCreacion(new Date());

        // Guardo la entrada del diario.
        EntradaDiario entradaDiario = entradaDiarioMapper.toEntity(entradaDiarioDTO);
        EntradaDiario entradaGuardada = entradaDiarioRepository.save(entradaDiario);

        // Devuelvo la entrada convertida a DTO.
        return entradaDiarioMapper.toDto(entradaGuardada);
    }

    @Override
    public void eliminarEntradaDiario(Long idEntradaDiario) {
        entradaDiarioRepository.deleteById(idEntradaDiario);
    }

    @Override
    public void eliminarEntradaDiario(String username, Long idEntradaDiario) throws UsuarioNotFoundException, EntradaDiarioNotFound, SinPermisoException {
        // Obtengo el usuario para comprobar que es de su autoría la entrada que quiere eliminar.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no ha sido encontrado."));

        EntradaDiario entradaDiario = entradaDiarioRepository.findById(idEntradaDiario)
                .orElseThrow(() -> new EntradaDiarioNotFound("La entrada de diario no ha sido encontrada."));

        if (usuario.getId().equals(entradaDiario.getUsuario().getId()))
            entradaDiarioRepository.deleteById(idEntradaDiario);
        else throw new SinPermisoException("No tienes permiso para eliminar esa entrada de diario.");
    }
}
