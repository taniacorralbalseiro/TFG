package com.tfg.service;

import com.tfg.model.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GrupoService {

    Grupo create(Grupo grupo);

    Grupo update(Long id, Grupo cambios);

    Grupo getById(Long id);

    void delete(Long id);

    Page<Grupo> list(Pageable pageable);

    boolean existsByDescripcion(String descripcion);

    Grupo getByDescripcion(String descripcion);

    Page<Grupo> searchDescripcion(String parcial, Pageable pageable);

    Page<Grupo> findByCapacidad(Integer capacidad, Pageable pageable);

    Page<Grupo> findByCapacidadMinima(Integer minima, Pageable pageable);
}
