package com.pccompare.gestionusuarios.util;

import com.pccompare.gestionusuarios.dto.response.UsuarioResponse;
import com.pccompare.gestionusuarios.model.Usuario;

/** Convierte entidades {@link Usuario} en DTO de respuesta, sin exponer la contraseña. */
public final class UsuarioMapper {

    private UsuarioMapper() {
        // Clase de utilidades: no se debe instanciar.
    }

    public static UsuarioResponse aResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}
