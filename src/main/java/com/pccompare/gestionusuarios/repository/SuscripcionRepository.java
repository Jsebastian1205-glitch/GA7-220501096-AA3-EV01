package com.pccompare.gestionusuarios.repository;

import com.pccompare.gestionusuarios.model.EstadoSuscripcion;
import com.pccompare.gestionusuarios.model.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {

    List<Suscripcion> findByUsuarioIdOrderByFechaInicioDesc(Long usuarioId);

    Optional<Suscripcion> findByUsuarioIdAndEstado(Long usuarioId, EstadoSuscripcion estado);

    boolean existsByUsuarioIdAndEstado(Long usuarioId, EstadoSuscripcion estado);
}
