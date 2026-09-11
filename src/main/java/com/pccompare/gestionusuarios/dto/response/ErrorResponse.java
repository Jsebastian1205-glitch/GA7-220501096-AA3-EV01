package com.pccompare.gestionusuarios.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Formato uniforme de error devuelto por el {@code GlobalExceptionHandler}.
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin Lombok)
 * para evitar depender de un procesador de anotaciones.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String mensaje;
    private String ruta;
    /** Detalle de errores de validación campo por campo, cuando aplica. */
    private List<String> detalles;

    public ErrorResponse() {
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String mensaje,
                          String ruta, List<String> detalles) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.detalles = detalles;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<String> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<String> detalles) {
        this.detalles = detalles;
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    /** Builder fluido para construir instancias de {@link ErrorResponse}. */
    public static class ErrorResponseBuilder {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String mensaje;
        private String ruta;
        private List<String> detalles;

        public ErrorResponseBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorResponseBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponseBuilder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public ErrorResponseBuilder ruta(String ruta) {
            this.ruta = ruta;
            return this;
        }

        public ErrorResponseBuilder detalles(List<String> detalles) {
            this.detalles = detalles;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(timestamp, status, error, mensaje, ruta, detalles);
        }
    }
}
