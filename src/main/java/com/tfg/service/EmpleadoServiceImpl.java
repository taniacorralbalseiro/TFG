package com.tfg.service;


import com.tfg.model.Empleado;
import com.tfg.model.enumerados.RolEmpleado;
import com.tfg.repository.EmpleadoRepository;
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
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepo;
    private final UsuarioRepository usuarioRepo;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepo, UsuarioRepository usuarioRepo) {
        this.empleadoRepo = empleadoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Empleado create(Empleado nuevo) {
        if (usuarioRepo.existsByNif(nuevo.getNif()))
            throw new ConflictException("Ya existe un usuario con NIF " + nuevo.getNif());
        if (usuarioRepo.existsByEmail(nuevo.getEmail()))
            throw new ConflictException("Ya existe un usuario con email " + nuevo.getEmail());

        if (nuevo.getPublicId() == null) nuevo.setPublicId(UUID.randomUUID());
        return empleadoRepo.save(nuevo);
    }

    @Override
    @Transactional(readOnly = true)
    public Empleado getByPublicId(UUID publicId) {
        return empleadoRepo.findByPublicId(publicId)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado: " + publicId));
    }

    @Override
    @Transactional(readOnly = true)
    public Empleado getByNif(String nif) {
        return empleadoRepo.findByNif(nif)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado por NIF: " + nif));
    }

    @Override
    public Empleado update(UUID publicId, Empleado cambios) {
        Empleado e = getByPublicId(publicId);
        // Comunes
        e.setNombre(cambios.getNombre());
        e.setApellidos(cambios.getApellidos());
        e.setEmail(cambios.getEmail());
        e.setTelefono(cambios.getTelefono());
        e.setFechaNacimiento(cambios.getFechaNacimiento());
        e.setEstadoCuenta(cambios.getEstadoCuenta());
        // Específicos Empleado
        e.setNColegiado(cambios.getNColegiado());
        e.setEspecialidad(cambios.getEspecialidad());
        e.setFechaContratacion(cambios.getFechaContratacion());
        e.setFechaCese(cambios.getFechaCese());
        e.setSalario(cambios.getSalario());
        e.setRol(cambios.getRol());
        e.setEstado(cambios.getEstado());
        return empleadoRepo.save(e);
    }

    @Override
    public void delete(UUID publicId) {
        Empleado e = getByPublicId(publicId);
        empleadoRepo.delete(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleado> findAll(Pageable pageable) {
        return empleadoRepo.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleado> listByCentro(UUID centroId, Pageable pageable) {
        return empleadoRepo.findByCentroId(centroId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleado> listByRol(RolEmpleado rol, Pageable pageable) {
        return empleadoRepo.findByRol(rol, pageable);
    }
}

