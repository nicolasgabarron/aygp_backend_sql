package com.nicogbdev.aygp_backend_sql.entrada_diario.application.service.impl;

import com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto.EntradaDiarioDTO;
import com.nicogbdev.aygp_backend_sql.entrada_diario.application.mapper.EntradaDiarioMapper;
import com.nicogbdev.aygp_backend_sql.entrada_diario.application.service.EntradaDiarioService;
import com.nicogbdev.aygp_backend_sql.entrada_diario.domain.entity.EntradaDiario;
import com.nicogbdev.aygp_backend_sql.entrada_diario.infrastructure.repository.EntradaDiarioRepository;
import com.nicogbdev.aygp_backend_sql.exceptions.EntradaDiarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public EntradaDiarioDTO obtenerEntradaDiario(Long idEntradaDiario) throws EntradaDiarioNotFoundException {
        return entradaDiarioRepository.findById(idEntradaDiario)
                .map(entradaDiario -> entradaDiarioMapper.toDto(entradaDiario))
                .orElseThrow(() -> new EntradaDiarioNotFoundException("La entrada de diario no ha sido encontrada."));
    }

    @Override
    public EntradaDiarioDTO obtenerEntradaDiario(String nombreUsuario, Long idEntradaDiario) throws EntradaDiarioNotFoundException {
        EntradaDiario entradaDiario = entradaDiarioRepository.findByIdAndUsuario_Username(idEntradaDiario, nombreUsuario)
                .orElseThrow(() -> new EntradaDiarioNotFoundException("La entrada del diario no ha sido encontrada."));

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
    public EntradaDiarioDTO modificarEntradaDiario(Long idEntrada, EntradaDiarioDTO entradaDiarioDTO) throws EntradaDiarioNotFoundException {
        EntradaDiario entradaModificar = entradaDiarioRepository.findById(idEntrada)
                .orElseThrow(() -> new EntradaDiarioNotFoundException("La entrada de diario no ha sido encontrada."));

        // Titulo
        entradaModificar.setTitulo(entradaDiarioDTO.getTitulo() == null ? entradaModificar.getTitulo() : entradaDiarioDTO.getTitulo());
        // Contenido
        entradaModificar.setContenido(entradaDiarioDTO.getContenido() == null ? entradaModificar.getContenido() : entradaDiarioDTO.getContenido());

        // Guardo la modificación.
        entradaModificar = entradaDiarioRepository.save(entradaModificar);

        return entradaDiarioMapper.toDto(entradaModificar);
    }

    @Override
    public EntradaDiarioDTO modificarEntradaDiario(String username, Long idEntrada, EntradaDiarioDTO entradaDiarioDTO) throws EntradaDiarioNotFoundException, UsuarioNotFoundException, SinPermisoException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no ha sido encontrado."));

        EntradaDiario entradaModificar = entradaDiarioRepository.findById(idEntrada)
                .orElseThrow(() -> new EntradaDiarioNotFoundException("La entrada de diario no ha sido encontrada."));

        // Comprobación de si pertenece la entrada a modificar al usuario que ha lanzado la petición.
        if(entradaModificar.getUsuario().getId().equals(usuario.getId())){
            // Titulo
            entradaModificar.setTitulo(entradaDiarioDTO.getTitulo() == null ? entradaModificar.getTitulo() : entradaDiarioDTO.getTitulo());
            // Contenido
            entradaModificar.setContenido(entradaDiarioDTO.getContenido() == null ? entradaModificar.getContenido() : entradaDiarioDTO.getContenido());

            // Guardo la modificación.
            entradaModificar = entradaDiarioRepository.save(entradaModificar);

            return entradaDiarioMapper.toDto(entradaModificar);
        }else {
            throw new SinPermisoException("No tienes permiso para modificar esa entrada de diario.");
        }
    }

    @Override
    public void eliminarEntradaDiario(Long idEntradaDiario) {
        entradaDiarioRepository.deleteById(idEntradaDiario);
    }

    @Override
    public void eliminarEntradaDiario(String username, Long idEntradaDiario) throws UsuarioNotFoundException, EntradaDiarioNotFoundException, SinPermisoException {
        // Obtengo el usuario para comprobar que es de su autoría la entrada que quiere eliminar.
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no ha sido encontrado."));

        EntradaDiario entradaDiario = entradaDiarioRepository.findById(idEntradaDiario)
                .orElseThrow(() -> new EntradaDiarioNotFoundException("La entrada de diario no ha sido encontrada."));

        if (usuario.getId().equals(entradaDiario.getUsuario().getId()))
            entradaDiarioRepository.deleteById(idEntradaDiario);
        else throw new SinPermisoException("No tienes permiso para eliminar esa entrada de diario.");
    }
}
