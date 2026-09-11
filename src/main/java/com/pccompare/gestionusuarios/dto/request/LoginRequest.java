package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Credenciales de inicio de sesión. El usuario puede identificarse con su
 * correo o con su nombre de usuario en el campo {@code identificador}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Debe indicar el correo o el nombre de usuario")
    private String identificador;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
