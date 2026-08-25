package com.biblioteca.application.service;

import com.biblioteca.domain.exception.LibroNoDisponibleException;
import com.biblioteca.domain.exception.PrestamoInvalidoException;
import com.biblioteca.domain.exception.RecursoNoEncontradoException;
import com.biblioteca.domain.model.EstadoPrestamo;
import com.biblioteca.domain.model.Prestamo;
import com.biblioteca.infrastructure.persistence.entity.LibroEntity;
import com.biblioteca.infrastructure.persistence.entity.PrestamoEntity;
import com.biblioteca.infrastructure.persistence.entity.UsuarioEntity;
import com.biblioteca.infrastructure.persistence.mapper.PrestamoMapper;
import com.biblioteca.infrastructure.persistence.repository.LibroJpaRepository;
import com.biblioteca.infrastructure.persistence.repository.PrestamoJpaRepository;
import com.biblioteca.infrastructure.persistence.repository.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrestamoService {

    private final PrestamoJpaRepository prestamoJpaRepository;
    private final LibroJpaRepository libroJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;

    public List<Prestamo> listarTodos() {
        return prestamoJpaRepository.findAll().stream()
                .map(PrestamoMapper::toDomain)
                .toList();
    }

    public Prestamo buscarPorId(Long id) {
        PrestamoEntity entity = prestamoJpaRepository.findById(id)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Prestamo", id));
        return PrestamoMapper.toDomain(entity);
    }

    /**
     * Registra un nuevo prestamo. Lanza LibroNoDisponibleException (422) si no hay stock.
     */
    public Prestamo crear(Long libroId, Long usuarioId) {
        LibroEntity libro = libroJpaRepository.findById(libroId)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Libro", libroId));

        UsuarioEntity usuario = usuarioJpaRepository.findById(usuarioId)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Usuario", usuarioId));

        if (libro.getStockDisponible() == null || libro.getStockDisponible() <= 0) {
            throw LibroNoDisponibleException.paraLibro(libroId);
        }

        libro.setStockDisponible(libro.getStockDisponible() - 1);
        libroJpaRepository.save(libro);

        PrestamoEntity nuevo = PrestamoEntity.builder()
                .libroId(libro.getId())
                .usuarioId(usuario.getId())
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucion(null)
                .estado(EstadoPrestamo.ACTIVO)
                .build();

        return PrestamoMapper.toDomain(prestamoJpaRepository.save(nuevo));
    }

    /**
     * Marca un prestamo como devuelto y repone el stock del libro.
     */
    public Prestamo devolver(Long prestamoId) {
        PrestamoEntity prestamo = prestamoJpaRepository.findById(prestamoId)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Prestamo", prestamoId));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new PrestamoInvalidoException(
                    "El prestamo con id [" + prestamoId + "] ya fue devuelto previamente");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamoJpaRepository.save(prestamo);

        LibroEntity libro = libroJpaRepository.findById(prestamo.getLibroId())
                .orElseThrow(() -> RecursoNoEncontradoException.de("Libro", prestamo.getLibroId()));
        if (libro.getStockDisponible() < libro.getStockTotal()) {
            libro.setStockDisponible(libro.getStockDisponible() + 1);
            libroJpaRepository.save(libro);
        }

        return PrestamoMapper.toDomain(prestamo);
    }
}
