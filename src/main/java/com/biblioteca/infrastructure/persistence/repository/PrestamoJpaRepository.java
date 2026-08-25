package com.biblioteca.infrastructure.persistence.repository;

import com.biblioteca.infrastructure.persistence.entity.PrestamoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoJpaRepository extends JpaRepository<PrestamoEntity, Long> {

    List<PrestamoEntity> findByUsuarioId(Long usuarioId);
}
