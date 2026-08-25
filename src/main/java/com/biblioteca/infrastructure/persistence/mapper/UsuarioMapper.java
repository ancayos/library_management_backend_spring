package com.biblioteca.infrastructure.persistence.mapper;

import com.biblioteca.domain.model.Usuario;
import com.biblioteca.infrastructure.persistence.entity.UsuarioEntity;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return Usuario.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .build();
    }

    public static UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;
        return UsuarioEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .email(domain.getEmail())
                .build();
    }
}
