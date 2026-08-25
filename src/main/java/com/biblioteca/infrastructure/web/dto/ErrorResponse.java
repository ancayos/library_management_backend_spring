package com.biblioteca.infrastructure.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estructura unificada de error devuelta por el GlobalExceptionHandler")
public class ErrorResponse {

    @Schema(description = "Mensaje descriptivo del error", example = "Libro con id [5] no fue encontrado")
    private String mensaje;

    @Schema(description = "Codigo interno de la categoria del error", example = "RESOURCE_NOT_FOUND")
    private String codigo;

    @Schema(description = "Codigo de estado HTTP", example = "404")
    private int status;

    @Schema(description = "Momento en que ocurrio el error")
    private LocalDateTime timestamp;

    @Schema(description = "Detalle de errores de validacion por campo (opcional)")
    private List<String> detalles;
}
