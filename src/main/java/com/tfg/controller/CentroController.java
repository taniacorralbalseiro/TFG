package com.tfg.controller;

import com.tfg.model.Centro;
import com.tfg.service.CentroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/centros")
@RequiredArgsConstructor
public class CentroController {

    private final CentroService centroService;

    // Crear un nuevo centro
    @PostMapping
    public ResponseEntity<Centro> create(@Valid @RequestBody Centro nuevo, UriComponentsBuilder ucb) {
        Centro creado = centroService.create(nuevo);
        URI location = ucb.path("/api/centros/{id}").buildAndExpand(creado.getId()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    // Actualizar un centro existente
    @PutMapping("/{id}")
    public Centro update(@PathVariable Long id, @RequestBody Centro cambios) {
        return centroService.update(id, cambios);
    }

    // Obtener un centro por id
    @GetMapping("/{id}")
    public Centro getById(@PathVariable Long id) {
        return centroService.getById(id);
    }

    // Eliminar un centro
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        centroService.delete(id);
    }

    // Listar todos los centros con paginación
    @GetMapping
    public Page<Centro> list(Pageable pageable) {
        return centroService.list(pageable);
    }

    @GetMapping("/by-email")
    public ResponseEntity<Centro> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(centroService.getByEmail(email));
    }

    @GetMapping("/exists/by-email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(centroService.existsEmail(email));
    }


    // Buscar por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public Page<Centro> findByCiudad(@PathVariable String ciudad, Pageable pageable) {
        return centroService.findByCiudad(ciudad, pageable);
    }

    // Buscar por provincia
    @GetMapping("/provincia/{provincia}")
    public Page<Centro> findByProvincia(@PathVariable String provincia, Pageable pageable) {
        return centroService.findByProvincia(provincia, pageable);
    }

    // Buscar por ciudad y provincia
    @GetMapping("/ciudad/{ciudad}/provincia/{provincia}")
    public Page<Centro> findByCiudadAndProvincia(@PathVariable String ciudad,
                                                 @PathVariable String provincia,
                                                 Pageable pageable) {
        return centroService.findByCiudadAndProvincia(ciudad, provincia, pageable);
    }

    // Búsqueda por nombre parcial
    @GetMapping("/search/nombre/{parcialNombre}")
    public Page<Centro> searchByNombre(@PathVariable String parcialNombre, Pageable pageable) {
        return centroService.searchByNombre(parcialNombre, pageable);
    }
}

