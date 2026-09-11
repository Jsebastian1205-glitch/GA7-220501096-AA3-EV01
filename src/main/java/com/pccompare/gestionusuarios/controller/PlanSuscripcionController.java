package com.pccompare.gestionusuarios.controller;

import com.pccompare.gestionusuarios.dto.request.PlanSuscripcionRequest;
import com.pccompare.gestionusuarios.dto.response.PlanSuscripcionResponse;
import com.pccompare.gestionusuarios.service.PlanSuscripcionService;
import com.pccompare.gestionusuarios.util.PlanSuscripcionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administración de planes de suscripción (HU: "Como administrador quiero
 * gestionar los planes de suscripción"). La consulta de planes activos es
 * pública dentro de la plataforma para que cualquier usuario autenticado
 * pueda elegir uno; la creación/edición/baja está reservada a ADMIN.
 */
@RestController
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlanSuscripcionController {

    private final PlanSuscripcionService planSuscripcionService;

    @GetMapping
    public ResponseEntity<List<PlanSuscripcionResponse>> listarActivos() {
        List<PlanSuscripcionResponse> respuesta = planSuscripcionService.listarActivos().stream()
                .map(PlanSuscripcionMapper::aResponse)
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PlanSuscripcionResponse>> listarTodos() {
        List<PlanSuscripcionResponse> respuesta = planSuscripcionService.listarTodos().stream()
                .map(PlanSuscripcionMapper::aResponse)
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanSuscripcionResponse> crear(@Valid @RequestBody PlanSuscripcionRequest request) {
        PlanSuscripcionResponse creado = PlanSuscripcionMapper.aResponse(planSuscripcionService.crear(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlanSuscripcionResponse> actualizar(@PathVariable Long id,
                                                                @Valid @RequestBody PlanSuscripcionRequest request) {
        PlanSuscripcionResponse actualizado = PlanSuscripcionMapper.aResponse(planSuscripcionService.actualizar(id, request));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        planSuscripcionService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
