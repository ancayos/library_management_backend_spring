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
@Schema(description = "Representacion de un usuario devuelta por la API")
public class UsuarioResponseDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Maria Perez")
    private String nombre;

    @Schema(example = "maria.perez@correo.com")
    private String email;
}
