package com.tfg.controller;

import com.tfg.model.Grupo;
import com.tfg.service.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;

    // CREATE
    @PostMapping
    public ResponseEntity<Grupo> create(@Valid @RequestBody Grupo body, UriComponentsBuilder uriBuilder) {
        Grupo creado = grupoService.create(body);
        return ResponseEntity
                .created(uriBuilder.path("/grupos/{id}").buildAndExpand(creado.getId()).toUri())
                .body(creado);
    }

    // READ by ID
    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.getById(id));
    }

    // UPDATE (full)
    @PutMapping("/{id}")
    public ResponseEntity<Grupo> update(@PathVariable Long id, @Valid @RequestBody Grupo cambios) {
        return ResponseEntity.ok(grupoService.update(id, cambios));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        grupoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // LIST paginado
    @GetMapping
    public ResponseEntity<Page<Grupo>> list(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(grupoService.list(pageable));
    }

    // Exists by descripcion (útil para validaciones en cliente)
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByDescripcion(@RequestParam String descripcion) {
        return ResponseEntity.ok(grupoService.existsByDescripcion(descripcion));
    }

    // Get único por descripción exacta
    @GetMapping("/by-descripcion")
    public ResponseEntity<Grupo> getByDescripcion(@RequestParam String descripcion) {
        return ResponseEntity.ok(grupoService.getByDescripcion(descripcion));
    }

    // Búsqueda por descripción (contiene, case-insensitive) paginada
    @GetMapping("/search")
    public ResponseEntity<Page<Grupo>> searchDescripcion(
            @RequestParam String descripcion,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(grupoService.searchDescripcion(descripcion, pageable));
    }

    // Filtrar por capacidad exacta (paginado)
    @GetMapping("/capacidad")
    public ResponseEntity<Page<Grupo>> findByCapacidad(
            @RequestParam Integer capacidad,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(grupoService.findByCapacidad(capacidad, pageable));
    }

    // Filtrar por capacidad mínima (>=) paginado
    @GetMapping("/capacidad-minima")
    public ResponseEntity<Page<Grupo>> findByCapacidadMinima(
            @RequestParam Integer minima,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(grupoService.findByCapacidadMinima(minima, pageable));
    }
}
