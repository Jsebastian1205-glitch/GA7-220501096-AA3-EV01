package com.pccompare.gestionusuarios.model;

/**
 * Estado de una suscripción de un usuario a un plan.
 */
public enum EstadoSuscripcion {
    /** Suscripción vigente y utilizable. */
    ACTIVA,
    /** Cancelada de forma anticipada por el usuario o un administrador. */
    CANCELADA,
    /** Llegó a su fecha de fin sin ser renovada. */
    VENCIDA
}
