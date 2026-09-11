package com.pccompare.gestionusuarios.exception;

/** Se lanza cuando se busca una entidad (usuario, plan, suscripción) que no existe. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
