package com.pccompare.gestionusuarios.service;

import com.pccompare.gestionusuarios.dto.request.ActualizarUsuarioRequest;
import com.pccompare.gestionusuarios.dto.request.CambiarPasswordRequest;
import com.pccompare.gestionusuarios.dto.request.RegistroUsuarioRequest;
import com.pccompare.gestionusuarios.model.EstadoUsuario;
import com.pccompare.gestionusuarios.model.Rol;
import com.pccompare.gestionusuarios.model.Usuario;

import java.util.List;

/**
 * Reglas de negocio relacionadas con el ciclo de vida del usuario:
 * registro, consulta, actualización de perfil y administración
 * (rol/estado) por parte de un administrador.
 */
public interface UsuarioService {

    Usuario registrar(RegistroUsuarioRequest request);

    List<Usuario> listarTodos();

    Usuario buscarPorId(Long id);

    Usuario buscarPorEmail(String email);

    Usuario actualizarPerfil(Long id, ActualizarUsuarioRequest request);

    void cambiarPassword(Long id, CambiarPasswordRequest request);

    Usuario cambiarRol(Long id, Rol nuevoRol);

    Usuario cambiarEstado(Long id, EstadoUsuario nuevoEstado);
}
