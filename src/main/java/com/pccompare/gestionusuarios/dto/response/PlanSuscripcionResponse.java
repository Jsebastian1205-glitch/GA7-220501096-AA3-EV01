package com.pccompare.gestionusuarios.dto.response;

import java.math.BigDecimal;

/**
 * Representación pública de un plan de suscripción.
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin Lombok)
 * para evitar depender de un procesador de anotaciones.
 */
public class PlanSuscripcionResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer duracionDias;
    private boolean activo;

    public PlanSuscripcionResponse() {
    }

    public PlanSuscripcionResponse(Long id, String nombre, String descripcion, BigDecimal precio,
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

    public static PlanSuscripcionResponseBuilder builder() {
        return new PlanSuscripcionResponseBuilder();
    }

    /** Builder fluido para construir instancias de {@link PlanSuscripcionResponse}. */
    public static class PlanSuscripcionResponseBuilder {
        private Long id;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private Integer duracionDias;
        private boolean activo;

        public PlanSuscripcionResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PlanSuscripcionResponseBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public PlanSuscripcionResponseBuilder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public PlanSuscripcionResponseBuilder precio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public PlanSuscripcionResponseBuilder duracionDias(Integer duracionDias) {
            this.duracionDias = duracionDias;
            return this;
        }

        public PlanSuscripcionResponseBuilder activo(boolean activo) {
            this.activo = activo;
            return this;
        }

        public PlanSuscripcionResponse build() {
            return new PlanSuscripcionResponse(id, nombre, descripcion, precio, duracionDias, activo);
        }
    }
}
