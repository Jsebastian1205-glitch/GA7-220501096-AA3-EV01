package com.pccompare.gestionusuarios.service.impl;

import com.pccompare.gestionusuarios.exception.ReglaNegocioException;
import com.pccompare.gestionusuarios.exception.RecursoNoEncontradoException;
import com.pccompare.gestionusuarios.model.EstadoSuscripcion;
import com.pccompare.gestionusuarios.model.PlanSuscripcion;
import com.pccompare.gestionusuarios.model.Suscripcion;
import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.repository.PlanSuscripcionRepository;
import com.pccompare.gestionusuarios.repository.SuscripcionRepository;
import com.pccompare.gestionusuarios.repository.UsuarioRepository;
import com.pccompare.gestionusuarios.service.SuscripcionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Reglas de negocio de las suscripciones.
 *
 * <p>Restricciones aplicadas:
 * <ul>
 *   <li>Un usuario no puede tener dos suscripciones ACTIVAS al mismo tiempo:
 *       debe cancelar la vigente antes de tomar una nueva.</li>
 *   <li>Solo se puede suscribir a un plan que esté {@code activo}.</li>
 *   <li>La fecha de fin se calcula automáticamente a partir de la duración
 *       del plan (fechaInicio + duracionDias), nunca la recibe el cliente.</li>
 *   <li>Solo el dueño de la suscripción (o un administrador) puede cancelarla.</li>
 * </ul>
 *
 * <p>Constructor escrito a mano (sin Lombok) para evitar depender de un
 * procesador de anotaciones.
 */
@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanSuscripcionRepository planSuscripcionRepository;

    public SuscripcionServiceImpl(SuscripcionRepository suscripcionRepository, UsuarioRepository usuarioRepository,
                                   PlanSuscripcionRepository planSuscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.planSuscripcionRepository = planSuscripcionRepository;
    }

    @Override
    public Suscripcion suscribir(Long usuarioId, Long planId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con id " + usuarioId));

        PlanSuscripcion plan = planSuscripcionRepository.findById(planId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el plan con id " + planId));

        if (!plan.isActivo()) {
            throw new ReglaNegocioException("El plan seleccionado ya no está disponible");
        }

        boolean tieneSuscripcionActiva = suscripcionRepository
                .existsByUsuarioIdAndEstado(usuarioId, EstadoSuscripcion.ACTIVA);
        if (tieneSuscripcionActiva) {
            throw new ReglaNegocioException(
                    "El usuario ya cuenta con una suscripción activa; debe cancelarla antes de tomar una nueva");
        }

        LocalDate hoy = LocalDate.now();
        Suscripcion nuevaSuscripcion = Suscripcion.builder()
                .usuario(usuario)
                .plan(plan)
                .fechaInicio(hoy)
                .fechaFin(hoy.plusDays(plan.getDuracionDias()))
                .estado(EstadoSuscripcion.ACTIVA)
                .build();

        return suscripcionRepository.save(nuevaSuscripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Suscripcion> obtenerSuscripcionActiva(Long usuarioId) {
        return suscripcionRepository.findByUsuarioIdAndEstado(usuarioId, EstadoSuscripcion.ACTIVA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> listarHistorial(Long usuarioId) {
        return suscripcionRepository.findByUsuarioIdOrderByFechaInicioDesc(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Suscripcion> listarTodas() {
        return suscripcionRepository.findAll();
    }

    @Override
    public void cancelar(Long usuarioId, Long suscripcionId) {
        Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la suscripción con id " + suscripcionId));

        if (!suscripcion.getUsuario().getId().equals(usuarioId)) {
            throw new ReglaNegocioException("La suscripción indicada no pertenece a este usuario");
        }

        if (suscripcion.getEstado() != EstadoSuscripcion.ACTIVA) {
            throw new ReglaNegocioException("Solo se puede cancelar una suscripción que esté activa");
        }

        suscripcion.setEstado(EstadoSuscripcion.CANCELADA);
        suscripcionRepository.save(suscripcion);
    }
}
