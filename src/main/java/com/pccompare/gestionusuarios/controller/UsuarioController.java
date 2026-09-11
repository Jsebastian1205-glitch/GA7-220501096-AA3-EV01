package com.pccompare.gestionusuarios.controller;

import com.pccompare.gestionusuarios.dto.request.ActualizarUsuarioRequest;
import com.pccompare.gestionusuarios.dto.request.CambiarEstadoUsuarioRequest;
import com.pccompare.gestionusuarios.dto.request.CambiarPasswordRequest;
import com.pccompare.gestionusuarios.dto.request.CambiarRolRequest;
import com.pccompare.gestionusuarios.dto.response.UsuarioResponse;
import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.security.UsuarioAutenticadoProvider;
import com.pccompare.gestionusuarios.service.UsuarioService;
import com.pccompare.gestionusuarios.util.UsuarioMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de gestión de usuarios.
 *
 * <p>Los endpoints bajo {@code /api/usuarios/me} los usa cualquier usuario
 * autenticado sobre su propio perfil (HU "actualizar mi perfil"). Los demás
 * endpoints (listar, cambiar rol, cambiar estado) están restringidos al rol
 * ADMIN (HU "gestionar usuarios / asignar roles").
 *
 * <p>Constructor escrito a mano (sin Lombok) para evitar depender de un
 * procesador de anotaciones.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    public UsuarioController(UsuarioService usuarioService, UsuarioAutenticadoProvider usuarioAutenticadoProvider) {
        this.usuarioService = usuarioService;
        this.usuarioAutenticadoProvider = usuarioAutenticadoProvider;
    }

    // ---------------------------------------------------------------
    // Perfil del propio usuario autenticado
    // ---------------------------------------------------------------

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> obtenerMiPerfil() {
        Usuario usuario = usuarioAutenticadoProvider.obtenerUsuarioActual();
        return ResponseEntity.ok(UsuarioMapper.aResponse(usuario));
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResponse> actualizarMiPerfil(@Valid @RequestBody ActualizarUsuarioRequest request) {
        Usuario actual = usuarioAutenticadoProvider.obtenerUsuarioActual();
        Usuario actualizado = usuarioService.actualizarPerfil(actual.getId(), request);
        return ResponseEntity.ok(UsuarioMapper.aResponse(actualizado));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> cambiarMiPassword(@Valid @RequestBody CambiarPasswordRequest request) {
        Usuario actual = usuarioAutenticadoProvider.obtenerUsuarioActual();
        usuarioService.cambiarPassword(actual.getId(), request);
        return ResponseEntity.noContent().build();
    }

    // ---------------------------------------------------------------
    // Administración de usuarios (solo ADMIN)
    // ---------------------------------------------------------------

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> respuesta = usuarioService.listarTodos().stream()
                .map(UsuarioMapper::aResponse)
                .toList();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(UsuarioMapper.aResponse(usuarioService.buscarPorId(id)));
    }

    @PatchMapping("/{id}/rol")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> cambiarRol(@PathVariable Long id,
                                                        @Valid @RequestBody CambiarRolRequest request) {
        Usuario actualizado = usuarioService.cambiarRol(id, request.getRol());
        return ResponseEntity.ok(UsuarioMapper.aResponse(actualizado));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> cambiarEstado(@PathVariable Long id,
                                                           @Valid @RequestBody CambiarEstadoUsuarioRequest request) {
        Usuario actualizado = usuarioService.cambiarEstado(id, request.getEstado());
        return ResponseEntity.ok(UsuarioMapper.aResponse(actualizado));
    }
}
