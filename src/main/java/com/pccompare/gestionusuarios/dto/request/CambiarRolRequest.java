package com.pccompare.gestionusuarios.dto.request;

import com.pccompare.gestionusuarios.model.Rol;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Usado por el administrador para asignar o cambiar el rol de un usuario. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambiarRolRequest {

    @NotNull(message = "Debe indicar el rol a asignar")
    private Rol rol;
}
