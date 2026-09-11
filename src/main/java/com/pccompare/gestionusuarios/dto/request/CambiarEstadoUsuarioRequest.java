package com.pccompare.gestionusuarios.dto.request;

import com.pccompare.gestionusuarios.model.EstadoUsuario;
import jakarta.validation.constraints.NotNull;

/**
 * Usado por el administrador para activar o desactivar (borrado lógico) un usuario.
 *
 * <p>Getters, setters y constructores escritos a mano (sin Lombok).
 */
public class CambiarEstadoUsuarioRequest {

    @NotNull(message = "Debe indicar el estado a asignar")
    private EstadoUsuario estado;

    public CambiarEstadoUsuarioRequest() {
    }

    public CambiarEstadoUsuarioRequest(EstadoUsuario estado) {
        this.estado = estado;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
}
