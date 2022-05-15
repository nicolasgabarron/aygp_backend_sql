package com.nicogbdev.aygp_backend_sql.usuario.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    // Endpoints.
    @GetMapping(value = "/todos")
    public ResponseEntity<HttpStatus> contenidoPublico(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/usuarios")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ESPECIALISTA') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<HttpStatus> contenidoUsuarios(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/especialistas")
    @PreAuthorize("hasRole('ESPECIALISTA') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<HttpStatus> contenidoEspecialistas(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/administradores")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<HttpStatus> contenidoAdministradores(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
