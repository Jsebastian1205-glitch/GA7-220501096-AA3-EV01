package com.pccompare.gestionusuarios.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos editables del perfil (historia de usuario: "Como usuario quiero
 * actualizar mi perfil"). La contraseña y el cambio de rol/estado se
 * manejan en endpoints y DTO separados por seguridad y trazabilidad.
 *
 * <p>Getters, setters y constructores escritos a mano (sin Lombok).
 */
public class ActualizarUsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50)
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    public ActualizarUsuarioRequest() {
    }

    public ActualizarUsuarioRequest(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
