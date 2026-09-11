package com.pccompare.gestionusuarios.service;

import com.pccompare.gestionusuarios.model.Suscripcion;

import java.util.List;
import java.util.Optional;

/**
 * Reglas de negocio de las suscripciones: alta, consulta y cancelación,
 * garantizando que un usuario no tenga más de una suscripción activa a
 * la vez.
 */
public interface SuscripcionService {

    Suscripcion suscribir(Long usuarioId, Long planId);

    Optional<Suscripcion> obtenerSuscripcionActiva(Long usuarioId);

    List<Suscripcion> listarHistorial(Long usuarioId);

    List<Suscripcion> listarTodas();

    void cancelar(Long usuarioId, Long suscripcionId);
}
