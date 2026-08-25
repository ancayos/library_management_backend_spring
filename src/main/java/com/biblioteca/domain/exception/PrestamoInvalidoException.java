package com.biblioteca.domain.exception;

/**
 * Se lanza ante operaciones invalidas sobre un prestamo (ej. devolver uno ya devuelto).
 * Es interceptada de forma centralizada por el GlobalExceptionHandler -> 400 BAD_REQUEST.
 */
public class PrestamoInvalidoException extends RuntimeException {

    public PrestamoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
