package com.pccompare.gestionusuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Plan de suscripción ofrecido por la plataforma (por ejemplo: Gratuito,
 * Premium Mensual, Premium Anual). Es administrado únicamente por un
 * {@link Rol#ADMIN} y consumido por los usuarios al suscribirse.
 */
@Entity
@Table(name = "planes_suscripcion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanSuscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    /** Precio del plan; 0.00 para el plan gratuito. */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    /** Duración del plan en días (se usa para calcular la fecha de fin de la suscripción). */
    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    /** Permite desactivar un plan sin borrarlo (para no afectar el historial de suscripciones). */
    @Column(nullable = false)
    @Builder.Default
    private boolean activo = true;
}
