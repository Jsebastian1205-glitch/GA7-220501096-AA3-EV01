package com.pccompare.gestionusuarios.service;

import com.pccompare.gestionusuarios.dto.request.PlanSuscripcionRequest;
import com.pccompare.gestionusuarios.model.PlanSuscripcion;

import java.util.List;

/** Administración de los planes de suscripción ofrecidos por la plataforma. */
public interface PlanSuscripcionService {

    PlanSuscripcion crear(PlanSuscripcionRequest request);

    PlanSuscripcion actualizar(Long id, PlanSuscripcionRequest request);

    PlanSuscripcion buscarPorId(Long id);

    List<PlanSuscripcion> listarTodos();

    List<PlanSuscripcion> listarActivos();

    void desactivar(Long id);
}
