package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Datos para el cambio de contraseña del propio usuario.
 *
 * <p>Getters, setters y constructores escritos a mano (sin Lombok).
 */
public class CambiarPasswordRequest {

    @NotBlank(message = "Debe indicar la contraseña actual")
    private String passwordActual;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "La nueva contraseña debe tener mínimo 8 caracteres e incluir al menos una letra y un número"
    )
    private String passwordNueva;

    public CambiarPasswordRequest() {
    }

    public CambiarPasswordRequest(String passwordActual, String passwordNueva) {
        this.passwordActual = passwordActual;
        this.passwordNueva = passwordNueva;
    }

    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getPasswordNueva() {
        return passwordNueva;
    }

    public void setPasswordNueva(String passwordNueva) {
        this.passwordNueva = passwordNueva;
    }
}
