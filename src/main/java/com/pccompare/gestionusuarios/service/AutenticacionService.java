package com.pccompare.gestionusuarios.service;

import com.pccompare.gestionusuarios.dto.request.LoginRequest;
import com.pccompare.gestionusuarios.dto.request.RegistroUsuarioRequest;
import com.pccompare.gestionusuarios.dto.response.AuthResponse;

/** Casos de uso de autenticación: registro con emisión de token y login. */
public interface AutenticacionService {

    AuthResponse registrarYAutenticar(RegistroUsuarioRequest request);

    AuthResponse autenticar(LoginRequest request);
}
