package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Historia de usuario: "Como usuario quiero suscribirme a un plan". */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuscribirseRequest {

    @NotNull(message = "Debe indicar el plan al que desea suscribirse")
    private Long planId;
}
