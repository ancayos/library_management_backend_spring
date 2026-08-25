package com.biblioteca.infrastructure.persistence.repository;

import com.biblioteca.infrastructure.persistence.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroJpaRepository extends JpaRepository<LibroEntity, Long> {

    // Spring Data JPA resuelve las operaciones CRUD de forma nativa, sin SQL manual
    Optional<LibroEntity> findByIsbn(String isbn);
}
