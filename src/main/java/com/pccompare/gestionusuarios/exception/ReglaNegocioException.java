package com.pccompare.gestionusuarios.exception;

/**
 * Se lanza cuando una operación viola una regla de negocio del módulo
 * (por ejemplo: intentar suscribirse teniendo ya una suscripción activa,
 * o intentar autenticarse con una cuenta inactiva).
 */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
