package com.pccompare.gestionusuarios.controller;

import com.pccompare.gestionusuarios.dto.request.LoginRequest;
import com.pccompare.gestionusuarios.dto.request.RegistroUsuarioRequest;
import com.pccompare.gestionusuarios.dto.response.AuthResponse;
import com.pccompare.gestionusuarios.service.AutenticacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints públicos de autenticación.
 *
 * <ul>
 *   <li>HU: "Como usuario quiero registrarme en la plataforma con mis datos personales".</li>
 *   <li>HU: "Como usuario quiero iniciar sesión con mi correo y contraseña".</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutenticacionService autenticacionService;

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
        AuthResponse respuesta = autenticacionService.registrarYAutenticar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse respuesta = autenticacionService.autenticar(request);
        return ResponseEntity.ok(respuesta);
    }
}
