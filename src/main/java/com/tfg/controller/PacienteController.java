package com.tfg.controller;

import com.tfg.model.Paciente;
import com.tfg.service.PacienteService;
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
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // POST /api/pacientes
    @PostMapping
    public ResponseEntity<Paciente> create(@Valid @RequestBody Paciente nuevo, UriComponentsBuilder ucb) {
        Paciente creado = pacienteService.create(nuevo);
        URI location = ucb.path("/api/pacientes/{id}").buildAndExpand(creado.getPublicId()).toUri();
        return ResponseEntity.created(location).body(creado);
    }

    // GET /api/pacientes/{publicId}
    @GetMapping("/{publicId}")
    public Paciente getByPublicId(@PathVariable UUID publicId) {
        return pacienteService.getByPublicId(publicId);
    }

    // GET /api/pacientes/nif/{nif}
    @GetMapping("/nif/{nif}")
    public Paciente getByNif(@PathVariable String nif) {
        return pacienteService.getByNif(nif);
    }

    // GET /api/pacientes?centroId=...
    @GetMapping
    public Page<Paciente> list(
            @RequestParam(required = false) UUID centroId,
            @PageableDefault(size = 20) Pageable pageable) {
        if (centroId != null) {
            return pacienteService.listByCentro(centroId, pageable);
        }
        return pacienteService.findAll(pageable);
    }

    // PUT /api/pacientes/{publicId}
    @PutMapping("/{publicId}")
    public Paciente update(@PathVariable UUID publicId, @Valid @RequestBody Paciente cambios) {
        return pacienteService.update(publicId, cambios);
    }

    // DELETE /api/pacientes/{publicId}
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> delete(@PathVariable UUID publicId) {
        pacienteService.delete(publicId);
        return ResponseEntity.noContent().build();
    }
}
