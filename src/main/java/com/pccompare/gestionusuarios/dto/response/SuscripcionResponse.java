package com.pccompare.gestionusuarios.dto.response;

import com.pccompare.gestionusuarios.model.EstadoSuscripcion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuscripcionResponse {
    private Long id;
    private Long usuarioId;
    private PlanSuscripcionResponse plan;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoSuscripcion estado;
}
