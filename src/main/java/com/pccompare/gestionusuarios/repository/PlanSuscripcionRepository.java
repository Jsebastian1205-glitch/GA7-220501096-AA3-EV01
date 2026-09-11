package com.pccompare.gestionusuarios.repository;

import com.pccompare.gestionusuarios.model.PlanSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanSuscripcionRepository extends JpaRepository<PlanSuscripcion, Long> {

    Optional<PlanSuscripcion> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);

    List<PlanSuscripcion> findByActivoTrue();
}
