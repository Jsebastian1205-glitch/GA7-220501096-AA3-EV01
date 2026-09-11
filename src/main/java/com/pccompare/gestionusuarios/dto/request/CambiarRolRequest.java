package com.pccompare.gestionusuarios.dto.request;

import com.pccompare.gestionusuarios.model.Rol;
import jakarta.validation.constraints.NotNull;

/**
 * Usado por el administrador para asignar o cambiar el rol de un usuario.
 *
 * <p>Getters, setters y constructores escritos a mano (sin Lombok).
 */
public class CambiarRolRequest {

    @NotNull(message = "Debe indicar el rol a asignar")
    private Rol rol;

    public CambiarRolRequest() {
    }

    public CambiarRolRequest(Rol rol) {
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
