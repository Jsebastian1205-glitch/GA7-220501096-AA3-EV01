package com.pccompare.gestionusuarios.dto.response;

import com.pccompare.gestionusuarios.model.EstadoSuscripcion;

import java.time.LocalDate;

/**
 * Representación pública de una suscripción, incluyendo el plan asociado.
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin Lombok)
 * para evitar depender de un procesador de anotaciones.
 */
public class SuscripcionResponse {

    private Long id;
    private Long usuarioId;
    private PlanSuscripcionResponse plan;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoSuscripcion estado;

    public SuscripcionResponse() {
    }

    public SuscripcionResponse(Long id, Long usuarioId, PlanSuscripcionResponse plan,
                                LocalDate fechaInicio, LocalDate fechaFin, EstadoSuscripcion estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public PlanSuscripcionResponse getPlan() {
        return plan;
    }

    public void setPlan(PlanSuscripcionResponse plan) {
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

    public static SuscripcionResponseBuilder builder() {
        return new SuscripcionResponseBuilder();
    }

    /** Builder fluido para construir instancias de {@link SuscripcionResponse}. */
    public static class SuscripcionResponseBuilder {
        private Long id;
        private Long usuarioId;
        private PlanSuscripcionResponse plan;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private EstadoSuscripcion estado;

        public SuscripcionResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SuscripcionResponseBuilder usuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
            return this;
        }

        public SuscripcionResponseBuilder plan(PlanSuscripcionResponse plan) {
            this.plan = plan;
            return this;
        }

        public SuscripcionResponseBuilder fechaInicio(LocalDate fechaInicio) {
            this.fechaInicio = fechaInicio;
            return this;
        }

        public SuscripcionResponseBuilder fechaFin(LocalDate fechaFin) {
            this.fechaFin = fechaFin;
            return this;
        }

        public SuscripcionResponseBuilder estado(EstadoSuscripcion estado) {
            this.estado = estado;
            return this;
        }

        public SuscripcionResponse build() {
            return new SuscripcionResponse(id, usuarioId, plan, fechaInicio, fechaFin, estado);
        }
    }
}
