package com.biblioteca.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de entrada para crear o actualizar un libro")
public class LibroRequestDTO {

    @NotBlank(message = "El titulo es obligatorio")
    @Schema(example = "Cien anios de soledad")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Schema(example = "Gabriel Garcia Marquez")
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio")
    @Schema(example = "978-0307474728")
    private String isbn;

    @NotNull(message = "El stock total es obligatorio")
    @Min(value = 0, message = "El stock total no puede ser negativo")
    @Schema(example = "5")
    private Integer stockTotal;
}
