package com.biblioteca.infrastructure.web.dto;

import com.biblioteca.domain.model.EstadoPrestamo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representacion de un prestamo devuelta por la API")
public class PrestamoResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private Long libroId;

    @Schema(example = "1")
    private Long usuarioId;

    @Schema(example = "2026-08-23")
    private LocalDate fechaPrestamo;

    @Schema(example = "null")
    private LocalDate fechaDevolucion;

    @Schema(example = "ACTIVO")
    private EstadoPrestamo estado;
}
