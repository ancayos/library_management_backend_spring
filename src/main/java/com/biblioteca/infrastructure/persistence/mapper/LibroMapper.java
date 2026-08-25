package com.biblioteca.infrastructure.persistence.mapper;

import com.biblioteca.domain.model.Libro;
import com.biblioteca.infrastructure.persistence.entity.LibroEntity;

public final class LibroMapper {

    private LibroMapper() {
    }

    public static Libro toDomain(LibroEntity entity) {
        if (entity == null) return null;
        return Libro.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .isbn(entity.getIsbn())
                .stockTotal(entity.getStockTotal())
                .stockDisponible(entity.getStockDisponible())
                .build();
    }

    public static LibroEntity toEntity(Libro domain) {
        if (domain == null) return null;
        return LibroEntity.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .autor(domain.getAutor())
                .isbn(domain.getIsbn())
                .stockTotal(domain.getStockTotal())
                .stockDisponible(domain.getStockDisponible())
                .build();
    }
}
