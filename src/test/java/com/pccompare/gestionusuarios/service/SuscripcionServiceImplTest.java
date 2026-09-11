package com.pccompare.gestionusuarios.service;

import com.pccompare.gestionusuarios.exception.ReglaNegocioException;
import com.pccompare.gestionusuarios.model.*;
import com.pccompare.gestionusuarios.repository.PlanSuscripcionRepository;
import com.pccompare.gestionusuarios.repository.SuscripcionRepository;
import com.pccompare.gestionusuarios.repository.UsuarioRepository;
import com.pccompare.gestionusuarios.service.impl.SuscripcionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de {@link SuscripcionServiceImpl}, centradas en la
 * regla de negocio central del módulo: un usuario no puede tener más de
 * una suscripción activa al mismo tiempo.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SuscripcionServiceImpl")
class SuscripcionServiceImplTest {

    @Mock
    private SuscripcionRepository suscripcionRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PlanSuscripcionRepository planSuscripcionRepository;

    @InjectMocks
    private SuscripcionServiceImpl suscripcionService;

    @Test
    @DisplayName("Permite suscribirse cuando el usuario no tiene una suscripción activa")
    void suscribirSinSuscripcionActivaPrevia() {
        Usuario usuario = Usuario.builder().id(1L).build();
        PlanSuscripcion plan = PlanSuscripcion.builder()
                .id(10L).nombre("Premium Mensual").precio(BigDecimal.TEN).duracionDias(30).activo(true).build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(planSuscripcionRepository.findById(10L)).thenReturn(Optional.of(plan));
        when(suscripcionRepository.existsByUsuarioIdAndEstado(1L, EstadoSuscripcion.ACTIVA)).thenReturn(false);
        when(suscripcionRepository.save(any(Suscripcion.class))).thenAnswer(inv -> inv.getArgument(0));

        Suscripcion resultado = suscripcionService.suscribir(1L, 10L);

        assertThat(resultado.getEstado()).isEqualTo(EstadoSuscripcion.ACTIVA);
        assertThat(resultado.getFechaFin()).isEqualTo(resultado.getFechaInicio().plusDays(30));
        verify(suscripcionRepository).save(any(Suscripcion.class));
    }

    @Test
    @DisplayName("Rechaza la suscripción si el usuario ya tiene una activa")
    void rechazarSegundaSuscripcionActiva() {
        Usuario usuario = Usuario.builder().id(1L).build();
        PlanSuscripcion plan = PlanSuscripcion.builder().id(10L).duracionDias(30).activo(true).build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(planSuscripcionRepository.findById(10L)).thenReturn(Optional.of(plan));
        when(suscripcionRepository.existsByUsuarioIdAndEstado(1L, EstadoSuscripcion.ACTIVA)).thenReturn(true);

        assertThatThrownBy(() -> suscripcionService.suscribir(1L, 10L))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("ya cuenta con una suscripción activa");

        verify(suscripcionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Rechaza la suscripción a un plan inactivo")
    void rechazarSuscripcionAPlanInactivo() {
        Usuario usuario = Usuario.builder().id(1L).build();
        PlanSuscripcion plan = PlanSuscripcion.builder().id(10L).duracionDias(30).activo(false).build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(planSuscripcionRepository.findById(10L)).thenReturn(Optional.of(plan));

        assertThatThrownBy(() -> suscripcionService.suscribir(1L, 10L))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("no está disponible");

        verify(suscripcionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Cancela una suscripción activa perteneciente al usuario")
    void cancelarSuscripcionActivaPropia() {
        Usuario usuario = Usuario.builder().id(1L).build();
        Suscripcion suscripcion = Suscripcion.builder()
                .id(5L).usuario(usuario).estado(EstadoSuscripcion.ACTIVA)
                .fechaInicio(LocalDate.now()).fechaFin(LocalDate.now().plusDays(30)).build();

        when(suscripcionRepository.findById(5L)).thenReturn(Optional.of(suscripcion));

        suscripcionService.cancelar(1L, 5L);

        assertThat(suscripcion.getEstado()).isEqualTo(EstadoSuscripcion.CANCELADA);
        verify(suscripcionRepository).save(suscripcion);
    }

    @Test
    @DisplayName("Rechaza cancelar una suscripción que pertenece a otro usuario")
    void rechazarCancelarSuscripcionDeOtroUsuario() {
        Usuario otroUsuario = Usuario.builder().id(2L).build();
        Suscripcion suscripcion = Suscripcion.builder()
                .id(5L).usuario(otroUsuario).estado(EstadoSuscripcion.ACTIVA)
                .fechaInicio(LocalDate.now()).fechaFin(LocalDate.now().plusDays(30)).build();

        when(suscripcionRepository.findById(5L)).thenReturn(Optional.of(suscripcion));

        assertThatThrownBy(() -> suscripcionService.cancelar(1L, 5L))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("no pertenece a este usuario");

        verify(suscripcionRepository, never()).save(any());
    }
}
