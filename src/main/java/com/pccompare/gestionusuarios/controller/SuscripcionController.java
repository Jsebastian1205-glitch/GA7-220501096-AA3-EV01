package com.pccompare.gestionusuarios.controller;

import com.pccompare.gestionusuarios.dto.request.SuscribirseRequest;
import com.pccompare.gestionusuarios.dto.response.SuscripcionResponse;
import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.security.UsuarioAutenticadoProvider;
import com.pccompare.gestionusuarios.service.SuscripcionService;
import com.pccompare.gestionusuarios.util.SuscripcionMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Suscripción del usuario a un plan.
 *
 * <ul>
 *   <li>HU: "Como usuario quiero suscribirme a un plan premium".</li>
 *   <li>HU: "Como usuario quiero consultar el estado de mi suscripción actual".</li>
 *   <li>HU: "Como usuario quiero cancelar mi suscripción".</li>
 *   <li>HU: "Como administrador quiero ver el estado de las suscripciones de los usuarios".</li>
 * </ul>
 *
 * <p>Constructor escrito a mano (sin Lombok) para evitar depender de un
 * procesador de anotaciones.
 */
@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    private final SuscripcionService suscripcionService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    public SuscripcionController(SuscripcionService suscripcionService,
                                  UsuarioAutenticadoProvider usuarioAutenticadoProvider) {
        this.suscripcionService = suscripcionService;
        this.usuarioAutenticadoProvider = usuarioAutenticadoProvider;
    }

    @PostMapping
    public ResponseEntity<SuscripcionResponse> suscribirme(@Valid @RequestBody SuscribirseRequest request) {
        Usuario actual = usuarioAutenticadoProvider.obtenerUsuarioActual();
        SuscripcionResponse creada = SuscripcionMapper.aResponse(
                suscripcionService.suscribir(actual.getId(), request.getPlanId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/me/activa")
    public ResponseEntity<SuscripcionResponse> obtenerMiSuscripcionActiva() {
        Usuario actual = usuarioAutenticadoProvider.obtenerUsuarioActual();
        return suscripcionService.obtenerSuscripcionActiva(actual.getId())
                .map(SuscripcionMapper::aResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/me")
    public ResponseEntity<List<SuscripcionResponse>> obtenerMiHistorial() {
        Usuario actual = usuarioAutenticadoProvider.obtenerUsuarioActual();
        List<SuscripcionResponse> historial = suscripcionService.listarHistorial(actual.getId()).stream()
                .map(SuscripcionMapper::aResponse)
                .toList();
        return ResponseEntity.ok(historial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarMiSuscripcion(@PathVariable Long id) {
        Usuario actual = usuarioAutenticadoProvider.obtenerUsuarioActual();
        suscripcionService.cancelar(actual.getId(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SuscripcionResponse>> listarTodas() {
        List<SuscripcionResponse> respuesta = suscripcionService.listarTodas().stream()
                .map(SuscripcionMapper::aResponse)
                .toList();
        return ResponseEntity.ok(respuesta);
    }
}
