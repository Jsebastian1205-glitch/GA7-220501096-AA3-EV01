package com.pccompare.gestionusuarios.exception;

/** Se lanza cuando el login falla por credenciales incorrectas o cuenta inactiva. */
public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
