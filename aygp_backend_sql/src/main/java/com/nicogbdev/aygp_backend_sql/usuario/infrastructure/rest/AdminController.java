package com.nicogbdev.aygp_backend_sql.usuario.infrastructure.rest;

import com.nicogbdev.aygp_backend_sql.usuario.application.dto.UsuarioDTO;
import com.nicogbdev.aygp_backend_sql.usuario.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminController {
    // Inyección de dependencias.
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    // Endpoints.
    @GetMapping(value = "/todosUsuarios", produces = "application/json")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios(){
        List<UsuarioDTO> usuarioDTOS = adminService.obtenerUsuarios();

        return new ResponseEntity<>(usuarioDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/usuario/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id){
        return adminService.obtenerUsuarioPorId(id)
                .map(usuarioDTO -> new ResponseEntity<>(usuarioDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/usuario/{id}")
    public ResponseEntity<Void> eliminarUsuarioPorId(@PathVariable Long id){
        adminService.eliminarUsuario(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
