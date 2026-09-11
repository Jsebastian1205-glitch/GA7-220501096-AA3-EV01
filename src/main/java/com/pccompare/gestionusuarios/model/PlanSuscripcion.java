package com.pccompare.gestionusuarios.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Plan de suscripción ofrecido por la plataforma (por ejemplo: Gratuito,
 * Premium Mensual, Premium Anual). Es administrado únicamente por un
 * {@link Rol#ADMIN} y consumido por los usuarios al suscribirse.
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin
 * Lombok) para evitar depender de un procesador de anotaciones.
 */
@Entity
@Table(name = "planes_suscripcion")
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
    private boolean activo = true;

    public PlanSuscripcion() {
        // Constructor requerido por JPA/Hibernate.
    }

    public PlanSuscripcion(Long id, String nombre, String descripcion, BigDecimal precio,
                            Integer duracionDias, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionDias = duracionDias;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(Integer duracionDias) {
        this.duracionDias = duracionDias;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public static PlanSuscripcionBuilder builder() {
        return new PlanSuscripcionBuilder();
    }

    /** Builder fluido para construir instancias de {@link PlanSuscripcion}. */
    public static class PlanSuscripcionBuilder {
        private Long id;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private Integer duracionDias;
        private boolean activo = true;

        public PlanSuscripcionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PlanSuscripcionBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public PlanSuscripcionBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public PlanSuscripcionBuilder precio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public PlanSuscripcionBuilder duracionDias(Integer duracionDias) {
            this.duracionDias = duracionDias;
            return this;
        }

        public PlanSuscripcionBuilder activo(boolean activo) {
            this.activo = activo;
            return this;
        }

        public PlanSuscripcion build() {
            return new PlanSuscripcion(id, nombre, descripcion, precio, duracionDias, activo);
        }
    }
}
