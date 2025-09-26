package com.tfg.service;

import com.tfg.model.Paciente;
import com.tfg.repository.PacienteRepository;
import com.tfg.repository.UsuarioRepository;
import com.tfg.service.exceptions.ConflictException;
import com.tfg.service.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepo;
    private final UsuarioRepository usuarioRepo;

    public PacienteServiceImpl(PacienteRepository pacienteRepo, UsuarioRepository usuarioRepo) {
        this.pacienteRepo = pacienteRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Paciente create(Paciente nuevo) {
        // Reglas simples de unicidad
        if (usuarioRepo.existsByNif(nuevo.getNif()))
            throw new ConflictException("Ya existe un usuario con NIF " + nuevo.getNif());
        if (usuarioRepo.existsByEmail(nuevo.getEmail()))
            throw new ConflictException("Ya existe un usuario con email " + nuevo.getEmail());

        // publicId viene por defecto en la entidad, pero puedes forzarlo si llega null
        if (nuevo.getPublicId() == null) nuevo.setPublicId(UUID.randomUUID());
        return pacienteRepo.save(nuevo);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente getByPublicId(UUID publicId) {
        return pacienteRepo.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado: " + publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente getByNif(String nif) {
        return pacienteRepo.findByNif(nif)
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado por NIF: " + nif));
    }

    @Override
    public Paciente update(UUID publicId, Paciente cambios) {
        Paciente p = getByPublicId(publicId);
        // Campos comunes (superclase) – podrías reutilizar UsuarioService.update si prefieres
        p.setNombre(cambios.getNombre());
        p.setApellidos(cambios.getApellidos());
        p.setEmail(cambios.getEmail());
        p.setTelefono(cambios.getTelefono());
        p.setFechaNacimiento(cambios.getFechaNacimiento());
        p.setEstadoCuenta(cambios.getEstadoCuenta());
        // Específicos Paciente:
       /* p.setNivelCognitivo(cambios.getNivelCognitivo());
        p.setAlergias(cambios.getAlergias());
        p.setCentroId(cambios.getCentroId());
        p.setGrupoId(cambios.getGrupoId());*/
        return pacienteRepo.save(p);
    }

    @Override
    public void delete(UUID publicId) {
        Paciente p = getByPublicId(publicId);
        pacienteRepo.delete(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> findAll(Pageable pageable) {
        return pacienteRepo.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Paciente> listByCentro(UUID centroId, Pageable pageable) {
        return pacienteRepo.findByCentroId(centroId, pageable);
    }
}
