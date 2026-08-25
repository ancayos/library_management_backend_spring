package com.biblioteca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de dominio puro. NO contiene anotaciones de persistencia (JPA),
 * conforme a la regla de aislamiento de capas del Hito 4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer stockTotal;
    private Integer stockDisponible;

    public boolean tieneStockDisponible() {
        return stockDisponible != null && stockDisponible > 0;
    }

    public void decrementarStock() {
        this.stockDisponible = this.stockDisponible - 1;
    }

    public void incrementarStock() {
        if (this.stockDisponible < this.stockTotal) {
            this.stockDisponible = this.stockDisponible + 1;
        }
    }
}
