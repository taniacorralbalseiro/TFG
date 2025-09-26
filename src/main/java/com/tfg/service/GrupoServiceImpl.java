package com.tfg.service;

import com.tfg.model.Grupo;
import com.tfg.repository.GrupoRepository;
import com.tfg.service.GrupoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;

    @Override
    public Grupo create(Grupo grupo) {
        if (grupo.getDescripcion() != null && grupoRepository.existsByDescripcion(grupo.getDescripcion())) {
            throw new DataIntegrityViolationException("Ya existe un grupo con esa descripción");
        }
        return grupoRepository.save(grupo);
    }

    @Override
    public Grupo update(Long id, Grupo cambios) {
        Grupo actual = grupoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado: " + id));

        if (cambios.getDescripcion() != null
                && !cambios.getDescripcion().equalsIgnoreCase(actual.getDescripcion())
                && grupoRepository.existsByDescripcion(cambios.getDescripcion())) {
            throw new DataIntegrityViolationException("Ya existe un grupo con esa descripción");
        }

        actual.setDescripcion(cambios.getDescripcion());
        actual.setCapacidad(cambios.getCapacidad());
        // aquí actualizarías relaciones si las tienes (centro, pacientes, etc.)

        return grupoRepository.save(actual);
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getById(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grupo no encontrado: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!grupoRepository.existsById(id)) {
            throw new EntityNotFoundException("Grupo no encontrado: " + id);
        }
        grupoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grupo> list(Pageable pageable) {
        return grupoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDescripcion(String descripcion) {
        return grupoRepository.existsByDescripcion(descripcion);
    }

    @Override
    @Transactional(readOnly = true)
    public Grupo getByDescripcion(String descripcion) {
        return grupoRepository.findByDescripcion(descripcion)
                .orElseThrow(() -> new EntityNotFoundException("No existe grupo con descripción: " + descripcion));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grupo> searchDescripcion(String parcial, Pageable pageable) {
        return grupoRepository.findByDescripcionContainingIgnoreCase(parcial, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grupo> findByCapacidad(Integer capacidad, Pageable pageable) {
        return grupoRepository.findByCapacidad(capacidad, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Grupo> findByCapacidadMinima(Integer minima, Pageable pageable) {
        return grupoRepository.findByCapacidadGreaterThanEqual(minima, pageable);
    }
}
