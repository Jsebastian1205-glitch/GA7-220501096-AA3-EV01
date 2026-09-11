package com.pccompare.gestionusuarios.dto.response;

import com.pccompare.gestionusuarios.model.EstadoUsuario;
import com.pccompare.gestionusuarios.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Representación pública de un {@link com.pccompare.gestionusuarios.model.Usuario}.
 * Nunca incluye la contraseña, evitando así exponer datos sensibles en la API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private Rol rol;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;
}
