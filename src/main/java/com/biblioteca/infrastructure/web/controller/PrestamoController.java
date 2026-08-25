package com.biblioteca.infrastructure.web.controller;

import com.biblioteca.application.service.PrestamoService;
import com.biblioteca.domain.model.Prestamo;
import com.biblioteca.infrastructure.web.dto.ErrorResponse;
import com.biblioteca.infrastructure.web.dto.PrestamoRequestDTO;
import com.biblioteca.infrastructure.web.dto.PrestamoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
@Tag(name = "Prestamos", description = "Registro y devolucion de prestamos de libros")
public class PrestamoController {

    private final PrestamoService prestamoService;

    @GetMapping
    @Operation(summary = "Listar todos los prestamos")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")})
    public ResponseEntity<List<PrestamoResponseDTO>> listar() {
        List<PrestamoResponseDTO> prestamos = prestamoService.listarTodos().stream()
                .map(this::toResponseDTO)
                .toList();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un prestamo por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Prestamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PrestamoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.buscarPorId(id);
        return ResponseEntity.ok(toResponseDTO(prestamo));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo prestamo",
            description = "Descuenta stock del libro. Falla con 422 si no hay ejemplares disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Prestamo registrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro o usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "El libro no tiene stock disponible",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PrestamoResponseDTO> crear(@Valid @RequestBody PrestamoRequestDTO request) {
        Prestamo creado = prestamoService.crear(request.getLibroId(), request.getUsuarioId());
        return new ResponseEntity<>(toResponseDTO(creado), HttpStatus.CREATED); // 201
    }

    @PatchMapping("/{id}/devolucion")
    @Operation(summary = "Registrar la devolucion de un prestamo",
            description = "Marca el prestamo como DEVUELTO y repone el stock del libro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolucion registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "El prestamo ya habia sido devuelto",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PrestamoResponseDTO> devolver(@PathVariable Long id) {
        Prestamo devuelto = prestamoService.devolver(id);
        return ResponseEntity.ok(toResponseDTO(devuelto));
    }

    private PrestamoResponseDTO toResponseDTO(Prestamo prestamo) {
        return PrestamoResponseDTO.builder()
                .id(prestamo.getId())
                .libroId(prestamo.getLibroId())
                .usuarioId(prestamo.getUsuarioId())
                .fechaPrestamo(prestamo.getFechaPrestamo())
                .fechaDevolucion(prestamo.getFechaDevolucion())
                .estado(prestamo.getEstado())
                .build();
    }
}
