package com.nicogbdev.aygp_backend_sql.recordatorio.infrastructure.rest;

import com.nicogbdev.aygp_backend_sql.exceptions.RecordatorioNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.dto.RecordatorioDTO;
import com.nicogbdev.aygp_backend_sql.recordatorio.application.service.RecordatorioService;
import com.nicogbdev.aygp_backend_sql.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recordatorios")
@PreAuthorize("hasRole('USUARIO') or hasRole('ESPECIALISTA') or hasRole('ADMINISTRADOR')")
public class RecordatorioRestController {
    // Inyección de dependencias.
    private final RecordatorioService recordatorioService;
    private final JwtUtils jwtUtils;

    @Autowired
    public RecordatorioRestController(RecordatorioService recordatorioService, JwtUtils jwtUtils) {
        this.recordatorioService = recordatorioService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoints.
    @GetMapping(value = "/todos", produces = "application/json")
    public ResponseEntity<List<RecordatorioDTO>> obtenerRecordatoriosUsuario(@CookieValue(value = "nicogbdev_jwt") String jwt) {
        String usuario = jwtUtils.getUserNameFromJwtToken(jwt);
        List<RecordatorioDTO> recordatorioDTOS = recordatorioService.obtenerRecordatoriosUsuario(usuario);

        return new ResponseEntity<>(recordatorioDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{idRecordatorio}")
    public  ResponseEntity<RecordatorioDTO> obtenerRecordatorioUsuarioPorId(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                                            @PathVariable Long idRecordatorio){
        String usuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            RecordatorioDTO recordatorioDTO = recordatorioService.obtenerRecordatorio(usuario, idRecordatorio);

            return new ResponseEntity<>(recordatorioDTO, HttpStatus.OK);
        } catch (RecordatorioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/nuevo", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RecordatorioDTO> crearRecordatorio(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                             @RequestBody RecordatorioDTO recordatorioDTO){
        String usuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            RecordatorioDTO savedRecordatorio = recordatorioService.crearRecordatorio(usuario, recordatorioDTO);

            return new ResponseEntity<>(savedRecordatorio, HttpStatus.CREATED);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/modificar/{idRecordatorio}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RecordatorioDTO> modificarRecordatorio(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                                 @PathVariable Long idRecordatorio,
                                                                 @RequestBody RecordatorioDTO recordatorioDTO){
        String usuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            RecordatorioDTO modRecordatorio = recordatorioService.modificarRecordatorio(usuario, idRecordatorio, recordatorioDTO);

            return new ResponseEntity<>(modRecordatorio, HttpStatus.OK);
        } catch (RecordatorioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SinPermisoException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping(value = "/realizado/{idRecordatorio}", produces = "application/json")
    public ResponseEntity<RecordatorioDTO> cambiarEstadoRecordatorio(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                                     @PathVariable Long idRecordatorio){
        String usuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            RecordatorioDTO recordatorioDTO = recordatorioService.cambiarRealizado(usuario, idRecordatorio);

            return new ResponseEntity<>(recordatorioDTO, HttpStatus.OK);
        } catch (RecordatorioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SinPermisoException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/eliminar/{idRecordatorio}")
    public ResponseEntity<Void> eliminarRecordatorio(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                     @PathVariable Long idRecordatorio){
        String usuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            recordatorioService.eliminarRecordatorio(usuario, idRecordatorio);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RecordatorioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SinPermisoException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
