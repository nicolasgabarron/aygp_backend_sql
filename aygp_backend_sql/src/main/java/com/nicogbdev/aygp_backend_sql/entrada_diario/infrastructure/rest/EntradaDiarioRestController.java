package com.nicogbdev.aygp_backend_sql.entrada_diario.infrastructure.rest;

import com.nicogbdev.aygp_backend_sql.entrada_diario.application.dto.EntradaDiarioDTO;
import com.nicogbdev.aygp_backend_sql.entrada_diario.application.service.EntradaDiarioService;
import com.nicogbdev.aygp_backend_sql.entrada_diario.domain.entity.EntradaDiario;
import com.nicogbdev.aygp_backend_sql.exceptions.EntradaDiarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diario")
@PreAuthorize("hasRole('USUARIO') or hasRole('ESPECIALISTA') or hasRole('ADMINISTRADOR')")
public class EntradaDiarioRestController {
    // Inyección de dependencias.
    EntradaDiarioService entradaDiarioService;

    JwtUtils jwtUtils;

    @Autowired
    public EntradaDiarioRestController(EntradaDiarioService entradaDiarioService, JwtUtils jwtUtils) {
        this.entradaDiarioService = entradaDiarioService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoints.

    @GetMapping(value = "/entradas", produces = "application/json")
    public ResponseEntity<List<EntradaDiarioDTO>> obtenerEntradasDiarioUsuario(@CookieValue(value = "nicogbdev_jwt") String jwt){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        List<EntradaDiarioDTO> entradaDiarioDTOS = entradaDiarioService.obtenerEntradasDiarioUsuario(nombreUsuario);

        return new ResponseEntity<>(entradaDiarioDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/entradas/{idEntrada}", produces = "application/json")
    public ResponseEntity<EntradaDiarioDTO> obtenerEntradaPorId(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                                @PathVariable Long idEntrada){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);
        try {
            EntradaDiarioDTO entradaDiarioDTO = entradaDiarioService.obtenerEntradaDiario(nombreUsuario, idEntrada);

            return new ResponseEntity<>(entradaDiarioDTO, HttpStatus.OK);
        } catch (EntradaDiarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/entradas/nueva", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EntradaDiarioDTO> crearEntrada(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                         @RequestBody EntradaDiarioDTO entradaDiarioDTO) {
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            EntradaDiarioDTO entradaDiario = entradaDiarioService.crearEntradaDiario(nombreUsuario, entradaDiarioDTO);

            return new ResponseEntity<>(entradaDiario, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/entradas/modificar/{idEntrada}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<EntradaDiarioDTO> modificarEntrada(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                             @PathVariable Long idEntrada,
                                                             @RequestBody EntradaDiarioDTO entradaDiarioDTO){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            EntradaDiarioDTO entradaModificada = entradaDiarioService.modificarEntradaDiario(nombreUsuario, idEntrada, entradaDiarioDTO);

            return new ResponseEntity<>(entradaModificada, HttpStatus.OK);
        } catch (SinPermisoException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (EntradaDiarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/entradas/eliminar/{idEntrada}")
    public ResponseEntity<Void> eliminarEntrada(@CookieValue(value = "nicogbdev_jwt") String jwt, @PathVariable Long idEntrada){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            entradaDiarioService.eliminarEntradaDiario(nombreUsuario, idEntrada);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UsuarioNotFoundException e) {
            // En caso de no conseguir recuperar el usuario...
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EntradaDiarioNotFoundException e) {
            // En caso de no conseguir recuperar la entrada de diario...
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SinPermisoException e) {
            // En caso de que la entrada de diario no pertenezca a ese usuario...
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
