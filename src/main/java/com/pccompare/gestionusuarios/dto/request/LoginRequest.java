package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Credenciales de inicio de sesión. El usuario puede identificarse con su
 * correo o con su nombre de usuario en el campo {@code identificador}.
 *
 * <p>Getters, setters y constructores escritos a mano (sin Lombok).
 */
public class LoginRequest {

    @NotBlank(message = "Debe indicar el correo o el nombre de usuario")
    private String identificador;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String identificador, String password) {
        this.identificador = identificador;
        this.password = password;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
