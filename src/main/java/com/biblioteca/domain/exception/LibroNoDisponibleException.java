package com.biblioteca.domain.exception;

/**
 * Regla de negocio: se lanza cuando se intenta prestar un libro sin stock disponible.
 * Es interceptada de forma centralizada por el GlobalExceptionHandler -> 422 UNPROCESSABLE_ENTITY.
 */
public class LibroNoDisponibleException extends RuntimeException {

    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public static LibroNoDisponibleException paraLibro(Long libroId) {
        return new LibroNoDisponibleException(
                "El libro con id [" + libroId + "] no tiene stock disponible para prestamo");
    }
}
