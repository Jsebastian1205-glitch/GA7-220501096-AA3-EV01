package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/** Datos para crear o actualizar un plan de suscripción (solo administradores). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanSuscripcionRequest {

    @NotBlank(message = "El nombre del plan es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    private BigDecimal precio;

    @NotNull(message = "La duración en días es obligatoria")
    @Min(value = 1, message = "La duración debe ser de al menos 1 día")
    private Integer duracionDias;
}
