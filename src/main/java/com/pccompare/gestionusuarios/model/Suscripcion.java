package com.pccompare.gestionusuarios.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Relación entre un {@link Usuario} y un {@link PlanSuscripcion} durante un
 * periodo determinado. Regla de negocio: un usuario solo puede tener UNA
 * suscripción con estado {@link EstadoSuscripcion#ACTIVA} a la vez (ver
 * validación en {@code SuscripcionServiceImpl}).
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin
 * Lombok) para evitar depender de un procesador de anotaciones.
 */
@Entity
@Table(name = "suscripciones")
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
    private EstadoSuscripcion estado = EstadoSuscripcion.ACTIVA;

    public Suscripcion() {
        // Constructor requerido por JPA/Hibernate.
    }

    public Suscripcion(Long id, Usuario usuario, PlanSuscripcion plan, LocalDate fechaInicio,
                        LocalDate fechaFin, EstadoSuscripcion estado) {
        this.id = id;
        this.usuario = usuario;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    /**
     * Verifica si la suscripción sigue vigente en la fecha actual,
     * combinando su estado y su fecha de fin.
     */
    public boolean estaVigente() {
        return estado == EstadoSuscripcion.ACTIVA && !fechaFin.isBefore(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PlanSuscripcion getPlan() {
        return plan;
    }

    public void setPlan(PlanSuscripcion plan) {
        this.plan = plan;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoSuscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoSuscripcion estado) {
        this.estado = estado;
    }

    public static SuscripcionBuilder builder() {
        return new SuscripcionBuilder();
    }

    /** Builder fluido para construir instancias de {@link Suscripcion}. */
    public static class SuscripcionBuilder {
        private Long id;
        private Usuario usuario;
        private PlanSuscripcion plan;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private EstadoSuscripcion estado = EstadoSuscripcion.ACTIVA;

        public SuscripcionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SuscripcionBuilder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public SuscripcionBuilder plan(PlanSuscripcion plan) {
            this.plan = plan;
            return this;
        }

        public SuscripcionBuilder fechaInicio(LocalDate fechaInicio) {
            this.fechaInicio = fechaInicio;
            return this;
        }

        public SuscripcionBuilder fechaFin(LocalDate fechaFin) {
            this.fechaFin = fechaFin;
            return this;
        }

        public SuscripcionBuilder estado(EstadoSuscripcion estado) {
            this.estado = estado;
            return this;
        }

        public Suscripcion build() {
            return new Suscripcion(id, usuario, plan, fechaInicio, fechaFin, estado);
        }
    }
}
