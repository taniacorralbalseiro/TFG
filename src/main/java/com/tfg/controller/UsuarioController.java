package com.tfg.controller;


import com.tfg.model.Usuario;
import com.tfg.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /api/usuarios/{publicId}
    @GetMapping("/{publicId}")
    public Usuario getByPublicId(@PathVariable UUID publicId) {
        return usuarioService.getByPublicId(publicId);
    }

    // GET /api/usuarios/nif/{nif}
    @GetMapping("/nif/{nif}")
    public Usuario getByNif(@PathVariable String nif) {
        return usuarioService.getByNif(nif);
    }

    // GET /api/usuarios
    @GetMapping
    public Page<Usuario> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return usuarioService.findAll(pageable);
    }

    // PUT /api/usuarios/{publicId}
    @PutMapping("/{publicId}")
    public Usuario update(@PathVariable UUID publicId, @Valid @RequestBody Usuario cambios) {
        return usuarioService.update(publicId, cambios);
    }

    // DELETE /api/usuarios/{publicId}
    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> delete(@PathVariable UUID publicId) {
        usuarioService.delete(publicId);
        return ResponseEntity.noContent().build();
    }
}
