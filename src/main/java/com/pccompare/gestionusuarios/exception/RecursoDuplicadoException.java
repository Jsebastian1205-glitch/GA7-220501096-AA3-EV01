package com.pccompare.gestionusuarios.exception;

/** Se lanza al intentar crear un recurso que viola una restricción de unicidad (email, username, nombre de plan...). */
public class RecursoDuplicadoException extends RuntimeException {
    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
