package com.pccompare.gestionusuarios.service.impl;

import com.pccompare.gestionusuarios.dto.request.PlanSuscripcionRequest;
import com.pccompare.gestionusuarios.exception.RecursoDuplicadoException;
import com.pccompare.gestionusuarios.exception.RecursoNoEncontradoException;
import com.pccompare.gestionusuarios.model.PlanSuscripcion;
import com.pccompare.gestionusuarios.repository.PlanSuscripcionRepository;
import com.pccompare.gestionusuarios.service.PlanSuscripcionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Administración de los planes de suscripción. Solo un administrador puede
 * invocar estas operaciones (control de acceso aplicado en el controlador
 * con @PreAuthorize).
 *
 * <p>Constructor escrito a mano (sin Lombok) para evitar depender de un
 * procesador de anotaciones.
 */
@Service
@Transactional
public class PlanSuscripcionServiceImpl implements PlanSuscripcionService {

    private final PlanSuscripcionRepository planSuscripcionRepository;

    public PlanSuscripcionServiceImpl(PlanSuscripcionRepository planSuscripcionRepository) {
        this.planSuscripcionRepository = planSuscripcionRepository;
    }

    @Override
    public PlanSuscripcion crear(PlanSuscripcionRequest request) {
        if (planSuscripcionRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RecursoDuplicadoException("Ya existe un plan con ese nombre");
        }

        PlanSuscripcion plan = PlanSuscripcion.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precio(request.getPrecio())
                .duracionDias(request.getDuracionDias())
                .activo(true)
                .build();

        return planSuscripcionRepository.save(plan);
    }

    @Override
    public PlanSuscripcion actualizar(Long id, PlanSuscripcionRequest request) {
        PlanSuscripcion plan = buscarPorId(id);

        boolean cambiaNombre = !plan.getNombre().equalsIgnoreCase(request.getNombre());
        if (cambiaNombre && planSuscripcionRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new RecursoDuplicadoException("Ya existe un plan con ese nombre");
        }

        plan.setNombre(request.getNombre());
        plan.setDescripcion(request.getDescripcion());
        plan.setPrecio(request.getPrecio());
        plan.setDuracionDias(request.getDuracionDias());

        return planSuscripcionRepository.save(plan);
    }

    @Override
    @Transactional(readOnly = true)
    public PlanSuscripcion buscarPorId(Long id) {
        return planSuscripcionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el plan con id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanSuscripcion> listarTodos() {
        return planSuscripcionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanSuscripcion> listarActivos() {
        return planSuscripcionRepository.findByActivoTrue();
    }

    @Override
    public void desactivar(Long id) {
        // No se elimina físicamente: un plan desactivado conserva el historial
        // de las suscripciones que ya lo usaron.
        PlanSuscripcion plan = buscarPorId(id);
        plan.setActivo(false);
        planSuscripcionRepository.save(plan);
    }
}
