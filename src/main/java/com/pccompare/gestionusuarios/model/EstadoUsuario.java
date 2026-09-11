package com.pccompare.gestionusuarios.model;

/**
 * Estado de la cuenta de un usuario dentro de la plataforma.
 *
 * <p>Regla de negocio: los usuarios nunca se eliminan físicamente de la
 * base de datos (borrado lógico); un administrador solo puede cambiar su
 * estado a {@link #INACTIVO}, lo que le impide iniciar sesión.
 */
public enum EstadoUsuario {
    ACTIVO,
    INACTIVO
}
