package com.biblioteca.application.service;

import com.biblioteca.domain.exception.RecursoNoEncontradoException;
import com.biblioteca.domain.model.Libro;
import com.biblioteca.infrastructure.persistence.entity.LibroEntity;
import com.biblioteca.infrastructure.persistence.mapper.LibroMapper;
import com.biblioteca.infrastructure.persistence.repository.LibroJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibroService {

    private final LibroJpaRepository libroJpaRepository;

    public List<Libro> listarTodos() {
        return libroJpaRepository.findAll().stream()
                .map(LibroMapper::toDomain)
                .toList();
    }

    public Libro buscarPorId(Long id) {
        LibroEntity entity = libroJpaRepository.findById(id)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Libro", id));
        return LibroMapper.toDomain(entity);
    }

    public Libro crear(Libro libro) {
        libro.setId(null);
        if (libro.getStockDisponible() == null) {
            libro.setStockDisponible(libro.getStockTotal());
        }
        LibroEntity guardado = libroJpaRepository.save(LibroMapper.toEntity(libro));
        return LibroMapper.toDomain(guardado);
    }

    public Libro actualizar(Long id, Libro cambios) {
        LibroEntity existente = libroJpaRepository.findById(id)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Libro", id));

        existente.setTitulo(cambios.getTitulo());
        existente.setAutor(cambios.getAutor());
        existente.setIsbn(cambios.getIsbn());
        existente.setStockTotal(cambios.getStockTotal());
        existente.setStockDisponible(cambios.getStockDisponible());

        return LibroMapper.toDomain(libroJpaRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!libroJpaRepository.existsById(id)) {
            throw RecursoNoEncontradoException.de("Libro", id);
        }
        libroJpaRepository.deleteById(id);
    }
}
