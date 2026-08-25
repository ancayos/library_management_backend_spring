package com.biblioteca.domain.exception;

/**
 * Se lanza cuando un recurso (Libro, Usuario, Prestamo) solicitado no existe.
 * Es interceptada de forma centralizada por el GlobalExceptionHandler -> 404 NOT_FOUND.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public static RecursoNoEncontradoException de(String recurso, Object id) {
        return new RecursoNoEncontradoException(recurso + " con id [" + id + "] no fue encontrado");
    }
}
