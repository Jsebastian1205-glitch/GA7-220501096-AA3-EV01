package com.pccompare.gestionusuarios.model;

import jakarta.persistence.*;

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
 *
 * <p>Nota de implementación: los getters, setters, constructores y el
 * builder se escriben aquí de forma explícita (sin Lombok) para que el
 * proyecto compile de manera predecible en cualquier equipo, sin depender
 * de un procesador de anotaciones en tiempo de compilación.
 */
@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_usuario_username", columnNames = "username")
        }
)
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
    private Rol rol = Rol.USUARIO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    /** Historial de suscripciones del usuario (relación 1:N). */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Suscripcion> suscripciones = new ArrayList<>();

    public Usuario() {
        // Constructor requerido por JPA/Hibernate.
    }

    public Usuario(Long id, String nombre, String apellido, String username, String email, String password,
                   Rol rol, EstadoUsuario estado, LocalDateTime fechaRegistro, List<Suscripcion> suscripciones) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.suscripciones = suscripciones != null ? suscripciones : new ArrayList<>();
    }

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

    // ---------------------------------------------------------------
    // Getters y setters
    // ---------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }

    public void setSuscripciones(List<Suscripcion> suscripciones) {
        this.suscripciones = suscripciones;
    }

    // ---------------------------------------------------------------
    // Builder (equivalente escrito a mano al @Builder de Lombok)
    // ---------------------------------------------------------------

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    /** Builder fluido para construir instancias de {@link Usuario} de forma legible. */
    public static class UsuarioBuilder {
        private Long id;
        private String nombre;
        private String apellido;
        private String username;
        private String email;
        private String password;
        private Rol rol = Rol.USUARIO;
        private EstadoUsuario estado = EstadoUsuario.ACTIVO;
        private LocalDateTime fechaRegistro;
        private List<Suscripcion> suscripciones = new ArrayList<>();

        public UsuarioBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UsuarioBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UsuarioBuilder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public UsuarioBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UsuarioBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UsuarioBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UsuarioBuilder rol(Rol rol) {
            this.rol = rol;
            return this;
        }

        public UsuarioBuilder estado(EstadoUsuario estado) {
            this.estado = estado;
            return this;
        }

        public UsuarioBuilder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public UsuarioBuilder suscripciones(List<Suscripcion> suscripciones) {
            this.suscripciones = suscripciones;
            return this;
        }

        public Usuario build() {
            return new Usuario(id, nombre, apellido, username, email, password, rol, estado, fechaRegistro, suscripciones);
        }
    }
}
