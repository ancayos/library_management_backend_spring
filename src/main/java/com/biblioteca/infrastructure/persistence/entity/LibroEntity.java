package com.biblioteca.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de infraestructura mapeada a la tabla "libros".
 * Las anotaciones JPA viven exclusivamente aqui, nunca en el modelo de dominio.
 */
@Entity
@Table(name = "libros")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "stock_total", nullable = false)
    private Integer stockTotal;

    @Column(name = "stock_disponible", nullable = false)
    private Integer stockDisponible;
}
