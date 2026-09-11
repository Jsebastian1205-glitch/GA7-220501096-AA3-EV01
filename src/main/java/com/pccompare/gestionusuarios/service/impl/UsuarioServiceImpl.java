package com.pccompare.gestionusuarios.service.impl;

import com.pccompare.gestionusuarios.dto.request.ActualizarUsuarioRequest;
import com.pccompare.gestionusuarios.dto.request.CambiarPasswordRequest;
import com.pccompare.gestionusuarios.dto.request.RegistroUsuarioRequest;
import com.pccompare.gestionusuarios.exception.ReglaNegocioException;
import com.pccompare.gestionusuarios.exception.RecursoDuplicadoException;
import com.pccompare.gestionusuarios.exception.RecursoNoEncontradoException;
import com.pccompare.gestionusuarios.model.EstadoUsuario;
import com.pccompare.gestionusuarios.model.Rol;
import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.repository.UsuarioRepository;
import com.pccompare.gestionusuarios.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de las reglas de negocio del usuario.
 *
 * <p>Reglas aplicadas aquí (trazables a las historias de usuario e
 * historia técnica del módulo):
 * <ul>
 *   <li>El email y el username deben ser únicos en la plataforma.</li>
 *   <li>La contraseña nunca se guarda ni se compara en texto plano (BCrypt).</li>
 *   <li>Todo usuario nuevo se registra con rol USUARIO y estado ACTIVO.</li>
 *   <li>El usuario nunca se borra físicamente; un administrador solo puede
 *       cambiar su estado a INACTIVO (borrado lógico).</li>
 * </ul>
 *
 * <p>Constructor escrito a mano (sin Lombok) para evitar depender de un
 * procesador de anotaciones.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrar(RegistroUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario registrado con ese correo");
        }
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RecursoDuplicadoException("El nombre de usuario ya está en uso");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Rol.USUARIO)
                .estado(EstadoUsuario.ACTIVO)
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró un usuario con el correo " + email));
    }

    @Override
    public Usuario actualizarPerfil(Long id, ActualizarUsuarioRequest request) {
        Usuario usuario = buscarPorId(id);

        // Si cambia el correo, se revalida que siga siendo único.
        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario registrado con ese correo");
        }

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void cambiarPassword(Long id, CambiarPasswordRequest request) {
        Usuario usuario = buscarPorId(id);

        if (!passwordEncoder.matches(request.getPasswordActual(), usuario.getPassword())) {
            throw new ReglaNegocioException("La contraseña actual no es correcta");
        }

        usuario.setPassword(passwordEncoder.encode(request.getPasswordNueva()));
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario cambiarRol(Long id, Rol nuevoRol) {
        Usuario usuario = buscarPorId(id);
        usuario.setRol(nuevoRol);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario cambiarEstado(Long id, EstadoUsuario nuevoEstado) {
        Usuario usuario = buscarPorId(id);
        usuario.setEstado(nuevoEstado);
        return usuarioRepository.save(usuario);
    }
}
