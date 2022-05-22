package com.nicogbdev.aygp_backend_sql.suceso_clave.infrastructure.rest;

import com.nicogbdev.aygp_backend_sql.exceptions.SinPermisoException;
import com.nicogbdev.aygp_backend_sql.exceptions.SucesoClaveNotFoundException;
import com.nicogbdev.aygp_backend_sql.exceptions.UsuarioNotFoundException;
import com.nicogbdev.aygp_backend_sql.security.jwt.JwtUtils;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.mapper.dto.SucesoClaveDTO;
import com.nicogbdev.aygp_backend_sql.suceso_clave.application.service.SucesoClaveService;
import com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/sclave")
@PreAuthorize("hasRole('USUARIO') or hasRole('ESPECIALISTA') or hasRole('ADMINISTRADOR')")
public class SucesoClaveRestController {
    // Inyección de dependencias.
    SucesoClaveService sucesoClaveService;
    JwtUtils jwtUtils;

    @Autowired
    public SucesoClaveRestController(SucesoClaveService sucesoClaveService, JwtUtils jwtUtils) {
        this.sucesoClaveService = sucesoClaveService;
        this.jwtUtils = jwtUtils;
    }

    // Endpoints.
    @GetMapping(value = "/sucesos", produces = "application/json")
    public ResponseEntity<List<SucesoClaveDTO>> obtenerSucesosClaveUsuario(@CookieValue(value = "nicogbdev_jwt") String jwt){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        List<SucesoClaveDTO> sucesoClaveDTOS = sucesoClaveService.obtenerSucesosClaveUsuario(nombreUsuario);

        return new ResponseEntity<>(sucesoClaveDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/sucesos/{idSuceso}", produces = "application/json")
    public ResponseEntity<SucesoClaveDTO> obtenerSucesoClavePorId(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                                  @PathVariable Long idSuceso){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            SucesoClaveDTO sucesoClaveDTO = sucesoClaveService.obtenerSucesoClave(nombreUsuario, idSuceso);

            return new ResponseEntity<>(sucesoClaveDTO, HttpStatus.OK);
        } catch (SucesoClaveNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/sucesos", produces = "application/json", consumes = "application/json")
    public ResponseEntity<SucesoClaveDTO> crearSucesoClave(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                           @RequestBody SucesoClaveDTO sucesoClaveDTO){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            SucesoClaveDTO sucesoGuardar = sucesoClaveService.crearSucesoClave(nombreUsuario, sucesoClaveDTO);

            return new ResponseEntity<>(sucesoGuardar, HttpStatus.CREATED);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/sucesos/{idSuceso}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<SucesoClaveDTO> modificarSucesoClave(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                              @PathVariable Long idSuceso,
                                                              @RequestBody SucesoClaveDTO sucesoClaveDTO){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            SucesoClaveDTO modifiedSucesoClave = sucesoClaveService.modificarSucesoClave(nombreUsuario, idSuceso, sucesoClaveDTO);

            return new ResponseEntity<>(modifiedSucesoClave, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SucesoClaveNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SinPermisoException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/sucesos/{idSuceso}")
    public ResponseEntity<Void> eliminarSucesoClave(@CookieValue(value = "nicogbdev_jwt") String jwt,
                                                    Long idSuceso){
        String nombreUsuario = jwtUtils.getUserNameFromJwtToken(jwt);

        try {
            sucesoClaveService.eliminarSucesoClave(nombreUsuario, idSuceso);
        } catch (SucesoClaveNotFoundException e) {
            // En caso de no encontrarse el suceso clave...
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SinPermisoException e) {
            // En caso de no tener permisos para ejecutar dicha eliminación...
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (UsuarioNotFoundException e) {
            // En caso de no encontrarse el usuario...
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
