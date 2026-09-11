package com.pccompare.gestionusuarios.service;

import com.pccompare.gestionusuarios.dto.request.CambiarPasswordRequest;
import com.pccompare.gestionusuarios.dto.request.RegistroUsuarioRequest;
import com.pccompare.gestionusuarios.exception.ReglaNegocioException;
import com.pccompare.gestionusuarios.exception.RecursoDuplicadoException;
import com.pccompare.gestionusuarios.model.EstadoUsuario;
import com.pccompare.gestionusuarios.model.Rol;
import com.pccompare.gestionusuarios.model.Usuario;
import com.pccompare.gestionusuarios.repository.UsuarioRepository;
import com.pccompare.gestionusuarios.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de {@link UsuarioServiceImpl}: registro (con sus
 * validaciones de unicidad) y cambio de contraseña.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioServiceImpl")
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private RegistroUsuarioRequest registroValido;

    @BeforeEach
    void setUp() {
        registroValido = new RegistroUsuarioRequest(
                "Ana", "Pérez", "anaperez", "ana@example.com", "Password123");
    }

    @Test
    @DisplayName("Registra un usuario nuevo con rol USUARIO, estado ACTIVO y contraseña cifrada")
    void registrarUsuarioValido() {
        when(usuarioRepository.existsByEmail(registroValido.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByUsername(registroValido.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registroValido.getPassword())).thenReturn("hash-cifrado");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario resultado = usuarioService.registrar(registroValido);

        assertThat(resultado.getRol()).isEqualTo(Rol.USUARIO);
        assertThat(resultado.getEstado()).isEqualTo(EstadoUsuario.ACTIVO);
        assertThat(resultado.getPassword()).isEqualTo("hash-cifrado");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Rechaza el registro si el correo ya está en uso")
    void registrarConEmailDuplicado() {
        when(usuarioRepository.existsByEmail(registroValido.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.registrar(registroValido))
                .isInstanceOf(RecursoDuplicadoException.class)
                .hasMessageContaining("correo");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Rechaza el registro si el nombre de usuario ya está en uso")
    void registrarConUsernameDuplicado() {
        when(usuarioRepository.existsByEmail(registroValido.getEmail())).thenReturn(false);
        when(usuarioRepository.existsByUsername(registroValido.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.registrar(registroValido))
                .isInstanceOf(RecursoDuplicadoException.class)
                .hasMessageContaining("usuario");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cambia la contraseña cuando la actual coincide")
    void cambiarPasswordConActualCorrecta() {
        Usuario usuario = Usuario.builder().id(1L).password("hash-anterior").build();
        CambiarPasswordRequest request = new CambiarPasswordRequest("actual123", "nuevaClave123");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("actual123", "hash-anterior")).thenReturn(true);
        when(passwordEncoder.encode("nuevaClave123")).thenReturn("hash-nuevo");

        usuarioService.cambiarPassword(1L, request);

        assertThat(usuario.getPassword()).isEqualTo("hash-nuevo");
        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Rechaza el cambio de contraseña si la actual no coincide")
    void cambiarPasswordConActualIncorrecta() {
        Usuario usuario = Usuario.builder().id(1L).password("hash-anterior").build();
        CambiarPasswordRequest request = new CambiarPasswordRequest("incorrecta", "nuevaClave123");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("incorrecta", "hash-anterior")).thenReturn(false);

        assertThatThrownBy(() -> usuarioService.cambiarPassword(1L, request))
                .isInstanceOf(ReglaNegocioException.class);

        verify(usuarioRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
    }
}
