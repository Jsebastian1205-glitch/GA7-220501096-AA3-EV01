package com.pccompare.gestionusuarios.dto.request;

import com.pccompare.gestionusuarios.model.EstadoUsuario;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Usado por el administrador para activar o desactivar (borrado lógico) un usuario. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoUsuarioRequest {

    @NotNull(message = "Debe indicar el estado a asignar")
    private EstadoUsuario estado;
}
