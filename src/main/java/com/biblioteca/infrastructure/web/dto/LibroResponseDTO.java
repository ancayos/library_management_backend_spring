package com.biblioteca.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de un libro devuelta por la API")
public class LibroResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Cien anios de soledad")
    private String titulo;

    @Schema(example = "Gabriel Garcia Marquez")
    private String autor;

    @Schema(example = "978-0307474728")
    private String isbn;

    @Schema(example = "5")
    private Integer stockTotal;

    @Schema(example = "3")
    private Integer stockDisponible;
}
