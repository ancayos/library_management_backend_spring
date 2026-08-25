package com.biblioteca.infrastructure.web.controller;

import com.biblioteca.application.service.LibroService;
import com.biblioteca.domain.model.Libro;
import com.biblioteca.infrastructure.web.dto.ErrorResponse;
import com.biblioteca.infrastructure.web.dto.LibroRequestDTO;
import com.biblioteca.infrastructure.web.dto.LibroResponseDTO;
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
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Gestion del catalogo de libros de la biblioteca")
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    @Operation(summary = "Listar todos los libros", description = "Retorna el catalogo completo de libros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    public ResponseEntity<List<LibroResponseDTO>> listar() {
        List<LibroResponseDTO> libros = libroService.listarTodos().stream()
                .map(this::toResponseDTO)
                .toList();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un libro por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@PathVariable Long id) {
        Libro libro = libroService.buscarPorId(id);
        return ResponseEntity.ok(toResponseDTO(libro));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo libro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<LibroResponseDTO> crear(@Valid @RequestBody LibroRequestDTO request) {
        Libro libro = Libro.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .isbn(request.getIsbn())
                .stockTotal(request.getStockTotal())
                .stockDisponible(request.getStockTotal())
                .build();

        Libro creado = libroService.crear(libro);
        return new ResponseEntity<>(toResponseDTO(creado), HttpStatus.CREATED); // 201
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de un libro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<LibroResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody LibroRequestDTO request) {
        Libro cambios = Libro.builder()
                .titulo(request.getTitulo())
                .autor(request.getAutor())
                .isbn(request.getIsbn())
                .stockTotal(request.getStockTotal())
                .stockDisponible(request.getStockTotal())
                .build();

        Libro actualizado = libroService.actualizar(id, cambios);
        return ResponseEntity.ok(toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un libro del catalogo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }

    private LibroResponseDTO toResponseDTO(Libro libro) {
        return LibroResponseDTO.builder()
                .id(libro.getId())
                .titulo(libro.getTitulo())
                .autor(libro.getAutor())
                .isbn(libro.getIsbn())
                .stockTotal(libro.getStockTotal())
                .stockDisponible(libro.getStockDisponible())
                .build();
    }
}
