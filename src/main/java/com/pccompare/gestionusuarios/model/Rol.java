package com.pccompare.gestionusuarios.model;

/**
 * Roles soportados por el módulo de gestión de usuarios.
 *
 * <p>{@link #ADMIN}: administra usuarios, planes de suscripción y tiene
 * visibilidad total del módulo.
 * <p>{@link #USUARIO}: usuario final de la plataforma de comparación de
 * componentes de PC; gestiona su propio perfil y su propia suscripción.
 */
public enum Rol {
    ADMIN,
    USUARIO
}
