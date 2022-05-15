package com.nicogbdev.aygp_backend_sql.usuario.infrastructure.rest;

import com.nicogbdev.aygp_backend_sql.rol.application.service.RolService;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.ERol;
import com.nicogbdev.aygp_backend_sql.rol.domain.entity.Rol;
import com.nicogbdev.aygp_backend_sql.security.jwt.JwtUtils;
import com.nicogbdev.aygp_backend_sql.security.services.UserDetailsImpl;
import com.nicogbdev.aygp_backend_sql.usuario.application.service.UsuarioService;
import com.nicogbdev.aygp_backend_sql.usuario.domain.entity.Usuario;
import com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.requests.LoginRequest;
import com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.requests.SignupRequest;
import com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.responses.MessageResponse;
import com.nicogbdev.aygp_backend_sql.usuario.domain.payloads.responses.UserInfoResponse;
import com.nicogbdev.aygp_backend_sql.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    // Inyección de dependencias.
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolService rolService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


    // Endpoints.

    //TODO: Revisar devolución ? por Usuario o UserInfoResponse.
    @PostMapping(value = "/signin")
    public ResponseEntity<?> autenticarUsuario(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> rolesUsuario = userDetails.getAuthorities().stream()
                .map(rol -> rol.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), rolesUsuario));
    }



    @PostMapping(value = "/signup")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody SignupRequest signupRequest){
        // Comprobación nombre de usuario.
        if (usuarioRepository.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>("El nombre de usuario especificado ya existe.",HttpStatus.BAD_REQUEST);
        }

        // Comprobación email.
        if (usuarioRepository.existsByEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("El email especificado ya existe.", HttpStatus.BAD_REQUEST);
        }

        // Creo el nuevo usuario.
        Usuario usuarioCrear = new Usuario(signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol rolUsuario = rolService.obtenerRolPorNombre(ERol.ROLE_USUARIO)
                    .orElseThrow(() -> new RuntimeException("ROL NO ENCONTRADO."));
            roles.add(rolUsuario);
        } else {
            strRoles.forEach(rol -> {
                switch (rol){
                    case "especialista":
                        Rol rolEspecialista = rolService.obtenerRolPorNombre(ERol.ROLE_ESPECIALISTA)
                                .orElseThrow(() -> new RuntimeException("ROL NO ENCONTRADO."));
                        roles.add(rolEspecialista);
                        break;
                    case "administrador":
                        Rol rolAdministrador = rolService.obtenerRolPorNombre(ERol.ROLE_ADMINISTRADOR)
                                .orElseThrow(() -> new RuntimeException("ROL NO ENCONTRADO."));
                        roles.add(rolAdministrador);
                        break;
                    default:
                        Rol rolUsuario = rolService.obtenerRolPorNombre(ERol.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("ROL NO ENCONTRADO."));
                        roles.add(rolUsuario);
                        break;
                }
            });
        }

        usuarioCrear.setRoles(roles);
        Usuario usuario = usuarioService.crearUsuario(usuarioCrear);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);

    }


    @PostMapping(value = "/logout")
    public ResponseEntity<?> logoutUsuario(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Has salido correctamente.");
    }
}