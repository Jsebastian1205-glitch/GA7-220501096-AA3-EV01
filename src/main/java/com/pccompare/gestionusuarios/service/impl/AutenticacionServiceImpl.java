package com.pccompare.gestionusuarios.service.impl;

import com.pccompare.gestionusuarios.dto.request.LoginRequest;
import com.pccompare.gestionusuarios.dto.request.RegistroUsuarioRequest;
import com.pccompare.gestionusuarios.dto.response.AuthResponse;
import com.pccompare.gestionusuarios.exception.CredencialesInvalidasException;
import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.repository.UsuarioRepository;
import com.pccompare.gestionusuarios.security.JwtUtil;
import com.pccompare.gestionusuarios.service.AutenticacionService;
import com.pccompare.gestionusuarios.service.UsuarioService;
import com.pccompare.gestionusuarios.util.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementa el registro con emisión inmediata de token (para que el
 * usuario quede autenticado justo después de crear su cuenta) y el login.
 *
 * <p>Regla de negocio: solo pueden iniciar sesión los usuarios con estado
 * ACTIVO; un usuario INACTIVO recibe un mensaje explícito en vez de un
 * genérico "credenciales inválidas".
 *
 * <p>Constructor escrito a mano (sin Lombok) para evitar depender de un
 * procesador de anotaciones.
 */
@Service
@Transactional
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AutenticacionServiceImpl(UsuarioRepository usuarioRepository, UsuarioService usuarioService,
                                     PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse registrarYAutenticar(RegistroUsuarioRequest request) {
        Usuario usuario = usuarioService.registrar(request);
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .usuario(UsuarioMapper.aResponse(usuario))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmailOrUsername(request.getIdentificador(), request.getIdentificador())
                .orElseThrow(() -> new CredencialesInvalidasException("Correo/usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Correo/usuario o contraseña incorrectos");
        }

        if (!usuario.puedeAutenticarse()) {
            throw new CredencialesInvalidasException("La cuenta se encuentra inactiva; contacte al administrador");
        }

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());

        return AuthResponse.builder()
                .token(token)
                .usuario(UsuarioMapper.aResponse(usuario))
                .build();
    }
}
