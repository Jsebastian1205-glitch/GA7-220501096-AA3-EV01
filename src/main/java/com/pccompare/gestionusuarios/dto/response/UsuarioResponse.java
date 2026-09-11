package com.pccompare.gestionusuarios.dto.response;

import com.pccompare.gestionusuarios.model.EstadoUsuario;
import com.pccompare.gestionusuarios.model.Rol;

import java.time.LocalDateTime;

/**
 * Representación pública de un {@link com.pccompare.gestionusuarios.model.Usuario}.
 * Nunca incluye la contraseña, evitando así exponer datos sensibles en la API.
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin Lombok)
 * para evitar depender de un procesador de anotaciones.
 */
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private Rol rol;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;

    public UsuarioResponse() {
    }

    public UsuarioResponse(Long id, String nombre, String apellido, String username, String email,
                            Rol rol, EstadoUsuario estado, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

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

    public static UsuarioResponseBuilder builder() {
        return new UsuarioResponseBuilder();
    }

    /** Builder fluido para construir instancias de {@link UsuarioResponse}. */
    public static class UsuarioResponseBuilder {
        private Long id;
        private String nombre;
        private String apellido;
        private String username;
        private String email;
        private Rol rol;
        private EstadoUsuario estado;
        private LocalDateTime fechaRegistro;

        public UsuarioResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UsuarioResponseBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UsuarioResponseBuilder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public UsuarioResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UsuarioResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UsuarioResponseBuilder rol(Rol rol) {
            this.rol = rol;
            return this;
        }

        public UsuarioResponseBuilder estado(EstadoUsuario estado) {
            this.estado = estado;
            return this;
        }

        public UsuarioResponseBuilder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public UsuarioResponse build() {
            return new UsuarioResponse(id, nombre, apellido, username, email, rol, estado, fechaRegistro);
        }
    }
}
