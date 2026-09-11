package com.pccompare.gestionusuarios.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Respuesta del login/registro: token JWT + datos básicos del usuario autenticado. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    @Builder.Default
    private String tipo = "Bearer";
    private UsuarioResponse usuario;
}
