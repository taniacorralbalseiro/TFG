package com.tfg.controller;

import com.tfg.model.Empleado;
import com.tfg.model.enumerados.RolEmpleado;
import com.tfg.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // POST /api/empleados
    @PostMapping
    public ResponseEntity<Empleado> create(@Valid @RequestBody Empleado nuevo, UriComponentsBuilder ucb) {
        Empleado creado = empleadoService.create(nuevo);
        URI location = ucb.path("/api/empleados/{id}").buildAndExpand(creado.getPublicId()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    // GET /api/empleados/{publicId}
    @GetMapping("/{publicId}")
    public Empleado getByPublicId(@PathVariable UUID publicId) {
        return empleadoService.getByPublicId(publicId);
    }

    // GET /api/empleados/nif/{nif}
    @GetMapping("/nif/{nif}")
    public Empleado getByNif(@PathVariable String nif) {
        return empleadoService.getByNif(nif);
    }

    // GET /api/empleados?centroId=...&rol=...
    @GetMapping
    public Page<Empleado> list(
            @RequestParam(required = false) UUID centroId,
            @RequestParam(required = false) RolEmpleado rol,
            @PageableDefault(size = 20) Pageable pageable) {

        if (centroId != null) {
            return empleadoService.listByCentro(centroId, pageable);
        } else if (rol != null) {
            return empleadoService.listByRol(rol, pageable);
        }
        return empleadoService.findAll(pageable);
    }

    // PUT /api/empleados/{publicId}
    @PutMapping("/{publicId}")
    public Empleado update(@PathVariable UUID publicId, @Valid @RequestBody Empleado cambios) {
        return empleadoService.update(publicId, cambios);
    }

    // DELETE /api/empleados/{publicId}
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> delete(@PathVariable UUID publicId) {
        empleadoService.delete(publicId);
        return ResponseEntity.noContent().build();
    }
}
