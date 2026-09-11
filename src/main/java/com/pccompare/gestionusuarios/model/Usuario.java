package com.pccompare.gestionusuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un usuario de la plataforma PC Compare.
 *
 * <p>Corresponde a la clase "Usuario" del diagrama de clases del módulo de
 * gestión de usuarios. Un usuario puede tener el rol {@link Rol#ADMIN} o
 * {@link Rol#USUARIO} y, mientras esté {@link EstadoUsuario#ACTIVO}, puede
 * autenticarse y administrar su propia suscripción.
 */
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_usuario_username", columnNames = "username")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    /** Nombre de usuario único usado como alternativa al correo para el login. */
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    /** Contraseña almacenada siempre cifrada (BCrypt); nunca se expone en las respuestas. */
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Rol rol = Rol.USUARIO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    /** Historial de suscripciones del usuario (relación 1:N). */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Suscripcion> suscripciones = new ArrayList<>();

    @PrePersist
    protected void alPersistir() {
        this.fechaRegistro = LocalDateTime.now();
    }

    /**
     * Indica si el usuario puede autenticarse en el sistema.
     * Regla de negocio: solo los usuarios con estado ACTIVO pueden iniciar sesión.
     */
    public boolean puedeAutenticarse() {
        return this.estado == EstadoUsuario.ACTIVO;
    }

    public boolean esAdministrador() {
        return this.rol == Rol.ADMIN;
    }
}
