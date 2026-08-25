package com.biblioteca.infrastructure.web.controller;

import com.biblioteca.domain.exception.LibroNoDisponibleException;
import com.biblioteca.domain.exception.PrestamoInvalidoException;
import com.biblioteca.domain.exception.RecursoNoEncontradoException;
import com.biblioteca.infrastructure.web.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interceptor perimetral centralizado (Pilar 1 del Hito 4).
 * Atrapa TODAS las excepciones de negocio y tecnicas, devolviendo siempre
 * un JSON unificado (ErrorResponse) y jamas un stacktrace nativo del servidor.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponse error = construir(ex.getMessage(), "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND, null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(LibroNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> handleLibroNoDisponible(LibroNoDisponibleException ex) {
        ErrorResponse error = construir(ex.getMessage(), "BUSINESS_RULE_VIOLATION", HttpStatus.UNPROCESSABLE_ENTITY, null);
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY); // 422
    }

    @ExceptionHandler(PrestamoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handlePrestamoInvalido(PrestamoInvalidoException ex) {
        ErrorResponse error = construir(ex.getMessage(), "INVALID_OPERATION", HttpStatus.BAD_REQUEST, null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidacion(MethodArgumentNotValidException ex) {
        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        ErrorResponse error = construir("Error de validacion en los datos enviados",
                "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, detalles);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorResponse error = construir(ex.getMessage(), "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = construir(ex.getMessage(), "BAD_REQUEST", HttpStatus.BAD_REQUEST, null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400
    }

    /**
     * Red de seguridad final: cualquier excepcion no controlada explicitamente
     * NUNCA se expone como stacktrace crudo al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenerica(Exception ex) {
        ErrorResponse error = construir("Ocurrio un error inesperado en el servidor",
                "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    private ErrorResponse construir(String mensaje, String codigo, HttpStatus status, List<String> detalles) {
        return ErrorResponse.builder()
                .mensaje(mensaje)
                .codigo(codigo)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .detalles(detalles)
                .build();
    }
}
