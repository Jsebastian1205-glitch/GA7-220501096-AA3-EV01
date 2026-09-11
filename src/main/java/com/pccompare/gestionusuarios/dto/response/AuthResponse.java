package com.pccompare.gestionusuarios.dto.response;

/**
 * Respuesta del login/registro: token JWT + datos básicos del usuario autenticado.
 *
 * <p>Getters, setters, constructores y builder escritos a mano (sin Lombok)
 * para evitar depender de un procesador de anotaciones.
 */
public class AuthResponse {

    private String token;
    private String tipo = "Bearer";
    private UsuarioResponse usuario;

    public AuthResponse() {
    }

    public AuthResponse(String token, String tipo, UsuarioResponse usuario) {
        this.token = token;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    /** Builder fluido para construir instancias de {@link AuthResponse}. */
    public static class AuthResponseBuilder {
        private String token;
        // Mantiene el mismo valor por defecto que antes proveía @Builder.Default.
        private String tipo = "Bearer";
        private UsuarioResponse usuario;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public AuthResponseBuilder usuario(UsuarioResponse usuario) {
            this.usuario = usuario;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, tipo, usuario);
        }
    }
}
