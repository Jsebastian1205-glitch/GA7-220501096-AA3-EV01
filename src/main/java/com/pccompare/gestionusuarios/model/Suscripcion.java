package com.pccompare.gestionusuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Relación entre un {@link Usuario} y un {@link PlanSuscripcion} durante un
 * periodo determinado. Regla de negocio: un usuario solo puede tener UNA
 * suscripción con estado {@link EstadoSuscripcion#ACTIVA} a la vez (ver
 * validación en {@code SuscripcionServiceImpl}).
 */
@Entity
@Table(name = "suscripciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanSuscripcion plan;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoSuscripcion estado = EstadoSuscripcion.ACTIVA;

    /**
     * Verifica si la suscripción sigue vigente en la fecha actual,
     * combinando su estado y su fecha de fin.
     */
    public boolean estaVigente() {
        return estado == EstadoSuscripcion.ACTIVA && !fechaFin.isBefore(LocalDate.now());
    }
}
