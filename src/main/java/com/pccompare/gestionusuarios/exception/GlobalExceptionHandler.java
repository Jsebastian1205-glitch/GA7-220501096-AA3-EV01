package com.pccompare.gestionusuarios.exception;

import com.pccompare.gestionusuarios.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Manejador centralizado de excepciones del módulo. Traduce cada excepción
 * de negocio a una respuesta HTTP coherente, con un cuerpo uniforme
 * ({@link ErrorResponse}) para que el front-end/consumidor de la API
 * siempre reciba el mismo formato de error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex, HttpServletRequest req) {
        return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), req, null);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicado(RecursoDuplicadoException ex, HttpServletRequest req) {
        return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), req, null);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaNegocio(ReglaNegocioException ex, HttpServletRequest req) {
        return construirRespuesta(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req, null);
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ErrorResponse> manejarCredenciales(CredencialesInvalidasException ex, HttpServletRequest req) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, ex.getMessage(), req, null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> manejarBadCredentials(HttpServletRequest req) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, "Credenciales inválidas", req, null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> manejarAccesoDenegado(HttpServletRequest req) {
        return construirRespuesta(HttpStatus.FORBIDDEN, "No tiene permisos para realizar esta acción", req, null);
    }

    /** Captura los errores de las anotaciones de Bean Validation (@NotBlank, @Email, etc.). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        return construirRespuesta(HttpStatus.BAD_REQUEST, "Error de validación en los datos enviados", req, detalles);
    }

    /** Cualquier otra excepción no controlada explícitamente: se evita exponer detalles internos. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGenerica(Exception ex, HttpServletRequest req) {
        return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en el servidor", req, null);
    }

    private ResponseEntity<ErrorResponse> construirRespuesta(HttpStatus status, String mensaje,
                                                               HttpServletRequest req, List<String> detalles) {
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .mensaje(mensaje)
                .ruta(req.getRequestURI())
                .detalles(detalles)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
