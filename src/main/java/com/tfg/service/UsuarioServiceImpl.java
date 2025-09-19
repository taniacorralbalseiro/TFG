package com.tfg.service;
import com.tfg.model.Paciente;
import com.tfg.model.Usuario;
import com.tfg.repository.UsuarioRepositorio;
import com.tfg.service.UsuarioService;
import com.tfg.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private final UsuarioRepositorio usuarioRepo;

    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepo.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getByPublicId(UUID publicId) {
        return usuarioRepo.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getByNif(String nif) {
        return usuarioRepo.findByNif(nif)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado por NIF: " + nif));
    }

    @Override
    public Usuario update(UUID publicId, Usuario cambios) {
        Usuario u = getByPublicId(publicId);
        // Campos comunes de Persona/Usuario según tu entidad:
        u.setNombre(cambios.getNombre());
        u.setApellidos(cambios.getApellidos());
        u.setEmail(cambios.getEmail());
        u.setTelefono(cambios.getTelefono());
        u.setFechaNacimiento(cambios.getFechaNacimiento());
        u.setEstadoCuenta(cambios.getEstadoCuenta());
        // NIF no lo toco si es clave natural que debe permanecer única/estable
        return usuarioRepo.save(u);
    }

    @Override
    public void delete(UUID publicId) {
        Usuario u = getByPublicId(publicId);
        usuarioRepo.delete(u);
    }
}
