package com.biblioteca.infrastructure.persistence.mapper;

import com.biblioteca.domain.model.Prestamo;
import com.biblioteca.infrastructure.persistence.entity.PrestamoEntity;

public final class PrestamoMapper {

    private PrestamoMapper() {
    }

    public static Prestamo toDomain(PrestamoEntity entity) {
        if (entity == null) return null;
        return Prestamo.builder()
                .id(entity.getId())
                .libroId(entity.getLibroId())
                .usuarioId(entity.getUsuarioId())
                .fechaPrestamo(entity.getFechaPrestamo())
                .fechaDevolucion(entity.getFechaDevolucion())
                .estado(entity.getEstado())
                .build();
    }

    public static PrestamoEntity toEntity(Prestamo domain) {
        if (domain == null) return null;
        return PrestamoEntity.builder()
                .id(domain.getId())
                .libroId(domain.getLibroId())
                .usuarioId(domain.getUsuarioId())
                .fechaPrestamo(domain.getFechaPrestamo())
                .fechaDevolucion(domain.getFechaDevolucion())
                .estado(domain.getEstado())
                .build();
    }
}
